package com.dj.boot.common.excel.easyExcel;

import com.dj.boot.common.file.FileCreateUtil;
import org.junit.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class EasyTest {
    @Test
    public void test() throws IOException {
        //读取数据
        readExcel();
        //写入数据
        //writeExcel();
    }

    private  void readExcel() throws IOException {
        InputStream inputStream = this.getClass().getResourceAsStream("/template/xlsx/User.xlsx");
        //InputStream inputStream = new FileInputStream("D:/user.xlsx");
        try {
            List<User> users = EasyExcelUtil.readExcel(new BufferedInputStream(inputStream), User.class);
            System.out.println(users);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void writeExcel() throws FileNotFoundException {
        File file = FileCreateUtil.fileExcelCreate("wj");
        EasyExcelUtil.writeExcel(file, data());

        //打印一下  一般获取到文件上传文件服务器
        InputStream inputStream = new FileInputStream(file);
        List<User> users = EasyExcelUtil.readExcel(new BufferedInputStream(inputStream), User.class);
        users.forEach(System.out::println);
    }


    private static List<User> data() {
        List<User> list = new ArrayList<User>();
        for (int i = 0; i < 10; i++) {
            User user = new User();
            user.setName("name" + i);
            user.setBirthday("2019/10/10");
            user.setNickName("NickName" + i);
            user.setPassword("2222222");
            list.add(user);
        }
        return list;
    }
}
