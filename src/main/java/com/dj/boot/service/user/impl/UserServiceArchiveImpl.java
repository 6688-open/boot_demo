package com.dj.boot.service.user.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dj.boot.annotation.ConcurrentLock;
import com.dj.boot.common.threadpoolutil.ThreadPoolUtils;
import com.dj.boot.common.threadpoolutil.ThreadUtils2;
import com.dj.boot.common.util.collection.CollectionUtils;
import com.dj.boot.configuration.IdGeneratorSnowFlake;
import com.dj.boot.mapper.user.UserMapper;
import com.dj.boot.mapper.useritem.UserItemMapper;
import com.dj.boot.pojo.User;
import com.dj.boot.pojo.UserDto;
import com.dj.boot.pojo.useritem.UserItem;
import com.dj.boot.service.user.UserService;
import org.apache.commons.compress.utils.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * @author ext.wangjia
 */
@Service("userServiceArchiveImpl")
public class UserServiceArchiveImpl extends ServiceImpl<UserMapper, User> implements UserService {


    @Resource
    private UserMapper userMapper;
    @Resource
    private UserItemMapper userItemMapper;

    @Override
    public Long getCount() {
        return userMapper.getCount();
    }



    /**
     * 用户展示
     * @param pageNo  当前页
     * @param user  传递参数
     * @return 返还数据
     * @throws Exception
     */
    @Override
    public Map<String, Object> findUserAll(Integer pageNo, User user) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        Page<User> page = new Page<>(pageNo, 1);
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        if( null != user.getSex()) {
            queryWrapper.eq("sex", user.getSex());
        }
//        if(!StringUtils.isEmpty(user.getUsername())   ) {
//            queryWrapper.and(i -> i.like("username", user.getUsername()).or().like("phone", user.getUsername()).or().like("email",user.getUsername()));
//        }
        queryWrapper.orderByDesc("id");
        IPage<User> iPage = this.page(page, queryWrapper);
        map.put("pageNo", pageNo);
        map.put("list", iPage.getRecords());
        map.put("totalPage", iPage.getPages());
        return map;
    }

    @Override
    public Page<User> findUserList(Page<User> page, UserDto userDto) throws Exception {
        return userMapper.findUserList(page, userDto);
    }

    @Override
    public User findUserByUsernameAndPassword(String userName) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_name", userName);
        //queryWrapper.eq("phone", user.getUsername()).or().eq("email", user.getUsername()).or().eq("username", user.getUsername());
        return this.getOne(queryWrapper);
    }


    /**
     * 创建一个线程执行多个任务
     */
    @Override
    public void threadTest() {
        Date date = new Date();
        List<User> userList = new ArrayList<>();
        User user = new User();
        user.setUserName("1111");
        User user1 = new User();
        user1.setUserName("2222");
        User user2 = new User();
        user2.setUserName("3333");
        System.out.println(Thread.currentThread().getName());
        System.out.println("begin");
        User user3 = this.getById(1);
        userList.add(user);
        userList.add(user1);
        userList.add(user2);
        saveUser(userList, date);
        System.out.println("over");
    }

    private void saveUser(List<User> userList, Date syncCbiTime) {
        ThreadUtils2.getThreadPoolExecutor().submit(new UserSyncSave(userList, syncCbiTime));
    }



    class UserSyncSave implements Callable<Boolean> {

        private List<User> userList;
        private Date syncCbiTime;

        public UserSyncSave(List<User> userList, Date syncTime) {
            this.userList = userList;
            this.syncCbiTime = syncTime;
        }

        @Override
        public Boolean call() throws Exception {
            try {
                System.out.println(Thread.currentThread().getName());
                saveUserSync(userList, syncCbiTime);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return true;
        }
    }


    private void saveUserSync(List<User> userList, Date syncCbiTime){
        //业务逻辑
        this.saveBatch(userList);
    }

















    /**
     * 创建多个线程执行多个任务
     */
    @Override
    public void threadTest1() {
        System.out.println(Thread.currentThread().getName());
        List<User> list = this.list();
        getNameByList(list);
    }



    private void getNameByList(List<User> userList) {
        System.out.println("启动所有核心线程");
        //启动所有核心线程
        //ThreadPoolUtils.getThreadPoolExecutor().prestartAllCoreThreads();
        //开启多个线程执行多个任务
        for (int i = 1; i <= 10; i++) {
            ThreadPoolUtils.getThreadPoolExecutor().submit(new GetUser(userList));
        }
        try {
            System.in.read(); //阻塞主线程
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    class GetUser implements Callable<Boolean> {

        private List<User> userList;

        public GetUser(List<User> userList) {
            this.userList = userList;
        }

        @Override
        public Boolean call() throws Exception {
            try {
                Format f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String format = f.format(new Date());
                System.out.println("当前线程名称-------"+Thread.currentThread().getName() + "时间 " + format);
                Thread.sleep(3000); //让任务执行慢点
                User user1 = getUserById(1);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return true;
        }
    }

    private User getUserById(Integer id){
        return this.getById(id);
    }












    @Autowired
    private IdGeneratorSnowFlake idGeneratorSnowFlake;

    @Override
    public String getIDBySnowFlake() {

        ExecutorService threadPool = Executors.newFixedThreadPool(5);
        for (int i = 1; i <= 20; i++) {
            threadPool.submit(()->{
                System.out.println(idGeneratorSnowFlake.snowFlakeId());
            });
        }
        threadPool.shutdown();
        return "ok";
    }

    @Override
    public List<User> getList() {
        return userMapper.getList();
    }

    @Override
    public List<User> findUserList(Page<User> page, UserDto userDto, User user) {
        List<User> list = Lists.newArrayList();
        User user1 = new User();
        user1.setPassword("111111111111");
        list.add(user1);
        return list;
    }

    @Override
    public Integer userAndItemAdd(User user, UserItem userItem) {
        boolean save = this.save(user);
        System.out.println(user.getId());
        userItem.setUserId(user.getId());
        userItem.setNickName(user.getUserName());
        Integer integer = userItemMapper.userItemAdd(userItem);
        return integer;
    }


    @Override
    public String testConcurrentLock(User user) {
        return " OK ! ";
    }


    @Override
    public Integer updateBatch(List<User> list) {
        return null;
    }
    @Override
    public Integer insertBatch(List<User> list) {
        return null;
    }

    @Override
    public List<User> findUserListByCondition(UserDto userDto) throws Exception {
        return null;
    }

    @Override
    public Integer updateBatchSaleableWarehouseStock(List<User> userList, List<Integer> ids) {
        return null;
    }


}
