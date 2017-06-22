package com.example.base_pay_library;

import com.tencent.mm.opensdk.modelpay.PayReq;

/**
 * @author dingjinwen
 * @description 支付接口
 * @date 2017/6/14 0014
 */
public interface IPayPresenter {
    void aliPay(String orderInfo);

    void wechatPay(String app_id, PayReq req);
}
