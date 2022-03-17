package com.bjpowernode.service.impl;

import com.bjpowernode.dao.ProductInfoDao;
import com.bjpowernode.domain.ProductInfo;
import com.bjpowernode.service.ProductInfoService;
import com.bjpowernode.vo.ProductInfoVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ProductInfoServiceImpl implements ProductInfoService {

    // 持久层访问对象
    @Resource
    private ProductInfoDao productInfoDao;


    // 获取全部商品的信息（按id降序排序，不分页）
    @Override
    public List<ProductInfo> getAllProduct() {

        List<ProductInfo> productInfoList = productInfoDao.getAllProduct();

        return productInfoList;
    }

    @Override
    public PageInfo split(int pageNum, int pageSize) {
        // 这行语句的作用是在sql语句后加上limit关键字，达到分页的功能
        PageHelper.startPage(pageNum, pageSize);

        // sql 语句变为 select * from product_info order by p_id limit (pageNum-1)*pageSize, pageSize;
        // 降序查询分页后的商品信息的列表
        List<ProductInfo> productInfoList = productInfoDao.getAllProduct();

        // 创建PageInfo对象，封装数据
        PageInfo<ProductInfo> pageInfo = new PageInfo<>(productInfoList); // 将查询到的列表传入，其内部就会自动将这个类中所有的属性附上对应的值。包括pageNum，pageSize，总页数pages，总记录数total等

        return pageInfo;
    }

    @Override
    public boolean saveProductInfo(ProductInfo productInfo) {

        boolean flag = true;

        int count = productInfoDao.saveProductInfo(productInfo);

        if(count != 1){
            flag = false;
        }

        return flag;
    }


    @Override
    public ProductInfo getProductInfoById(Integer pid) {

        ProductInfo productInfo = productInfoDao.getProductInfoById(pid);

        return productInfo;
    }


    @Override
    public boolean updateProductById(ProductInfo productInfo) {
        boolean flag = true;

        int count = productInfoDao.updateProductById(productInfo);

        if(count != 1){
            flag = false;
        }

        return flag;
    }


    @Override
    public boolean deleteProductInfo(int pid) {
        boolean flag = true;

        int count = productInfoDao.deleteProductInfoById(pid);

        if(count != 1){
            flag = false;
        }

        return flag;
    }


    @Override
    public boolean deleteBatch(String[] idArray) {
        boolean flag = true;

        int count = productInfoDao.deleteBatch(idArray);

        if(count != idArray.length){
            flag = false;
        }

        return flag;
    }


    @Override
    public List<ProductInfo> selectProductInfoByConditions(ProductInfoVo productInfoVo) {
        return productInfoDao.selectProductInfoByConditions(productInfoVo);
    }


    // 多条件查询+分页
    @Override
    public PageInfo<ProductInfo> splitPageVo(ProductInfoVo productInfoVo, int pageSize) {

        PageHelper.startPage(productInfoVo.getPageNum(), pageSize);

        List<ProductInfo> list = productInfoDao.selectProductInfoByConditions(productInfoVo);

        return new PageInfo<>(list);
    }
}
