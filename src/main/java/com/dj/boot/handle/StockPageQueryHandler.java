package com.dj.boot.handle;

import com.dj.boot.common.base.BaseResponse;
import com.dj.boot.common.base.Request;
import com.dj.boot.pojo.User;
import com.dj.boot.pojo.UserDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 查询处理器
 **/
@Slf4j
@Component("stockPageQueryHandler")
public class StockPageQueryHandler extends AbstractHandler<User, UserDto> {

    @Override
    public BaseResponse<UserDto> handle(Request<User> request) {
        System.out.println("stockPageQueryHandler...................");
        return null;
    }
}
