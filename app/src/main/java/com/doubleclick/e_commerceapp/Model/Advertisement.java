package com.doubleclick.e_commerceapp.Model;

public class Advertisement {

    public Advertisement(String image, String name, int ordered, String timeBegin, String timeEnd) {
        ImageAd = image;
        Name = name;
        this.ordered = ordered;
        TimeBegin = timeBegin;
        TimeEnd = timeEnd;
    }

    public Advertisement() {
    }


    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public int getOrdered() {
        return ordered;
    }

    public void setOrdered(int ordered) {
        this.ordered = ordered;
    }

    public String getTimeBegin() {
        return TimeBegin;
    }

    public void setTimeBegin(String timeBegin) {
        TimeBegin = timeBegin;
    }

    public String getTimeEnd() {
        return TimeEnd;
    }

    public void setTimeEnd(String timeEnd) {
        TimeEnd = timeEnd;
    }

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

    private String ImageAd;
    private String Name;
    private int ordered;
    private String TimeBegin;
    private String TimeEnd;
    private String TypeAd;
    private String IdAdmin;
    private String IDProducts;
    private String AdminName;
    private String RandomKey;

    public String getAdminName() {
        return AdminName;
    }

    public void setAdminName(String adminName) {
        AdminName = adminName;
    }



    public String getIDProducts() {
        return IDProducts;
    }

    public void setIDProducts(String IDProducts) {
        this.IDProducts = IDProducts;
    }



    public String getIdAdmin() {
        return IdAdmin;
    }

    public void setIdAdmin(String idAdmin) {
        IdAdmin = idAdmin;
    }



    public String getRandomKey() {
        return RandomKey;
    }

    public void setRandomKey(String randomKey) {
        RandomKey = randomKey;
    }



    @Override
    public String toString() {
        return "Advertisement{" +
                "ImageAd='" + ImageAd + '\'' +
                ", TypeAd='" + TypeAd + '\'' +
                ", RandomKey='" + RandomKey + '\'' +
                '}';
    }
}
