package com.bjpowernode.test;

import com.bjpowernode.dao.ProductInfoDao;
import com.bjpowernode.domain.ProductInfo;
import com.bjpowernode.vo.ProductInfoVo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext_dao.xml", "classpath:applicationContext_service.xml"})
public class MyTest {

    @Autowired
    private ProductInfoDao productInfoDao;

    @Test
    public void testSelectProductInfoByConditions(){
        ProductInfoVo productInfoVo = new ProductInfoVo();
        // productInfoVo.setpName("4");
        // productInfoVo.setTypeId(3);
        //productInfoVo.setlPrice(2000);
        productInfoVo.sethPrice(3999);

        List<ProductInfo> productInfos = productInfoDao.selectProductInfoByConditions(productInfoVo);

        for(ProductInfo productInfo : productInfos){
            System.out.println(productInfo);
        }
    }

}
