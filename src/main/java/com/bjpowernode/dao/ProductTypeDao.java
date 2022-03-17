package com.bjpowernode.dao;

import com.bjpowernode.domain.ProductType;

import java.util.List;

public interface ProductTypeDao {

    // 获取所有商品类别的信息
    List<ProductType> getAllProductType();
}