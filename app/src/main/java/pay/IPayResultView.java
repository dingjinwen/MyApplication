package pay;

import mvp.IView;

/**
 * @author dingjinwen
 * @description 支付结果细分对错接口
 * @date 2017/6/14 0014
 */

public interface IPayResultView extends IView {
    void onPaySuccess(String result, int payType);

    void onPayFail(String result, int payType);
}
