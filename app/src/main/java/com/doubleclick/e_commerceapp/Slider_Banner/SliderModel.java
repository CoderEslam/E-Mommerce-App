package com.doubleclick.e_commerceapp.Slider_Banner;

public class SliderModel {

//    public SliderModel(int drawable) {
//        this.drawable = drawable;
//    }
//    public String getBanner() {
//        return banner;
//    }
//    public void setBanner(String banner) {
//        this.banner = banner;
//    }
//    private String banner;
//    public int getDrawable() {
//        return drawable;
//    }
//
//    public void setDrawable(int drawable) {
//        this.drawable = drawable;
//    }
//
//    private int drawable;
//    public String getBackgroundColor() {
//        return backgroundColor;
//    }
//
//
//    public void setBackgroundColor(String backgroundColor) {
//        this.backgroundColor = backgroundColor;
//    }
//
//    private String backgroundColor;
//    public void setBackgroundcolor(int backgroundcolor) {
//        this.backgroundcolor = backgroundcolor;
//    }
//
//    private int backgroundcolor;

    @Override
    public String toString() {
        return "SliderModel{" +
                "ImageAd='" + ImageAd + '\'' +
                ", TypeAd='" + TypeAd + '\'' +
                '}';
    }

    public SliderModel() {

    }

    public SliderModel(String imageAd, String typeAd) {
        ImageAd = imageAd;
        TypeAd = typeAd;
    }

    private String ImageAd;
    private String TypeAd;


    public String getImageAd() {
        return ImageAd;
    }

    public void setImageAd(String imageAd) {
        ImageAd = imageAd;
    }

    public String getTypeAd() {
        return TypeAd;
    }

    public void setTypeAd(String typeAd) {
        TypeAd = typeAd;
    }


}
