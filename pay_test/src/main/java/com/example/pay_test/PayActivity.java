package com.example.pay_test;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.example.base_pay_library.IPayPresenter;
import com.example.base_pay_library.IPayPresenterImpl;
import com.example.base_pay_library.IPayResultView;
import com.example.base_pay_library.PayHelper;
import com.tencent.mm.opensdk.modelpay.PayReq;

public class PayActivity extends AppCompatActivity implements IPayResultView {

    private PayHelper mPayHelper;
    private IPayPresenter mPayPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);
        mPayHelper = new PayHelper();
        mPayPresenter = new IPayPresenterImpl(this, mPayHelper, this);
    }

    //支付宝支付
    public void btnAliPay(View view) {
        if (mPayPresenter != null) {
            //完整的符合支付宝参数规范的订单信息
            //final String payInfo = orderInfo + "&sign=\"" + sign + "\"&" + getSignType();
            final String payInfo = "partner=" + Constants.PARTNER +
                    "&seller_id=" + Constants.SELLER +
                    "&out_trade_no=" + "0819145412-6177" +
                    "&subject=" + "测试" +
                    "&body=" + "测试测试" +
                    "&total_fee=" + "0.01" +
                    "&notify_url=" + "http://notify.msp.hk/notify.htm" +
                    "&service=" + "mobile.securitypay.pay" +
                    "&payment_type=" + "1" +
                    "&_input_charset =" + "utf-8" +
                    "&it_b_pay=" + "30m" +
                    "&sign=" + Constants.RSA_PRIVATE +
                    "&sign_type=" + "RSA";
            mPayPresenter.aliPay(payInfo);
        }
    }

    //微信支付
    public void btnWechatPay(View view) {
        if (mPayPresenter != null) {
            PayReq req = new PayReq();

            /**
             * 请求app服务器得到的回调结果
             */
//            req.appId = APP_ID;// 微信开放平台审核通过的应用APPID
//            req.partnerId = jsonObject.getString("partnerid");// 微信支付分配的商户号
//            req.prepayId = jsonObject.getString("prepayid");// 预支付订单号，app服务器调用“统一下单”接口获取
//            req.nonceStr = jsonObject.getString("noncestr");// 随机字符串，不长于32位，服务器小哥会给咱生成
//            req.timeStamp = jsonObject.getString("timestamp");// 时间戳，app服务器小哥给出
//            req.packageValue = jsonObject.getString("package");// 固定值Sign=WXPay，可以直接写死，服务器返回的也是这个固定值
//            req.sign = jsonObject.getString("sign");// 签名，服务器小哥给出，他会根据：https://pay.weixin.qq.com/wiki/doc/api/app/app.php?chapter=4_3指导得到这个
            mPayPresenter.wechatPay(Constants.APP_ID, req);
        }
    }

    @Override
    public void onPaySuccess(String result, int payType) {
        showToast("支付成功：" + result);
    }

    @Override
    public void onPayFail(String result, int payType) {
        showToast("支付失败：" + result);
    }

    private void showToast(String s) {
        Toast.makeText(this, s, Toast.LENGTH_LONG).show();
    }

}
