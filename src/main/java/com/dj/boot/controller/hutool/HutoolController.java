package com.dj.boot.controller.hutool;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.lang.Validator;
import cn.hutool.core.util.IdcardUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.asymmetric.RSA;
import cn.hutool.crypto.symmetric.AES;
import cn.hutool.extra.qrcode.QrCodeUtil;
import cn.hutool.extra.qrcode.QrConfig;
import cn.hutool.poi.word.Word07Writer;
import io.swagger.annotations.ApiOperation;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.KeyPair;
import java.util.Date;

@RestController
@RequestMapping("/hutool/")
public class HutoolController {


    /**
     * 导出 word
     * @param response
     * @throws IOException
     */
    @GetMapping("contextLoads")
    public void contextLoads(HttpServletResponse response) throws IOException {

        // 告诉浏览器用什么软件可以打开此文件
        response.setContentType("multipart/form-data");
        response.setCharacterEncoding("utf-8");
        response.setHeader("Content-Disposition", "attachment;filename="+ URLEncoder.encode("test.docx", "utf-8"));

        Word07Writer writer = new Word07Writer();

        // 添加段落（标题）
        writer.addText(new Font("方正小标宋简体", Font.PLAIN, 22), "我是第一部分", "我是第二部分");
        // 添加段落（正文）
        writer.addText(new Font("宋体", Font.PLAIN, 22), "我是正文第一部分", "我是正文第二部分");
         // 写出到文件
        //writer.flush(FileUtil.file("e:/wordWrite.docx"));
        //写出到浏览器
        writer.flush(response.getOutputStream());
        // 关闭
        writer.close();
    }



    public static void main(String[] args) {
        String format = DateUtil.format(new Date(), "yyyy-MM-dd");
        System.out.println(format);

        //SecureUtil.generateKeyPair();
//        KeyPair pair = SecureUtil.generateKeyPair("1234567890");
//        AES aes = SecureUtil.aes();
//        RSA rsa = SecureUtil.rsa();
        /**
         * 身份证验证
         */
        //idCard();
        /**
         * 二维码识别
         */
        //QrCodeUtilTest();

        validator();
    }

    private static void validator() {
        boolean email = Validator.isEmail("1111@163.com");
        boolean mobile = Validator.isMobile("86-18351867657");
        System.out.println(email);
    }

    /**
     * 身份证校验
     */
    private static void idCard() {
        String ID_18 = "320321199406020233";
        String ID_15 = "150102880730303";

        //是否有效
        boolean valid = IdcardUtil.isValidCard(ID_18);
        boolean valid15 = IdcardUtil.isValidCard(ID_15);

        //转换
        String convert15To18 = IdcardUtil.convert15To18(ID_15);
        Assert.assertEquals(convert15To18, "150102198807303035");

        //年龄
        DateTime date = DateUtil.parse("2020-06-02");

        int age = IdcardUtil.getAgeByIdCard(ID_18, date);
        //Assert.assertEquals(age, 26);

        int age2 = IdcardUtil.getAgeByIdCard(ID_15, date);
       // Assert.assertEquals(age2, 28);

        //生日
        String birth = IdcardUtil.getBirthByIdCard(ID_18);
        //Assert.assertEquals(birth, "19781216");

        String birth2 = IdcardUtil.getBirthByIdCard(ID_15);
       // Assert.assertEquals(birth2, "19880730");

//省份
        String province = IdcardUtil.getProvinceByIdCard(ID_18);
        //Assert.assertEquals(province, "江苏");

        String province2 = IdcardUtil.getProvinceByIdCard(ID_15);
        //Assert.assertEquals(province2, "内蒙古");
    }

    /**
     * 二维码识别
     */
    private static void QrCodeUtilTest() {
        //附带logo
                QrCodeUtil.generate(

                "https://git.lug.ustc.edu.cn/wj_001-group/demo_boot", //二维码内容
                QrConfig.create().setImg("d:/logo_small.jpg"), //附带logo
                FileUtil.file("d:/qrcodeWithLogo.jpg")//写出到的文件
        );
        /**
         * 生成二维码
         */
        QrCodeUtil.generate("https://git.lug.ustc.edu.cn/wj_001-group/demo_boot", 300, 300, FileUtil.file("d:/qrcode.jpg"));
        System.out.println(111);
        //识别二维码   https://git.lug.ustc.edu.cn/wj_001-group/demo_boot
        String decode = QrCodeUtil.decode(FileUtil.file("d:/qrcode.jpg"));
    }


    /**
     * cesi
     */










}
