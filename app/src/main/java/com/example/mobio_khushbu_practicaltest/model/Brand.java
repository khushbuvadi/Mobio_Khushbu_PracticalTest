package com.example.mobio_khushbu_practicaltest.model;

public class Brand {
    String id,BrandName;

    public Brand(String id, String brandName) {
        this.id = id;
        BrandName = brandName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBrandName() {
        return BrandName;
    }

    public void setBrandName(String brandName) {
        BrandName = brandName;
    }

    @Override
    public String toString() {
        return "Brand{" +
                "id='" + id + '\'' +
                ", BrandName='" + BrandName + '\'' +
                '}';
    }
}
