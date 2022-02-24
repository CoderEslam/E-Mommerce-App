package com.doubleclick.e_commerceapp.Fragments.Favorite;

public class Favorite {


    private String ChildCategory;
    private String HeadCategory;
    private String Pid;

    public Favorite() {
    }

    public String getFid() {
        return Fid;
    }

    public void setFid(String fid) {
        Fid = fid;
    }

    private String Fid;

    public String getChildCategory() {
        return ChildCategory;
    }

    public void setChildCategory(String childCategory) {
        ChildCategory = childCategory;
    }

    public String getHeadCategory() {
        return HeadCategory;
    }

    public void setHeadCategory(String headCategory) {
        HeadCategory = headCategory;
    }

    public String getPid() {
        return Pid;
    }

    public void setPid(String pid) {
        this.Pid = pid;
    }

    @Override
    public String toString() {
        return "Favorite{" +
                "ChildCategory='" + ChildCategory + '\'' +
                ", HeadCategory='" + HeadCategory + '\'' +
                ", Pid='" + Pid + '\'' +
                ", Fid='" + Fid + '\'' +
                '}';
    }

}
