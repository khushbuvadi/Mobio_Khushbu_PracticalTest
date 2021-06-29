package com.example.mobio_khushbu_practicaltest.model;

public class Color {
    String ColorName;
    boolean isSelected;

    public Color(String colorName, boolean isSelected) {
        ColorName = colorName;
        this.isSelected = isSelected;
    }

    public String getColorName() {
        return ColorName;
    }

    public void setColorName(String colorName) {
        ColorName = colorName;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    @Override
    public String toString() {
        return "Color{" +
                "ColorName='" + ColorName + '\'' +
                ", isSelected=" + isSelected +
                '}';
    }
}
