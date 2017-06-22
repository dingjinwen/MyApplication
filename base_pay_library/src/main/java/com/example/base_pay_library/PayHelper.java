package com.example.base_pay_library;

import android.app.Activity;
import android.os.AsyncTask;
import android.text.TextUtils;

import com.alipay.sdk.app.PayTask;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.util.Map;

/**
 * @author dingjinwen
 * @description 支付帮组类
 * @date 2017/6/14 0014
 */

public class PayHelper {

    /**
     * 微信支付
     *
     * @param activity
     * @param app_id
     * @param req
     */
    public void wechatPay(Activity activity, String app_id, PayReq req) {
        IWXAPI api = WXAPIFactory.createWXAPI(activity, app_id, true);
        api.registerApp(app_id);
        api.sendReq(req);
    }

    /**
     * 支付宝支付
     *
     * @param activity
     * @param payInfo
     */
    public void aliPay(Activity activity, String payInfo, IPayResultView payResultView) {
        // 必须异步调用
        new AliPayTask(activity, payInfo, payResultView).execute();
    }

    private class AliPayTask extends AsyncTask<String, Integer, Map<String, String>> {
        private Activity mActivity;
        private String mPayInfo;
        private IPayResultView mPayResultView;

        public AliPayTask(Activity activity, String payInfo, IPayResultView payResultView) {
            super();
            mActivity = activity;
            mPayInfo = payInfo;
            mPayResultView = payResultView;
        }

        @Override
        protected Map<String, String> doInBackground(String... params) {
            // 构造PayTask 对象
            PayTask alipay = new PayTask(mActivity);
            // 调用支付接口，获取支付结果
            Map<String, String> result = alipay.payV2(mPayInfo, true);
            return result;
        }

        /**
         * 9000 -> 订单支付成功
         * 8000 -> 正在处理中
         * 4000 -> 订单支付失败
         * 6001 -> 用户中途取消
         * 6002 -> 网络连接出错
         *
         * @param result
         */
        @Override
        protected void onPostExecute(Map<String, String> result) {
            AliPayResult aliPayResult = new AliPayResult(result);
            /**
             * 同步返回的结果必须放置到服务端进行验证（验证的规则请看https://doc.open.alipay.com/doc2/
             * detail.htm?spm=0.0.0.0.xdvAU6&treeId=59&articleId=103665&
             * docType=1) 建议商户依赖异步通知
             */
            String resultInfo = aliPayResult.getResult();// 同步返回需要验证的信息
            String resultStatus = aliPayResult.getResultStatus();
            // 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
            if (TextUtils.equals(resultStatus, "9000")) {
                mPayResultView.onPaySuccess(resultInfo, PayConstants.PAY_TYPE_ALIPAY);
            } else {
                // 判断resultStatus 为非"9000"则代表可能支付失败
                // "8000"代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
                // 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
                mPayResultView.onPayFail(resultInfo, PayConstants.PAY_TYPE_ALIPAY);
            }
        }
    }
}
