package com.dj.boot.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dj.boot.annotation.Login;
import com.dj.boot.aspect.holder.LoginContext;
import com.dj.boot.aspect.holder.LoginUserContextHodler;
import com.dj.boot.aspect.holder.PermissionContext;
import com.dj.boot.btp.exception.ParamException;
import com.dj.boot.common.base.BaseResponse;
import com.dj.boot.common.base.Response;
import com.dj.boot.common.base.ResultModel;
import com.dj.boot.common.util.HttpRequestUtil;
import com.dj.boot.common.util.LogUtils;
import com.dj.boot.common.util.SpringContextUtil;
import com.dj.boot.common.util.ValidatorUtil;
import com.dj.boot.common.util.md5.PasswordSecurityUtil;
import com.dj.boot.configuration.dispatch.proxy.ProxyDispatch;
import com.dj.boot.pojo.ScheduleJob;
import com.dj.boot.pojo.TenantDto;
import com.dj.boot.pojo.User;
import com.dj.boot.pojo.UserDto;
import com.dj.boot.pojo.useritem.UserItem;
import com.dj.boot.pojo.validate.GroupInterface;
import com.dj.boot.service.async.AsyncService;
import com.dj.boot.service.user.UserService;
import com.dj.boot.service.user.impl.UserAdapter;
import com.google.common.base.Preconditions;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.compress.utils.Lists;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.formula.functions.T;
import org.redisson.misc.Hash;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.swing.text.Keymap;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author ext.wangjia
 */
@Api(value = "用户操作接口")
@RestController
@RequestMapping("/user/")
public class UserController {


    @Autowired
    private UserService userService;

    @Autowired
    private Environment environment;

    @Autowired
    private AsyncService asyncService;

    @Value("${qiniu.url}")
    private String qiniuUrl;

    @Value("${actualFlag}")
    private Boolean actualFlag;

    @Value("${jsf.alias}")
    private String alias;

    @Resource
    private UserAdapter userAdapter;
    /**
     * 假如配置文件里面有edu.loginHost 则从配置文件里读取
     * 否则就是默认值 http://xuexizhongxin.wuliujie.com/interfaces/login
     */
    @Value("${edu.loginHost:http://xuexizhongxin.wuliujie.com/interfaces/login}")
    private String loginHost;

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);


    DateTimeFormatter pattern = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * 测试
     * @return 返回数据
     */
    @ApiOperation(value = "测试")
    @PostMapping("test")
    @Login
    public Response<User> test(@RequestBody User user) throws Exception {

        if (logger.isInfoEnabled()) {
            logger.info("UserController --> test:{}", JSONObject.toJSONString(user));
        }

        if (Objects.nonNull(user.getOperateTime())) {
            Date operateTime = user.getOperateTime();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            System.out.println("把当前时间转换成字符串：" + sdf.format(operateTime));
        }

        Response<User>  response = Response.success();
        try {
            Response<String> generateOrderResponse = userAdapter.generateOrder();
            if (generateOrderResponse.isSuccess() && null != generateOrderResponse.getData()) {
                logger.info(generateOrderResponse.getData());
            }
            response.setData(user);
        } catch (Exception e) {
            logger.info(e.getMessage());
            return Response.error(Response.ERROR_BUSINESS, "系统异常");
        }
        return response;
    }

    @PostMapping("updateBatchSaleableWarehouseStock")
    public Response<User> updateBatchSaleableWarehouseStock() throws Exception {

        Response<User>  response = Response.success();
        List<User> list = userService.findUserListByCondition(new UserDto());
        List<Integer> ids = list.stream().map(User::getId).collect(Collectors.toList());
        Integer count = userService.updateBatchSaleableWarehouseStock(list, ids);
        logger.error("修改成功改的数量{}:", count);
        return response;
    }



    /**
     *
     *  @ProxyDispatch("alien") 代理
     * @param user2
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "从配置文件里读取信息--- 用户列表", notes="从配置文件里读取信息--- 用户列表")
    @GetMapping("userList")
    @ProxyDispatch("alien")
    public ResultModel userList(@RequestBody @Validated(GroupInterface.class) User user2 ) throws Exception {
        //通过beanID 从IOC 获取bean  在controller 调用 里面的方法
        User user1 = SpringContextUtil.getBean("userController", UserController.class).getById(1);
        System.out.println(user1);


        //存储登录信息 权限信息
        if (LoginUserContextHodler.get().getPermissionContext() == null) {
           logger.error("error");
        }
        //获取上下文
        PermissionContext permissionContext = LoginUserContextHodler.get().getPermissionContext();
        LoginContext loginContext = LoginUserContextHodler.get().getLoginContext();

        /**
         * 校验入参
         */
        ResultModel resultModel = validComponent(user2);
        if (!resultModel.isResult()) {
            return resultModel;
        }

        Page<User> page = new Page<>();
        //设置当前页
        page.setCurrent(1);
        //每页条数
        page.setSize(10);

        UserDto userDto = new UserDto();
        userDto.setEmail("183@163.com");
        userDto.setId(2);
        //e, UserDto userDto

        User u = new User();
        u.setUserName("wangjia");
        u.setPassword("111111");

        List<User> userList = userService.findUserList(page, userDto, u);
        userList.forEach(user -> u.setPassword("222222"));

        Map<String, Object> map = new HashMap<>();
        map.put("userList", userList);
        map.put("111", "22222");


        //获取配置文件里的内容
        String carbonCallInterface = environment.getProperty("commercial.test");
        System.out.println(qiniuUrl);
        System.out.println(alias);
        System.out.println(actualFlag);
        System.out.println("loginHost: "+loginHost);
        System.out.println(carbonCallInterface);
