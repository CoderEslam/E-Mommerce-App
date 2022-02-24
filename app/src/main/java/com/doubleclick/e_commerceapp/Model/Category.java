package com.doubleclick.e_commerceapp.Model;

public class Category {


    public String getCategoryName() {
        return CategoryName;
    }

    public void setCategoryName(String categoryName) {
        CategoryName = categoryName;
    }

    public String getCategoryImage() {
        return CategoryImage;
    }

    public void setCategoryImage(String categoryImage) {
        CategoryImage = categoryImage;
    }

    @Override
    public String toString() {
        return "Category{" +
                "CategoryName='" + CategoryName + '\'' +
                ", CategoryImage='" + CategoryImage + '\'' +
                '}';
    }

    private String CategoryName;
    private String CategoryImage;


}
