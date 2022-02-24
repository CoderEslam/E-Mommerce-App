package com.doubleclick.e_commerceapp.Database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.doubleclick.e_commerceapp.Model.Products;

import java.util.List;


@Dao
public interface ProductsDAO {

    @Insert
    void insert(Products products);

    @Update
    void update(Products products);

    @Delete
    void delete(Products products);

    @Query("SELECT * FROM Products")
    LiveData<List<Products>> getAllProducts();

}
