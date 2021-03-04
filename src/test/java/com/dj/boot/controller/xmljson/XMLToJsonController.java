package com.dj.boot.controller.xmljson;

import com.dj.boot.BootDemoApplicationTests;
import com.dj.boot.common.util.xml.XMLTools;
import com.dj.boot.pojo.vo.*;
import com.thoughtworks.xstream.XStream;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName XMLToJsonController
 * @Description TODO
 * @Author wj
 * @Date 2019/12/6 19:50
 * @Version 1.0
 **/
public class XMLToJsonController extends BootDemoApplicationTests {

    @Test
    public void test(){
        UserVo user = new UserVo();
        user.setEmail("1225328916@163.com");
        user.setUserName("wj");

        //创建解析XML对象
        XStream xStream = new XStream();
        //设置别名, 默认会输出全路径
        //声明XStream注解来源
        xStream.processAnnotations(UserVo.class);
        xStream.alias("UserVo", UserVo.class);
        //转为xml
        String xml = xStream.toXML(user);
        System.out.println(xml);

        //xml转对象
        UserVo userVo1 = (UserVo) XMLTools.fromXML(xml, UserVo.class);
        XStream xStreamByDomDriver = XMLTools.getXStreamByDomDriver();
        String xmlTaskContent = XMLTools.generateXMLTaskContent(user);
        XStream xStream1 = XMLTools.getXStream();

        XStream xStreamToJson = new XStream();
        xStreamToJson.alias("UserVo", UserVo.class);
        //转对象
        UserVo userVo = (UserVo)xStreamToJson.fromXML(xml);
        System.out.println(userVo.toString());
    }




    @Test
    public void test1(){
        List<UserVo> userVoList = new ArrayList<>();
        List<Student> studentList = new ArrayList<>();
        XmlFile xmlFile = new XmlFile();
        Header header = new Header();
        Body body = new Body();
        UserVo user1 = new UserVo();
        user1.setEmail("1225328916@163.com");
        user1.setUserName("wj");
        UserVo user2 = new UserVo();
        user2.setEmail("1225328916@163.com");
        user2.setUserName("wj");
        UserVo user3 = new UserVo();
        user3.setEmail("1225328916@163.com");
        user3.setUserName("wj");
        userVoList.add(user1);
        userVoList.add(user2);
        userVoList.add(user3);

        Student student1 = new Student();
        student1.setAge(1);
        student1.setSex("男");
        Student student2 = new Student();
        student2.setAge(2);
        student2.setSex("女");
        Student student3 = new Student();
        student3.setAge(3);
        student3.setSex("男女");
        studentList.add(student1);
        studentList.add(student2);
        studentList.add(student3);

        header.setUserVoList(userVoList);
        body.setStudentList(studentList);
        xmlFile.setBody(body);
        xmlFile.setHeader(header);

        //创建解析XML对象
        XStream xStream = new XStream();
        //xStream对象设置默认安全防护，
        XStream.setupDefaultSecurity(xStream);
        //同时设置允许的类
        xStream.allowTypes(new Class[]{XmlFile.class, Header.class, Body.class, Student.class, UserVo.class});
        //设置别名, 默认会输出全路径
        //声明XStream注解来源
        xStream.processAnnotations(XmlFile.class);
        xStream.alias("XmlFile", XmlFile.class);
        //转为xml
        String xml = xStream.toXML(xmlFile);
        System.out.println(xml);



        System.out.println("start---------------------------XML --------------->    JSON---------------------------");
        XStream xStreamToJson = new XStream();
        //xStream对象设置默认安全防护，
        XStream.setupDefaultSecurity(xStreamToJson);
        //同时设置允许的类
        xStreamToJson.allowTypes(new Class[]{XmlFile.class, Header.class, Body.class, Student.class, UserVo.class});
        xStreamToJson.alias("XmlFile", XmlFile.class);
        xStreamToJson.alias("Header", Header.class);
        xStreamToJson.alias("Body", Body.class);
        xStreamToJson.alias("Student", Student.class);
        xStreamToJson.alias("UserVo", UserVo.class);
        //转对象
        XmlFile xmlFile1 = (XmlFile)xStreamToJson.fromXML(xml);
        System.out.println(xmlFile1.toString());
    }

    @Override
    public void run() {

    }
}
