package pay;

import android.content.Context;
import android.content.Intent;

import com.example.administrator.myapplication.R;

import base.BaseActivity;

/**
 * @author dingjinwen
 * @description
 * @date 2017/6/13 0013
 */

public class AliPayActivity extends BaseActivity<IPayPresenterImpl> implements IPayResultView {


    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        /**
         * 完整的符合支付宝参数规范的订单信息
         */
//        final String payInfo = orderInfo + "&sign=\"" + sign + "\"&" + getSignType();
        final String payInfo = "partner=" + PayConstants.PARTNER +
                "&seller_id=" + PayConstants.SELLER +
                "&out_trade_no=" + "0819145412-6177" +
                "&subject=" + "测试" +
                "&body=" + "测试测试" +
                "&total_fee=" + "0.01" +
                "&notify_url=" + "http://notify.msp.hk/notify.htm" +
                "&service=" + "mobile.securitypay.pay" +
                "&payment_type=" + "1" +
                "&_input_charset =" + "utf-8" +
                "&it_b_pay=" + "30m" +
                "&sign=" + PayConstants.RSA_PRIVATE +
                "&sign_type=" + "RSA";

        mPresenter.pay(PayConstants.PAY_TYPE_ALIPAY, payInfo);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.layout_fragment_one;
    }

    @Override
    protected IPayPresenterImpl loadPresenter() {
        return new IPayPresenterImpl(this, new PayHelper());
    }

    public static void launch(Context context) {
        Intent intent = new Intent(context, AliPayActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void onPaySuccess(String result, int payType) {
        showToast("支付成功：" + result);
    }

    @Override
    public void onPayFail(String result, int payType) {
        showToast("支付失败：" + result);
    }
}
