package com.example.mobio_khushbu_practicaltest.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;

public class Product implements Parcelable {
    String id,Price,Image,ProductName,Qty,SelectedColor,SelectedBrand;
    ArrayList<String> ColorList;
    ArrayList<Brand> BrandList;

    public Product(String id, String price, String image, String productName, ArrayList<String> colorList,
                   ArrayList<Brand> brandList,String Qty,String SelectedColor,String SelectedBrand) {
        this.id = id;
        Price = price;
        Image = image;
        ProductName = productName;
        ColorList = colorList;
        BrandList = brandList;
        this.Qty = Qty;
        this.SelectedColor = SelectedColor;
        this.SelectedBrand = SelectedBrand;
    }

    protected Product(Parcel in) {
        id = in.readString();
        Price = in.readString();
        Image = in.readString();
        ProductName = in.readString();
        Qty = in.readString();
        SelectedColor = in.readString();
        SelectedBrand = in.readString();
        ColorList = in.createStringArrayList();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(Price);
        dest.writeString(Image);
        dest.writeString(ProductName);
        dest.writeString(Qty);
        dest.writeString(SelectedColor);
        dest.writeString(SelectedBrand);
        dest.writeStringList(ColorList);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Product> CREATOR = new Creator<Product>() {
        @Override
        public Product createFromParcel(Parcel in) {
            return new Product(in);
        }

        @Override
        public Product[] newArray(int size) {
            return new Product[size];
        }
    };

    @Override
    public String toString() {
        return "Product{" +
                "id='" + id + '\'' +
                ", Price='" + Price + '\'' +
                ", Image='" + Image + '\'' +
                ", ProductName='" + ProductName + '\'' +
                ", Qty='" + Qty + '\'' +
                ", SelectedColor='" + SelectedColor + '\'' +
                ", SelectedBrand='" + SelectedBrand + '\'' +
                ", ColorList=" + ColorList +
                ", BrandList=" + BrandList +
                '}';
    }

    public String getQty() {
        return Qty;
    }

    public void setQty(String qty) {
        Qty = qty;
    }

    public String getSelectedColor() {
        return SelectedColor;
    }

    public void setSelectedColor(String selectedColor) {
        SelectedColor = selectedColor;
    }

    public String getSelectedBrand() {
        return SelectedBrand;
    }

    public void setSelectedBrand(String selectedBrand) {
        SelectedBrand = selectedBrand;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }

    public ArrayList<String> getColorList() {
        return ColorList;
    }

    public void setColorList(ArrayList<String> colorList) {
        ColorList = colorList;
    }

    public ArrayList<Brand> getBrandList() {
        return BrandList;
    }

    public void setBrandList(ArrayList<Brand> brandList) {
        BrandList = brandList;
    }

}
