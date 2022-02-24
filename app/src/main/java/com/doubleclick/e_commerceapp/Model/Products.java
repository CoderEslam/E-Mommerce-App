package com.doubleclick.e_commerceapp.Model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Products {

    public Products(String pname, String description, String price, String image,
                    //String category,
             String pid, String date, String time, String idAdmin) {
        this.pname = pname;
        this.description = description;
        this.priceBefore = price;
        this.image = image;
        //this.category = category;
        this.pid = pid;
        this.date = date;
        this.time = time;
        this.idAdmin = idAdmin;
    }

    public Products(String pname, String description, String price, String image,
                    //String category,
             String pid, String date, String time, String idAdmin, int id) {
        this.pname = pname;
        this.description = description;
        this.priceBefore = price;
        this.image = image;
        //this.category = category;
        this.pid = pid;
        this.date = date;
        this.time = time;
        this.idAdmin = idAdmin;
        this.id = id;
    }


    private String ChildCategory;
    private String pid; //Product ID
    private String date;
    private String time;
    private String idAdmin;
    private String HeadCategory;
    private String pname;
    private String description;

    public String getPriceBefore() {
        return priceBefore;
    }

    public void setPriceBefore(String priceBefore) {
        this.priceBefore = priceBefore;
    }

    private String priceBefore;
    private String image;
    private String rate1;
    private String rate2;
    private String rate3;
    private String rate4;
    private String rate5;
    private String trademark;


    public String getImage1() {
        return image1;
    }

    public void setImage1(String image1) {
        this.image1 = image1;
    }

    public String getImage2() {
        return image2;
    }

    public void setImage2(String image2) {
        this.image2 = image2;
    }

    public String getImage3() {
        return image3;
    }

    public void setImage3(String image3) {
        this.image3 = image3;
    }

    public String getImage4() {
        return image4;
    }

    public void setImage4(String image4) {
        this.image4 = image4;
    }

    private String image1;
    private String image2;
    private String image3;
    private String image4;

    /*public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    private String category;*/

    public Products(String pname, String description, String image, String price, String HeadCategory, String childCategory,
                    String pid,
                    String idAdmin,
                    String trademark,
                    String time,
                    String rate1,
                    String rate2,
                    String rate3,
                    String rate4,
                    String rate5) {
        this.pname = pname;
        this.description = description;
        this.priceBefore = price;
        this.image = image;
        this.rate1 = rate1;
        this.rate2 = rate2;
        this.rate3 = rate3;
        this.rate4 = rate4;
        this.rate5 = rate5;
        this.trademark = trademark;
        this.HeadCategory = HeadCategory;
        this.ChildCategory = childCategory;
        this.pid = pid;
        this.time = time;
        this.idAdmin = idAdmin;
    }




    public String getChildCategory() {
        return ChildCategory;
    }

    public void setChildCategory(String childCategory) {
        this.ChildCategory = childCategory;
    }

    

    public String getHeadCategory() {
        return HeadCategory;
    }

    public void setHeadCategory(String headCategory) {
        HeadCategory = headCategory;
    }

    @PrimaryKey(autoGenerate = true)
    private int id;


    public String getRate1() {
        return rate1;
    }

    public void setRate1(String rate1) {
        this.rate1 = rate1;
    }

    public String getRate2() {
        return rate2;
    }

    public void setRate2(String rate2) {
        this.rate2 = rate2;
    }

    public String getRate3() {
        return rate3;
    }

    public void setRate3(String rate3) {
        this.rate3 = rate3;
    }

    public String getRate4() {
        return rate4;
    }

    public void setRate4(String rate4) {
        this.rate4 = rate4;
    }

    public String getRate5() {
        return rate5;
    }

    public void setRate5(String rate5) {
        this.rate5 = rate5;
    }

    public String getTrademark() {
        return trademark;
    }

    public void setTrademark(String trademark) {
        this.trademark = trademark;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getIdAdmin() {
        return idAdmin;
    }

    public void setIdAdmin(String idAdmin) {
        this.idAdmin = idAdmin;
    }


    public Products() {
        //must empty Constractor
    }


    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrice() {
        return priceBefore;
    }

    public void setPrice(String price) {
        this.priceBefore = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "Products{" +
                "pname='" + pname + '\'' +
                ", description='" + description + '\'' +
                ", price='" + priceBefore + '\'' +
                ", image='" + image + '\'' +
                ", trademark='" + trademark + '\'' +
                ", ChildCategory='" + ChildCategory + '\'' +
                ", pid='" + pid + '\'' +
                ", time='" + time + '\'' +
                ", idAdmin='" + idAdmin + '\'' +
                ", HeadCategory='" + HeadCategory + '\'' +
                '}';
    }

    public boolean isXS() {
        return XS;
    }

    public void setXS(boolean XS) {
        this.XS = XS;
    }

    public boolean isS() {
        return S;
    }

    public void setS(boolean s) {
        S = s;
    }

    public boolean isM() {
        return M;
    }

    public void setM(boolean m) {
        M = m;
    }

    public boolean isL() {
        return L;
    }

    public void setL(boolean l) {
        L = l;
    }

    public boolean isXL() {
        return XL;
    }

    public void setXL(boolean XL) {
        this.XL = XL;
    }

    public boolean isXXL() {
        return XXL;
    }

    public void setXXL(boolean XXL) {
        this.XXL = XXL;
    }

    public boolean isXXXL() {
        return XXXL;
    }

    public void setXXXL(boolean XXXL) {
        this.XXXL = XXXL;
    }
    public String getMatrial() {
        return Matrial;
    }

    public void setMatrial(String Matrial) {
        this.Matrial = Matrial;
    }

    public String getBelongesTo() {
        return belongesTo;
    }

    public void setBelongesTo(String belongesTo) {
        this.belongesTo = belongesTo;
    }
    public String getPriceAfter() {
        return priceAfter;
    }

    public void setPriceAfter(String priceAfter) {
        this.priceAfter = priceAfter;
    }
///////////////////Clothes
    private boolean XS;
    private boolean S;
    private boolean M;
    private boolean L;
    private boolean XL;
    private boolean XXL;
    private boolean XXXL;
    private String Matrial;
    private String belongesTo;
    private String priceAfter;

    public Products(String childCategory, String pid, String date, String time, String idAdmin, String headCategory, String pname, String description, String priceBefore ,String priceAfter , String trademark, String image1, String image2, String image3, String image4, boolean XS, boolean s, boolean m, boolean l, boolean XL, boolean XXL, boolean XXXL, String Matrial, String belongesTo) {
        ChildCategory = childCategory;
        this.pid = pid;
        this.date = date;
        this.time = time;
        this.idAdmin = idAdmin;
        HeadCategory = headCategory;
        this.pname = pname;
        this.description = description;
        this.priceBefore = priceBefore;
        this.priceAfter = priceAfter;
        this.trademark = trademark;
        this.image1 = image1;
        this.image2 = image2;
        this.image3 = image3;
        this.image4 = image4;
        this.XS = XS;
        S = s;
        M = m;
        L = l;
        this.XL = XL;
        this.XXL = XXL;
        this.XXXL = XXXL;
        this.Matrial = Matrial;
        this.belongesTo = belongesTo;
    }
    //////////////////////////////////Clothes
}