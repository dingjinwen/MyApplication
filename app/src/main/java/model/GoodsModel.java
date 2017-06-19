package model;

import bean.Goods;
import mvp.BaseModel;

/**
 * @author dingjinwen
 * @description
 * @date 2017/6/8 0008
 */

public class GoodsModel extends BaseModel {
    Goods goods;

    public Goods getGoods() {
        return goods;
    }

    public void setGoods(Goods goods) {
        this.goods = goods;
    }

    public static GoodsModel getGoodsForNet(boolean flag, String goodsId) {
        final GoodsModel model = new GoodsModel();
        if (flag) {
            Goods goods = new Goods();
            goods.setName("矿泉水");
            goods.setPrice(12.5);
            model.setGoods(goods);
            model.status = 1;
        } else {
            model.status = 0;
            model.errorCode = 21;
            model.errorMessage = "没网络";
        }
        return model;
    }
}
