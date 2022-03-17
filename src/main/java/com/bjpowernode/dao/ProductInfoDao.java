package com.bjpowernode.dao;

import com.bjpowernode.domain.ProductInfo;
import com.bjpowernode.vo.ProductInfoVo;

import java.util.List;

public interface ProductInfoDao {

    // 查询所有商品信息（按id降序排序，不分页）
    List<ProductInfo> getAllProduct();

    // 插入商品信息
    int saveProductInfo(ProductInfo productInfo);

    // 根据id查询商品信息
    ProductInfo getProductInfoById(Integer pid);

    // 根据id更新商品信息
    int updateProductById(ProductInfo productInfo);

    // 根据id删除单条商品信息
    int deleteProductInfoById(int pid);

    // 批量删除商品信息
    int deleteBatch(String[] idArray);

    // 多条件查询商品信息
    List<ProductInfo> selectProductInfoByConditions(ProductInfoVo productInfoVo);
}