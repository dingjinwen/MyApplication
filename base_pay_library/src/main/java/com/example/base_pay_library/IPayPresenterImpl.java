package com.example.base_pay_library;

import android.app.Activity;

import com.tencent.mm.opensdk.modelpay.PayReq;

/**
 * @author dingjinwen
 * @description
 * @date 2017/6/14 0014
 */

public class IPayPresenterImpl implements IPayPresenter {

    private Activity mContext;
    private PayHelper mPayHelper;
    private IPayResultView mPayResultView;

    public IPayPresenterImpl(Activity context, PayHelper payHelper, IPayResultView iPayResultView) {
        mContext = context;
        mPayHelper = payHelper;
        mPayResultView = iPayResultView;
    }

    @Override
    public void aliPay(String orderInfo) {
        mPayHelper.aliPay(mContext, orderInfo, mPayResultView);
    }

    @Override
    public void wechatPay(String app_id, PayReq req) {
        mPayHelper.wechatPay(mContext, app_id, req);
    }
}
