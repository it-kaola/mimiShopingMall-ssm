package com.bjpowernode.service.impl;

import com.bjpowernode.dao.ProductTypeDao;
import com.bjpowernode.domain.ProductType;
import com.bjpowernode.service.ProductTypeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ProductTypeServiceImpl implements ProductTypeService {

    @Resource
    private ProductTypeDao productTypeDao;

    @Override
    public List<ProductType> getAllProductType() {
        return productTypeDao.getAllProductType();
    }
}
