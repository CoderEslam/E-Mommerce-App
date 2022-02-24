package com.doubleclick.e_commerceapp.Model;


public class ProductsRetrive {

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
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
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

    public String getIdAdmin() {
        return idAdmin;
    }

    public void setIdAdmin(String idAdmin) {
        this.idAdmin = idAdmin;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private String pname;
    private String description;
    private String price;
    private String image;
    private String category;
    private String pid;
    private String date;
    private String time;
    private String idAdmin;
    private int id;

    @Override
    public String toString() {
        return "ProductsRetrive{" +
                "pname='" + pname + '\'' +
                ", description='" + description + '\'' +
                ", price='" + price + '\'' +
                ", image='" + image + '\'' +
                ", category='" + category + '\'' +
                ", pid='" + pid + '\'' +
                ", date='" + date + '\'' +
                ", time='" + time + '\'' +
                ", idAdmin='" + idAdmin + '\'' +
                ", id=" + id +
                '}';
    }
}
