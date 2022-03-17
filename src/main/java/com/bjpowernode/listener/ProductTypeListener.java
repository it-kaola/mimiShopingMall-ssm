package com.bjpowernode.listener;

import com.bjpowernode.domain.ProductType;
import com.bjpowernode.service.ProductTypeService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.List;

public class ProductTypeListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        // 手工获取spring容器，在容器中获取ProductTypeService对象
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext_*.xml");
        ProductTypeService productTypeServiceProxy = (ProductTypeService) applicationContext.getBean("productTypeServiceImpl");
        // 获取商品类型列表信息
        List<ProductType> productTypeList = productTypeServiceProxy.getAllProductType();
        // 将商品类型列表信息放入应用域中
        servletContextEvent.getServletContext().setAttribute("productTypeList", productTypeList);
        System.out.println("已将商品类别信息列表放入应用域中");
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
