package com.bjpowernode.service;

import com.bjpowernode.domain.ProductInfo;
import com.bjpowernode.vo.ProductInfoVo;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface ProductInfoService {

    List<ProductInfo> getAllProduct();

    PageInfo split(int pageNum, int pageSize);

    boolean saveProductInfo(ProductInfo productInfo);

    ProductInfo getProductInfoById(Integer pid);

    boolean updateProductById(ProductInfo productInfo);

    boolean deleteProductInfo(int pid);

    boolean deleteBatch(String[] idArray);

    List<ProductInfo> selectProductInfoByConditions(ProductInfoVo productInfoVo);

    // 多条件查询+分页
    PageInfo<ProductInfo> splitPageVo(ProductInfoVo productInfoVo, int pageSize);
}
