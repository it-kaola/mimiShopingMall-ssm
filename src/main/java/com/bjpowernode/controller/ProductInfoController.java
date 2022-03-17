package com.bjpowernode.controller;

// 商品信息控制器对象

import com.bjpowernode.domain.ProductInfo;
import com.bjpowernode.service.ProductInfoService;
import com.bjpowernode.utils.DateFormat;
import com.bjpowernode.utils.FileNameUtil;
import com.bjpowernode.vo.ProductInfoVo;
import com.github.pagehelper.PageInfo;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/prod")
public class ProductInfoController {

    public static final int PAGE_SIZE = 5;

    // 商品图片的名称
    private String imageFileName = "";


    // 业务层访问对象
    @Autowired
    private ProductInfoService productInfoServiceProxy; // 实际上获取的是代理对象，jdk动态代理



    // 获取全部商品的信息（不分页）
    @RequestMapping("/getAllProduct.action")
    public String getAllProduct(HttpServletRequest request){
        List<ProductInfo> productInfoList = productInfoServiceProxy.getAllProduct();

        // 将查询到的商品信息列表放到请求域中
        request.setAttribute("list", productInfoList);

        return "product"; // 请求转发到product.jsp页面
    }



    // 点击"商品管理"时，展示第一页商品信息；或更新商品时，停留在当前页
    @RequestMapping("/split.action")
    public String split(HttpServletRequest request){

        PageInfo<ProductInfo> pageInfo = null;

       ProductInfoVo productInfoVo = (ProductInfoVo) request.getSession().getAttribute("productInfoVo");

        if(productInfoVo != null){
            // 说明是需要进行分页操作的
            pageInfo = productInfoServiceProxy.splitPageVo(productInfoVo, PAGE_SIZE);
            // 清空session域中的这个对象，避免造成其他影响，减轻内存
            // request.getSession().removeAttribute("productInfoVo");
        }else{
            // 获取第1页的数据
            pageInfo = productInfoServiceProxy.split(1, PAGE_SIZE);
        }

        request.setAttribute("info", pageInfo);

        return "product";
    }



    // 点击页码按钮时，进行分页展示商品信息的功能
    @RequestMapping("/ajaxsplit.action")
    @ResponseBody // 只要不进行视图跳转，就得加这个注解
    public void ajaxsplit(HttpSession session, ProductInfoVo productInfoVo){
        // 取得当前页的商品信息数据
        PageInfo<ProductInfo> pageInfo = productInfoServiceProxy.splitPageVo(productInfoVo, PAGE_SIZE);

        session.setAttribute("info",  pageInfo); // 使用session是因为当表格重新刷新table时，是一次全新的请求了，此时request的对象已经被销毁了
    }



    // 多条件查寻商品信息
    @RequestMapping("/selectProductInfoByConditions.action")
    @ResponseBody
    public void selectProductInfoByConditions(HttpSession session, ProductInfoVo productInfoVo){
        List<ProductInfo> productInfoList = productInfoServiceProxy.selectProductInfoByConditions(productInfoVo);
        session.setAttribute("list", productInfoList);
    }



