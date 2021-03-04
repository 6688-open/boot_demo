package com.dj.boot.aspect.expressionparser;

import com.dj.boot.pojo.User;
import com.dj.boot.pojo.UserDto;
import org.junit.Test;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

/**
 * @ProjectName: boot_demo
 * @PackageName: com.dj.boot.aspect.expressionparser
 * @User: ext.wangjia
 * @Author: wangJia
 * @Date: 2020-09-04-14-58
 */
public class ExpressionParserTest {
    ExpressionParser parser = new SpelExpressionParser();

    @Test
    public void test () {

        Expression exp = parser.parseExpression("'put spel expression here'");
        String msg = exp.getValue(String.class);
    }


    @Test
    public void test1 () {
        User user = new User();
        user.setId(1);
        StandardEvaluationContext context = new StandardEvaluationContext(user);

        //display the value of item.name property
        Expression exp = parser.parseExpression("id");
        String id = exp.getValue(context, String.class);
    }



    @Test
    public void test2 () {
        User user = new User();
        user.setId(1);


        UserDto userDto = new UserDto();
        userDto.setId(2);



        StandardEvaluationContext context = new StandardEvaluationContext();
        context.setVariable("user", user);
        context.setVariable("userDto", userDto);


        //display the value of item.name property
        Expression exp = parser.parseExpression("#user.id");
        Expression expDto = parser.parseExpression("#userDto.id");
        String id = exp.getValue(context, String.class);
        String idDto = expDto.getValue(context, String.class);
    }

    @Test
    public void test3 () {
        //method invocation
        Expression exp2 = parser.parseExpression("'Hello World'.length()");
        int msg2 = (Integer) exp2.getValue();
        System.out.println(msg2);
    }

    @Test
    public void test4 () {
        //Mathematical operators
        Expression exp3 = parser.parseExpression("100 * 2");
        int msg3 = (Integer) exp3.getValue();
        System.out.println(msg3);
    }
    @Test
    public void test5 () {
        User user = new User();
        user.setId(1);
        user.setUserName("wj");
        StandardEvaluationContext context = new StandardEvaluationContext(user);
        //test if item.name == 'mkyong'
        Expression exp5 = parser.parseExpression("id == 2");
        Expression exp6 = parser.parseExpression("userName == 'wj'");
        boolean msg5 = exp5.getValue(context, Boolean.class);
        boolean msg6 = exp6.getValue(context, Boolean.class);
        System.out.println(msg5);
        System.out.println(msg6);
    }
}