//        List<User> list = userService.list();
//        User user = new User();
//        BeanUtils.copyProperties(userVo, user);
//        Integer pageNo = 1;
//        return new ResultModel<>().success(userService.findUserAll(pageNo, user));
        return new ResultModel<>().success(map);
    }







    /**
     * user列表展示
     * @return 返回数据
     */
    @ApiOperation(value = "登录")
    @PostMapping("login")
    public ResultModel<Object> login(HttpServletRequest request, String userName, String password){

        String deptPo = request.getParameter("deptPo");
        String ipAddress = HttpRequestUtil.getIpAddress(request);
        System.out.println(ipAddress+"--------------------------------------------");
        Map<String, Object> map = new HashMap<>(16);
        try {
            User user = userService.findUserByUsernameAndPassword(userName);
            if (Objects.isNull(user)){
                return new ResultModel<>().success(201 , "fail");
            }
            //加盐    MD5  加盐 判断密码是否一致
            String salt = user.getSalt();
            //前台加密后 +  盐  ----->加密    最终密码
            String pwd = PasswordSecurityUtil.generatePassword(password, salt);
            if(!user.getPassword().equals(pwd)) {
                return new ResultModel<>().error("密码输入错误");
            }
            return new ResultModel<>().success(200,"success",map);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResultModel<>().error("异常"+e.getMessage());
        }
    }



    private void checkParam(User user){
        Preconditions.checkArgument(user != null, "参数不能为空");
        Preconditions.checkArgument(StringUtils.isNotEmpty(user.getUserName()),"用户名不为空");
    }

    /**
     * 参数校验
     * @param user
     * @return
     */
    private ResultModel<Object> validComponent (User user) {
        //只会校验加了GroupInterface标识的字段属性
        //ValidatorUtil.Result result = ValidatorUtil.validate(user, GroupInterface.class);
        //只会校验原生的 不加任何标识 不会校验加了GroupInterface 。的字段属性
        ValidatorUtil.Result result = ValidatorUtil.validate(user);
        if (!result.isPass()) {
            logger.error("用户校验结果："+ StringUtils.join(result.getErrMsgList(), ";"));
            return new ResultModel<>().error(201,StringUtils.join(result.getErrMsgList(), ";"));
        }
        return new ResultModel<>().success(200);
    }




    /**
     * user列表展示
     * @return 返回数据
     */
    @ApiOperation(value = "注册")
    @PostMapping("register")
    public ResultModel register(User user){
        //1、入参校验
        ResultModel resultModel = validComponent(user);
        if (!resultModel.isResult()) {
            return resultModel;
        }
        //非空校验
        checkParam(user);
        Map<String, Object> map = new HashMap<>();
        try {
            //生成保存盐
            String salt = PasswordSecurityUtil.generateSalt();
            String overPassword = PasswordSecurityUtil.generatePassword(user.getPassword(), salt);
            user.setPassword(overPassword);
            user.setSalt(salt);
            userService.save(user);
            return new ResultModel().success(205,"success",map);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResultModel().error("异常"+e.getMessage());
        }
    }





    /**
     * 注册去重
     * @param userName 用户名
     * @param phone  手机号
     * @param email  邮箱
     * @return
     */
    @PostMapping("uniq")
    public Boolean uniq(String userName, String phone, String email){
        try{
           // User user = userService.findUserToUniq(userName, phone, email);
            //判断是否有值
//            if(null != user){
//                return false;//失败
//            }
            return true;//成功
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }


    /**
     * user列表展示
     * @return 返回数据
     */
    @ApiOperation(value = "mapper 用户列表", notes="用户列表")
    @PostMapping("list")
    public ResultModel list(UserDto userDto){

        userDto.setCreateTime(LocalDateTime.now());

        userDto.setReturnFlag(false);
        userDto.setSex(3);



        int pageNo = 1;
        int pageSize = 10;
        Map<String, Object> map = new HashMap<>();
        try {
            Page<User> page = new Page<>();
            //设置当前页
            page.setCurrent(pageNo);
            //每页条数
            page.setSize(pageSize);
            Page<User> pageInfo = userService.findUserList(page, userDto);
            //分页结果
            map.put("list", pageInfo.getRecords());
            map.put("totalPage", pageInfo.getPages());
            map.put("pageNo", pageNo);

            return new ResultModel().success(200,"success",map);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResultModel().error("异常"+e.getMessage());
        }
    }


    /**
     * 批量修改
     * @return 返回数据
     */
    @ApiOperation(value = "批量修改", notes="批量修改")
    @PostMapping("batchUpdate")
    public Response batchUpdate(){
        Response<Object> response = Response.success(Response.SUCCESS_CODE, Response.SUCCESS_MESSAGE);

        List<User> list = Lists.newArrayList();
        User user = new User().setUserName("wj-01").setPassword("123-01").setId(11146);
        User user1 = new User().setUserName("wj-02").setPassword("123-02").setId(11147);
        list.add(user);
        list.add(user1);
        Integer integer = userService.updateBatch(list);

        if (integer < 0) {
            response.setCode(Response.ERROR_BUSINESS);
            return response;
        }
        return response;
    }


    /**
     * 批量添加
     * @return 返回数据
     */
    @ApiOperation(value = "批量添加", notes="批量添加")
    @PostMapping("insertBatch")
    public Response insertBatch(){
        Response<Object> response = Response.success();

        List<User> list = Lists.newArrayList();
        User user = new User().setUserName("insertBatch-wj-01").setPassword("insertBatch-123-01").setId(11146).setSex(1).setSalt("12345fgh45").setCreateTime(LocalDateTime.now());
        User user1 = new User().setUserName("insertBatch-wj-02").setPassword("insertBatch-123-02").setId(11147).setSex(2).setSalt("12345fgh45").setCreateTime(LocalDateTime.now());
        list.add(user);
        list.add(user1);
        Integer integer = userService.insertBatch(list);

        if (integer < 0) {
            response.setCode(Response.ERROR_BUSINESS);
            return response;
        }
        return response;
    }







    @ApiOperation(value = "测试return finally执行顺序", notes="测试return finally执行顺序")
    @GetMapping("testFinally")
    public List<User> testFinally(){
        //先执行return，把返回结果保存在返回结果区域，并没有返回，再执行finally，最后把保存在结果区域的结果返回给调用者
        List<User> list = null;
        try {
            list = userService.list();
            list.stream().map(user -> user.setCreateTimeStr(pattern.format(user.getCreateTime()))).collect(Collectors.toList());
            LogUtils.info("return1111");
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            LogUtils.info("finally");

        }
        LogUtils.info("return22222");
        return list;
    }


    @ApiOperation(value = "异步回调", notes="异步回调")
    @GetMapping("testCompletableFuture")
    public User testCompletableFuture(){
        System.out.println("start");
        User byId = userService.getById(1);
        CompletableFuture<Void> completableFuture = CompletableFuture.runAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            User user = new User().setUserName("异步").setSex(33);
            userService.save(user);
            System.out.println(Thread.currentThread().getName() + "\t" + "添加成功");
        });

        System.out.println(222);

        return byId;
    }









    @ApiOperation(value = "测试@Async", notes="测试@Async")
    @GetMapping("testAsync")
    public String testAsync(){
        LocalDateTime beginTime = LocalDateTime.now();
        //     @Async 在主线程里打断点   断点走的是主线程 就算异步里面打了断点也不会进断点
        //            直接在异步里面打断点  断点肯定会走  走的不是主线程  走的是另一个新开启的线程
        //      @Async  不能在一个类里面   启动类加上 @EnableAsync开启异步
        //      @Async  新开一个线程去执行 不会影响主线程
        String s = asyncService.testAsync();
        LocalDateTime endTime = LocalDateTime.now();
        LogUtils.info("time" + (endTime.getSecond()-beginTime.getSecond()));
        return s;
    }


    @ApiOperation(value = " 根据id 获取数据", notes="根据id 获取数据")
    @GetMapping("getById")
    public User getById(Integer id){
        User user = userService.getById(id);
        return user;
    }


    /**
     * 测试 @Transactional 注解
     * @return
     */
    @ApiOperation(value = " 添加User和item", notes="添加User和item")
    @GetMapping("addUserAndItem")
    public User addUserAndItem(){
        User user = new User();
        user.setUserName("wj").setPassword("11111").setSex(1).setSalt("333333");
        UserItem userItem = new UserItem();
        userService.userAndItemAdd(user, userItem);
        return user;
    }



    /**
     * 非空校验
     * @return 返回数据
     */
    @ApiOperation(value = "测试")
    @PostMapping("testParam")
    public Response<User> testParam(){
        Response<User>  response = Response.success(Response.SUCCESS_CODE, Response.SUCCESS_MESSAGE);
        User user = new User();
        user.setSex(2);
        try {
            String[] fileCode = {"id","sex", "userName","password"};
            String[] fileName = {"主键","性别","名字","密码"};
            this.fieldMustCheck(user,fileCode,fileName);
            response.setData(user);
        } catch (ParamException e) {
            logger.info(e.getMessage());
            return Response.error(Response.ERROR_BUSINESS, e.getMessage());
        } catch (Exception e) {
            logger.info(e.getMessage());
            return Response.error(Response.ERROR_BUSINESS, "系统异常");
        }
        return response;
    }


    /**
     * 校验必填项
     * @param
     */
    public void fieldMustCheck( User user, String[] fileCodes, String[] fileNames) {
        try {
            if (fileCodes.length > 0) {
                for (int i = 0; i < fileCodes.length; i++) {
                    Field tbField = user.getClass().getDeclaredField(fileCodes[i]);
                    tbField.setAccessible(true);
                    if (isNullOrEmpty(tbField.get(user))) {
                        throw new ParamException(400, fileNames[i] + " 不可为空!");
                    }
                }
            }
        } catch (Exception e) {
            throw  new ParamException(400, e.getMessage());
        }
    }
    /**
     * 字段为空校验
     * @param object
     * @return
     */
    private boolean isNullOrEmpty(Object object) {
        if (object == null) {
            return true;
        }
        if (object instanceof String && StringUtils.isBlank((String) object)) {
            return true;
        }
        return false;
    }


    /**
     * 根据商家编号获取事业部信息列表，开通预收预付单的事业部
     * @param id   商家id  事业部id  仓库id  承运商id
     * @param type  seller dept  warehouse   shopId  商家--事业部--店铺/仓库  三级联动
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "getTenantId.do")
    @ResponseBody
    public Response getTenantId(String id, String type) throws Exception {
        Response response = Response.success();
        if (StringUtils.isBlank(id) || StringUtils.isBlank(type)) {
            response.setCode(BaseResponse.ERROR_PARAM);
            response.setMsg("参数id: ["+ id + "] && type: [" + type +"] 不允许为空");
            return response;
        }
        List<TenantDto> list = Lists.newArrayList();
        if (StringUtils.equals(type, "city")) {
            TenantDto dto1 = new TenantDto();
            dto1.setId("100000");
            dto1.setName("南京");
            TenantDto dto2 = new TenantDto();
            dto2.setId("200000");
            dto2.setName("上海");
            TenantDto dto3 = new TenantDto();
            dto3.setId("300000");
            dto3.setName("北京");
            list.add(dto1);
            list.add(dto2);
            list.add(dto3);
        }
        if (StringUtils.equals(type, "seller")) {
            TenantDto dto1 = new TenantDto();
            dto1.setId("sellerId001");
            dto1.setName("sellerName001");
            TenantDto dto2 = new TenantDto();
            dto2.setId("sellerId002");
            dto2.setName("sellerName002");
            list.add(dto1);
            list.add(dto2);
        }
        if (StringUtils.equals(type, "dept")) {
            TenantDto dto1 = new TenantDto();
            dto1.setId("deptId001");
            dto1.setName("deptName001");
            TenantDto dto2 = new TenantDto();
            dto2.setId("deptId002");
            dto2.setName("deptName002");
            list.add(dto1);
            list.add(dto2);
        }
        if (StringUtils.equals(type, "warehouse")) {
            TenantDto dto1 = new TenantDto();
            dto1.setId("warehouse001");
            dto1.setName("warehouse001");
            TenantDto dto2 = new TenantDto();
            dto2.setId("warehouse002");
            dto2.setName("warehouse002");
            list.add(dto1);
            list.add(dto2);
        }
        response.setData(list);
        return response;
    }




    /**
     * 获取商家信息列表 开通预收预付单的事业部
     *
     * @return errors
     */
    @RequestMapping(value = "getAllSeller.do")
    @ResponseBody
    public Object getAllSeller(String sellerName,String query) throws Exception {
        if(StringUtils.isBlank(sellerName)){
            sellerName = query;
        }

        logger.error("开始获取商家权限信息列表：" + sellerName);
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();


        try {
            Map<String, String> map1 = new HashMap<String, String>();
            map1.put("id", "111");
            map1.put("sellerName", "商家A");

            Map<String, String> map2 = new HashMap<String, String>();
            map2.put("id", "222");
            map2.put("sellerName", "商家B");

            Map<String, String> map3 = new HashMap<String, String>();
            map3.put("id", "333");
            map3.put("sellerName", "商家C");

            list.add(map1);
            list.add(map2);
            list.add(map3);
        } catch (Exception e) {
            logger.error("查询承运商接口异常",e);
        }

        return list;
    }







}
