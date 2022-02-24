package com.doubleclick.e_commerceapp.Activites.User.SeeAllCategory.ExbendableList;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Entity
public class ExpandedModel {

    public ExpandedModel(String parent, String child) {
        this.parent = parent;
        this.child = child;
    }



    public List<String> getList() {
        return list;
    }


    public Map<String, Set<String>> getMapList() {
        return mapList;
    }

    public void setMapList(Map<String, Set<String>> mapList) {
        this.mapList = mapList;
    }

    public ExpandedModel(Map<String, Set<String>> mapList) {
        this.mapList = mapList;
    }

    private Map<String, Set<String>> mapList;

    public void setList(List<String> list) {
        this.list = list;
    }

    private List<String> list;

    private String parent;
    private String child;
    @PrimaryKey(autoGenerate = true)
    private int id;

    public ExpandedModel() {
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public String getChild() {
        return child;
    }

    public void setChild(String child) {
        this.child = child;
    }

    @Override
    public String toString() {
        return "Model{" +
                "parent='" + parent + '\'' +
                ", child='" + child + '\'' +
                '}';
    }
}
