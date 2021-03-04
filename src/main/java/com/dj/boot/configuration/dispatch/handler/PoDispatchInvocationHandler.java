package com.dj.boot.configuration.dispatch.handler;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dj.boot.configuration.dispatch.proxy.DispatchSelector;
import com.dj.boot.pojo.User;
import com.dj.boot.pojo.UserDto;
import org.springframework.util.StringUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

/**
 * @author ext.wangjia
 */
public class PoDispatchInvocationHandler implements DispatchInvocationHandler {

    private static final int VALUE = 2020;
    private static final String ARCHIVE = "archive";
    private static final String METHOD_NAME = "findUserList";

    @Override
    public Object invoke(Map<String, Object> candidateTarget, String defaultTarget, Method method, Object[] args) throws Throwable {
        System.out.println(method.getName());

        //特殊处理
        if (METHOD_NAME.equals(method.getName())) {
            return parasData(candidateTarget, defaultTarget, method, args);
        }

        String target = DispatchSelector.selected();
        if (!StringUtils.isEmpty(target)) {
            target = Integer.parseInt(target) >= VALUE ? defaultTarget : ARCHIVE;
        }
        Object targetInstance = candidateTarget.get(StringUtils.isEmpty(target) ? defaultTarget : target);
        return method.invoke(targetInstance, args);
    }



    public Object parasData(Map<String, Object> candidateTarget, String defaultTarget, Method method, Object[] args) throws InvocationTargetException, IllegalAccessException {
        if (args != null && args.length > 0) {
            User u = (User) args[2];
            System.out.println(u.getUserName());

            for (int i = 0; i < args.length; i++) {
                if (i == 0) {
                    Page<User> page = (Page<User>) args[i];
                    System.out.println(page.getCurrent());
                } else if (i==1) {
                    UserDto userDto = (UserDto) args[i];
                    System.out.println(userDto.getEmail());
                } else {
                    User user = (User) args[i];
                    System.out.println(u.getUserName());
                }

            }
        }
        List<User> activeUserList = (List<User>) method.invoke(candidateTarget.get(defaultTarget), args);
        List<User> archiveUserList = (List<User>) method.invoke(candidateTarget.get(ARCHIVE), args);
        System.out.println("activeUserList----------------------------------------");
        activeUserList.forEach(System.out::println);
        System.out.println("archiveUserList----------------------------------------");
        archiveUserList.forEach(System.out::println);
        return activeUserList;
    }
}
