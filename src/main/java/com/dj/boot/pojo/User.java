package com.dj.boot.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.dj.boot.common.enums.OrderTypeEnum;
import com.dj.boot.pojo.validate.GroupInterface;
import com.dj.boot.pojo.validate.NotLessThanZero;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;

/**
 * @author ext.wangjia
 *  EqualsAndHashCode 对userName 进行比较  不关心别的字段 userName
 */
@Data
@Accessors(chain = true)
@TableName("dj_user")
@AllArgsConstructor
@NoArgsConstructor
// @EqualsAndHashCode(of = {"userName"})
public class User extends UserCommon<Date> implements Serializable {

    @TableId(type = IdType.AUTO)
    private Integer id;
    @NotNull(message = "sex不能为空")
    private Integer sex;
    /**
     * 入参校验 直接返回错误
     * public void test(@Validated (GroupInterface.class) User user) //{}
     *
     * .@NotBlank(message = "pin不能为空")  只能是字符串
     */
    @Valid
    @NotEmpty(message = "用户名不能为空")
    private String userName;
    /**
     * 1 @JsonFormat 返回给前端数据 自动将Date -----> String
     *       fasterxml maven jar包
     *     . @ResponseBody返回json数据的时候，才会返回格式化的yyyy-MM-dd HH:mm:ss时间
     *      直接输出不可以
     * 2  @DateTimeFormat   接收前端传来的参数  String -----> Date
     *    引入依赖 joda-time
     *
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createTime;

    private String salt;
    @NotEmpty(message = "密码不能为空")
    private String password;

    private BigDecimal userNum;
    private BigDecimal totalNum;


    /**
     *  查询条件 id 的集合
     *   加了 GroupInterface 校验会忽略ids 不会校验  加上GroupInterface 只是对标注的字段进行校验
     */
    @TableField(exist = false)
    @NotNull(message = "商品批次档案ID列表不能为空", groups = {GroupInterface.class})
    @Size(min=1, message = "商品批次档案ID列表size必须大于零", groups = {GroupInterface.class})
    private List<Integer> ids;


//    @TableField(exist = false)
//    @NotNull(message = "person不能为空")
//    @Valid
//    private Person person;


    /**
     * 百分比
     *
     * @see com.dj.boot.common.enums.OrderTypeEnum
     */
    @TableField(exist = false)
    private String distributeRate;

    @TableField(exist = false)
    private String createTimeStr;

    @TableField(exist = false)
    private String pin;

    @TableField(exist = false)
    private List<Integer> list;

    /**
     * created
     */
    @TableField(exist = false)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date created;


    @TableField(exist = false)
    private String token;
    @TableField(exist = false)
    private int idx;
    @TableField(exist = false)
    @Email
    @NotEmpty(message = "email不能为空")
    private String email;
    @TableField(exist = false)
    private Date operateTime;
    @TableField(exist = false)
    private String phone = "18351867657";


    public User(Integer id, Integer sex, @Valid @NotEmpty(message = "用户名不能为空") String userName, String salt, @NotEmpty(message = "密码不能为空") String password) {
        this.id = id;
        this.sex = sex;
        this.userName = userName;
        this.salt = salt;
        this.password = password;
    }


    /*    SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
        -- Table structure for dj_user
-- ----------------------------
    DROP TABLE IF EXISTS `dj_user`;
    CREATE TABLE `dj_user` (
            `id` int(11) NOT NULL,
  `sex` int(11) DEFAULT NULL,
  `user_name` varchar(255) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
            `salt` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;*/

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        User user = (User) o;
        return Objects.equals(id, user.id) &&
                Objects.equals(sex, user.sex) &&
                Objects.equals(userName, user.userName) &&
                Objects.equals(createTime, user.createTime) &&
                Objects.equals(salt, user.salt) &&
                Objects.equals(password, user.password) &&
                Objects.equals(createTimeStr, user.createTimeStr) &&
                Objects.equals(created, user.created) &&
                Objects.equals(token, user.token) &&
                Objects.equals(email, user.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, sex, userName, createTime, salt, password, createTimeStr, created, token, email);
    }


    public static void main(String[] args) {
        User user = new User();
        user.setUserName("111");
        user.setPassword("222");

        User user1 = new User();
        user1.setUserName("1112");
        user1.setPassword("333");

        System.out.println(user.equals(user1));
    }
}
