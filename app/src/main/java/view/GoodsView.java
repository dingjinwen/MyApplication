package view;

import bean.Goods;
import mvp.IView;

/**
 * @author dingjinwen
 * @description
 * @date 2017/6/8 0008
 */

public interface GoodsView extends IView {
    void getGoodsSuccess(Goods goods);

    void getGoodsFail(int errorCode, String errorMessage);
}
