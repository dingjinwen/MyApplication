package pay;

import android.app.Activity;

import mvp.BasePresenter;

/**
 * @author dingjinwen
 * @description
 * @date 2017/6/14 0014
 */

public class IPayPresenterImpl extends BasePresenter<IPayResultView> implements IPayPresenter {

    private Activity mContext;
    private PayHelper mPayHelper;

    public IPayPresenterImpl(Activity context, PayHelper payHelper) {
        mContext = context;
        mPayHelper = payHelper;
    }

    @Override
    public void pay(int payType, String orderInfo) {
        switch (payType) {
            case PayConstants.PAY_TYPE_ALIPAY:
                mPayHelper.aliPay(mContext, orderInfo, getView());
                break;
            case PayConstants.PAY_TYPE_WECHAT:
                break;
        }
    }
}
