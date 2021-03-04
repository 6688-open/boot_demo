package com.dj.boot.common.excel.exc;


import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
public class UserDto {

    @Excel(name = "ID")
    private String id;
    @Excel(name = "用户名")
    private String username;
    @Excel(name = "密码")
    private String password;


    public UserDto(String id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }
}
