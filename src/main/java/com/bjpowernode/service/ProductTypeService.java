package com.bjpowernode.service;

import com.bjpowernode.domain.ProductType;

import java.util.List;

public interface ProductTypeService {

    // 获取所有商品类别的信息
    List<ProductType> getAllProductType();
}
