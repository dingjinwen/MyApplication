package bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author dingjinwen
 * @description
 * @date 2017/6/8 0008
 */

public class Goods implements Parcelable {
    private String name;
    private double price;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeDouble(this.price);
    }

    public Goods() {
    }

    protected Goods(Parcel in) {
        this.name = in.readString();
        this.price = in.readDouble();
    }

    public static final Parcelable.Creator<Goods> CREATOR = new Parcelable.Creator<Goods>() {
        @Override
        public Goods createFromParcel(Parcel source) {
            return new Goods(source);
        }

        @Override
        public Goods[] newArray(int size) {
            return new Goods[size];
        }
    };
}
