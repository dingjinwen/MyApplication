package presenter;

import model.GoodsModel;
import mvp.BasePresenter;
import view.GoodsView;

/**
 * @author dingjinwen
 * @description
 * @date 2017/6/8 0008
 */

public class GoodsPresenterImp extends BasePresenter<GoodsView> implements GoodsPresenter {

    @Override
    public void getGoods(String goodId) {
        GoodsModel model = GoodsModel.getGoodsForNet(true, "");
        GoodsView goodsView = getView();
        if (model != null) {
            if (goodsView != null) {
                getView().getGoodsSuccess(model.getGoods());
            }
        } else {
            if (goodsView != null) {
                getView().getGoodsFail(model.errorCode, model.errorMessage);
            }
        }
    }

}
