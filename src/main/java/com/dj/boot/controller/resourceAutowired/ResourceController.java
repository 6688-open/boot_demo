package com.dj.boot.controller.resourceautowired;

import com.dj.boot.service.resourceAutowride.Person;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ext.wangjia
 */
@Api(value = "测试 @Resource  @Autowired")
@RestController
@RequestMapping("/resource/")
public class ResourceController {


    /**
     *   1 @Resource 和@Autowired 都可以作为注入属性的修饰，
     *     在接口仅有单一实现类时，两个注解的修饰效果相同，可以互相替换，不影响使用。
     *
     *
     *    2 @Resource是Java自己的注解，@Resource有两个属性是比较重要的，分是name和type；
     *      Spring将@Resource注解的name属性解析为bean的名字，而type属性则解析为bean的类型。
     *      所以如果使用name属性，则使用byName的自动注入策略，而使用type属性时则使用byType自动注入策略。
     *      如果既不指定name也不指定type属性，这时将通过反射机制使用byName自动注入策略。
     *
     *    3 @Autowired是spring的注解，是spring2.5版本引入的，Autowired只根据type进行注入，不会去匹配name。
     *      如果涉及到type无法辨别注入对象时，那需要依赖@Qualifier或@Primary注解一起来修饰。
     */

    /**
     * 1 当借口只有一个实现类时  @Autowired    @Resource 都可以
     *
     * 2 当有两个实现类时
     *              1 @Resource 启动报错 No qualifying bean of type 'com.dj.boot.service.resourceAutowride.Person
     *              2  @Autowired Field person in com.dj.boot.controller.resourceAutowride.ResourceController required a single bean, but 2 were found
     *
     *      使用    @Resource
     *
     *              . @Resource
     *              . @Qualifier("manImpl")
     *               private Person person;
     *
     *               或者
     *               .@Resource(name = "manImpl")    实现类的bean 名称
     *                private Person person;
     *               。@Resource(name = "womanImpl")
     *                 private Person person2;
     *
     *
     *      使用@Autowired
     *
     *                   .@Autowired
     *                   .@Qualifier("manImpl")
     *                    private Person person;
     *
     *              .@Primary是修饰实现类的，告诉spring，如果有多个实现类时，优先注入被@Primary注解修饰的那个。
     *
     */

    //@Autowired
    @Autowired
    private Person person;




    @ApiOperation(value = "测试 @Resource  @Autowired")
    @GetMapping("test")
    public String testUser(){
        String s = person.runMarathon();
        System.out.println(s);
        return "1";
    }


}