    // 异步ajax上传图片
    @ResponseBody
    @RequestMapping("/ajaxImg.action")
    public Object ajaxImg(HttpServletRequest request, MultipartFile pimage){

        // 通过UUID获取32位的图片文件名称
        String imageName = FileNameUtil.getUUIDFileName();
        // 根据上传图片的原始名称，获取后缀名
        String type = FileNameUtil.getFileType(pimage.getOriginalFilename()); // pimage.getOriginalFilename()获取的是上传图片的原始名称
        // 将以上两部分进行字符串的拼接，获得完整的图片文件名
        imageFileName = imageName + type;

        // 得到项目中图片存储的路径
        String savePath = request.getSession().getServletContext().getRealPath("/image_big"); // 获取绝对路径
        System.out.println(savePath + File.separator + imageFileName);

        // 转存
        try {
            pimage.transferTo(new File(savePath + File.separator + imageFileName)); // File.separator 为 "\"
            // savePath + File.separator + imageFileName 即为：H:\WKCTO-Java\MimiShopingMall\code\mimi_shopping_mall_ssm\src\main\webapp\image_big\easduifchudf9031238jh.jpg
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 返回客户端JSON数据，封装图片的路径，为了在页面实现立即回显
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("imgurl", imageFileName);

        return jsonObject.toString();
    }



    // 增加商品
    @RequestMapping("/saveProduct.action")
    public String saveProduct(HttpServletRequest request, ProductInfo productInfo){
        // 需要赋值的就是商品的图片信息和增加商品信息时的时间
        productInfo.setpDate(new Date());
        productInfo.setpImage(imageFileName);

        boolean flag = productInfoServiceProxy.saveProductInfo(productInfo);

        if(! flag){
            request.setAttribute("msg", "添加商品失败");
        }else{
            request.setAttribute("msg", "添加商品成功");
        }

        // 清空imageFileName变量中的内容，为了在更新商品信息时，方便判断是否修改了商品的图片
        imageFileName = "";

        return "forward:/prod/split.action"; // 转发到商品第一页
    }



    // 显示商品详细信息
    @RequestMapping("/showProductDetail.action")
    public String showProductDetail(HttpServletRequest request, ProductInfoVo productInfoVo, Integer pid){

        ProductInfo productInfo = productInfoServiceProxy.getProductInfoById(pid);

        request.setAttribute("prod", productInfo);

        request.getSession().setAttribute("productInfoVo", productInfoVo);

        return "update";
    }



    // 更新商品信息
    @RequestMapping("/updateProduct.action")
    public String updateProduct(HttpServletRequest request, ProductInfo productInfo){

        if(! "".equals(imageFileName)){
            // 如果imageFileName不为空，证明用户更改了商品信息的图片，出发了异步上传文件的请求
            productInfo.setpImage(imageFileName);
        }
        // 如果为空，证明用户没有更改商品信息的图片，直接从隐藏域中取pImage属性的值自动注入即可

        boolean flag = productInfoServiceProxy.updateProductById(productInfo);

        if(flag){
            request.setAttribute("msg","更新商品信息成功!");
        }else{
            request.setAttribute("msg","更新商品信息失败!");
        }

        // 清空imageFileName属性的内容，方便下次做判断
        imageFileName = "";

        return "forward:/prod/split.action";
    }



    // 删除单个商品
    @RequestMapping("/deleteProductInfo.action")
    public String deleteProductInfo(HttpServletRequest request, ProductInfoVo productInfoVo, int pid){

        boolean flag = productInfoServiceProxy.deleteProductInfo(pid);

        if(flag){
            request.setAttribute("msg", "删除商品成功！");
            request.getSession().setAttribute("productInfoVo", productInfoVo);
        }else{
            request.setAttribute("msg", "删除商品失败！");
        }

        return "forward:/prod/deleteSplitAjax.action";
    }



    // 批量删除
    @RequestMapping("/deleteBatch.action")
    public String deleteBatch(HttpServletRequest request, ProductInfoVo productInfoVo, String ids){
        // 将字符串转为数组，以逗号分割
        String[] idArray = ids.split(",");

        boolean flag = productInfoServiceProxy.deleteBatch(idArray);

        if(flag){
            request.setAttribute("msg", "批量删除成功！");
            request.getSession().setAttribute("productInfoVo", productInfoVo);
        }else{
            request.setAttribute("msg", "批量删除失败！");
        }
        return "forward:/prod/deleteSplitAjax.action";
    }



    // 删除商品，停留在当前页的相关操作
    @RequestMapping(value = "/deleteSplitAjax.action", produces = "text/pain;charset=UTF-8")
    @ResponseBody
    public String deleteSplitAjax(HttpSession session, HttpServletRequest request){

        PageInfo<ProductInfo> pageInfo = null;

        ProductInfoVo productInfoVo = (ProductInfoVo) request.getSession().getAttribute("productInfoVo");

        if(productInfoVo != null){
            pageInfo = productInfoServiceProxy.splitPageVo(productInfoVo, PAGE_SIZE);
        }else{
            pageInfo = productInfoServiceProxy.split(1, PAGE_SIZE);
        }

        session.setAttribute("info", pageInfo);

        return (String) request.getAttribute("msg");
    }







}
