package com.doubleclick.e_commerceapp.Model;

import com.doubleclick.e_commerceapp.Slider_Banner.SliderModel;

import java.util.List;

public class HomePage {

    public static  final int BANNER_SLIDER = 0;
    public static  final int STRIB_AD_BANNER = 1;
    public static  final int HORIZONTAL_PRODUCT_VIEW = 2;

    //for products
    public HomePage( int type,String categoryName) {
        CategoryName = categoryName;
        Type = type;
    }

    //for Strip Image.
    public HomePage(int type,String backgrondColor, String imageResource) {
        this.backgrondColor = backgrondColor;
        ImageResource = imageResource;
        Type = type;
    }

    public HomePage( int type,List<SliderModel> sliderModelList) {
        this.sliderModelList = sliderModelList;
        Type = type;
    }

    public HomePage(String CategoryName ,List<Products> productsList, int type) {
        this.productsList = productsList;
        this.CategoryName = CategoryName;
        Type = type;
    }


    public String getCategoryName() {
        return CategoryName;
    }

    public void setCategoryName(String categoryName) {
        CategoryName = categoryName;
    }

    public String getBackgrondColor() {
        return backgrondColor;
    }

    public void setBackgrondColor(String backgrondColor) {
        this.backgrondColor = backgrondColor;
    }

    public List<Products> getProductsList() {
        return productsList;
    }

    public void setProductsList(List<Products> productsList) {
        this.productsList = productsList;
    }

    private String CategoryName;
    private String backgrondColor;

    public String getImageResource() {
        return ImageResource;
    }

    public void setImageResource(String imageResource) {
        ImageResource = imageResource;
    }

    private String ImageResource;
    private List<Products> productsList;

    public List<SliderModel> getSliderModelList() {
        return sliderModelList;
    }

    public void setSliderModelList(List<SliderModel> sliderModelList) {
        this.sliderModelList = sliderModelList;
    }

    private List<SliderModel> sliderModelList;

    public HomePage(int type,List<Advertisement> advertisementList,String Strip) {
        this.advertisementList = advertisementList;
        Type = type;
    }

    public List<Advertisement> getAdvertisementList() {
        return advertisementList;
    }

    public void setAdvertisementList(List<Advertisement> advertisementList) {
        this.advertisementList = advertisementList;
    }

    private List<Advertisement> advertisementList;

    public HomePage(String categoryName, String backgrondColor, List<Products> productsList, int type) {
        CategoryName = categoryName;
        this.backgrondColor = backgrondColor;
        this.productsList = productsList;
        Type = type;
    }

    private int Type;

    public int getType() {
        return Type;
    }

    public void setType(int type) {
        Type = type;
    }

    public HomePage() {
    }
}
