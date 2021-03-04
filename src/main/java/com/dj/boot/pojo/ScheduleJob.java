package com.dj.boot.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @ClassName JobEntity
 * @Description TODO
 * @Author wj
 * @Date 2019/12/11 18:37
 * @Version 1.0
 **/
@Data
@Accessors(chain = true)
@TableName("schedule_job")
public class ScheduleJob implements Serializable{

    @TableId(type = IdType.AUTO)
    private Integer id;
    private String beanName;          //job bean名称
    private String methodName;         //job方法名
    private String cron;          //执行的cron
    private String param;     //job的参数
    private String description;   //job描述信息
   // @Column(name = "vm_param")
    private String vmParam;       //vm参数
    private Integer status;        //job的执行状态,这里我设置为OPEN/CLOSE且只有该值为OPEN才会执行该Job
    public ScheduleJob() {
    }
    //新增Builder模式,可选,选择设置任意属性初始化对象
    public ScheduleJob(Builder builder) {
        id = builder.id;
        beanName = builder.beanName;
        methodName = builder.methodName;
        cron = builder.cron;
        param = builder.param;
        description = builder.description;
        vmParam = builder.vmParam;
        status = builder.status;
    }
    public static class Builder {
        private Integer id;
        private String beanName = "";          //job名称
        private String methodName = "";         //job方法名
        private String cron = "";          //执行的cron
        private String param = "";     //job的参数
        private String description = "";   //job描述信息
        private String vmParam = "";       //vm参数
        private Integer status = null;        //job的执行状态,只有该值为0才会执行该Job
        public Builder withId(Integer i) {
            id = i;
            return this;
        }
        public Builder withName(String n) {
            beanName = n;
            return this;
        }
        public Builder withGroup(String g) {
            methodName = g;
            return this;
        }
        public Builder withCron(String c) {
            cron = c;
            return this;
        }
        public Builder withParameter(String p) {
            param = p;
            return this;
        }
        public Builder withDescription(String d) {
            description = d;
            return this;
        }
        public Builder withVMParameter(String vm) {
            vmParam = vm;
            return this;
        }
        public Builder withStatus(Integer s) {
            status = s;
            return this;
        }
        public ScheduleJob newJobEntity() {
            return new ScheduleJob(this);
        }
    }



 /*
   SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for schedule_job
-- ----------------------------
DROP TABLE IF EXISTS `schedule_job`;
CREATE TABLE `schedule_job` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `bean_name` varchar(255) DEFAULT NULL,
  `method_name` varchar(255) DEFAULT NULL,
  `cron` varchar(255) DEFAULT NULL,
  `param` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `vm_param` varchar(255) DEFAULT NULL,
  `status` int(11) DEFAULT NULL COMMENT '0 代表 启动  1代表不启动',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;
*/


//    INSERT INTO `schedule_job` VALUES ('2', 'secondJop', 'execute', '0/5 * * * * ? ', '2', '第二个', null, '0');
//    INSERT INTO `schedule_job` VALUES ('3', 'thirdJop', 'execute', '*/6 * * * * ?', '3', '第三个', null, '0');
//    INSERT INTO `schedule_job` VALUES ('5', 'firstJop', 'execute', '0 0/2 * * * ?', '5', '第五个', null, '0');








/*
Navicat MySQL Data Transfer
Source Server         : local_mysql
Source Server Version : 50537
Source Host           : localhost:3306
Source Database       : quartz_test
Target Server Type    : MYSQL
Target Server Version : 50537
File Encoding         : 65001
Date: 2016-12-05 18:15:38
*/

/*    SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
        -- Table structure for qrtz_blob_triggers
-- ----------------------------
    DROP TABLE IF EXISTS `qrtz_blob_triggers`;
    CREATE TABLE `qrtz_blob_triggers` (
            `sched_name` varchar(120) NOT NULL,
  `trigger_name` varchar(80) NOT NULL,
  `trigger_group` varchar(80) NOT NULL,
  `blob_data` blob,
    PRIMARY KEY (`sched_name`,`trigger_name`,`trigger_group`),
    CONSTRAINT `qrtz_blob_triggers_ibfk_1` FOREIGN KEY (`sched_name`, `trigger_name`, `trigger_group`) REFERENCES `qrtz_triggers` (`sched_name`, `trigger_name`, `trigger_group`)
            ) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
        -- Records of qrtz_blob_triggers
-- ----------------------------

        -- ----------------------------
        -- Table structure for qrtz_calendars
-- ----------------------------
    DROP TABLE IF EXISTS `qrtz_calendars`;
    CREATE TABLE `qrtz_calendars` (
            `sched_name` varchar(120) NOT NULL,
  `calendar_name` varchar(80) NOT NULL,
  `calendar` blob NOT NULL,
    PRIMARY KEY (`calendar_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
        -- Records of qrtz_calendars
-- ----------------------------

        -- ----------------------------
        -- Table structure for qrtz_cron_triggers
-- ----------------------------
    DROP TABLE IF EXISTS `qrtz_cron_triggers`;
    CREATE TABLE `qrtz_cron_triggers` (
            `sched_name` varchar(120) NOT NULL,
  `trigger_name` varchar(80) NOT NULL,
  `trigger_group` varchar(80) NOT NULL,
  `cron_expression` varchar(120) NOT NULL,
  `time_zone_id` varchar(80) DEFAULT NULL,
    PRIMARY KEY (`sched_name`,`trigger_name`,`trigger_group`),
    CONSTRAINT `qrtz_cron_triggers_ibfk_1` FOREIGN KEY (`sched_name`, `trigger_name`, `trigger_group`) REFERENCES `qrtz_triggers` (`sched_name`, `trigger_name`, `trigger_group`)
            ) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
        -- Records of qrtz_cron_triggers
-- ----------------------------
    INSERT INTO `qrtz_cron_triggers` VALUES ('dufy_test', 'trigger1', 'group1', '0/2 * * * * ?', 'Asia/Shanghai');

-- ----------------------------
        -- Table structure for qrtz_fired_triggers
-- ----------------------------
    DROP TABLE IF EXISTS `qrtz_fired_triggers`;
    CREATE TABLE `qrtz_fired_triggers` (
            `sched_name` varchar(120) NOT NULL,
  `entry_id` varchar(95) NOT NULL,
  `trigger_name` varchar(80) NOT NULL,
  `trigger_group` varchar(80) NOT NULL,
  `instance_name` varchar(80) NOT NULL,
  `fired_time` bigint(20) NOT NULL,
  `sched_time` bigint(20) NOT NULL,
  `priority` int(11) NOT NULL,
  `state` varchar(16) NOT NULL,
  `job_name` varchar(80) DEFAULT NULL,
  `job_group` varchar(80) DEFAULT NULL,
  `is_nonconcurrent` int(11) DEFAULT NULL,
  `requests_recovery` int(11) DEFAULT NULL,
    PRIMARY KEY (`sched_name`,`entry_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
        -- Records of qrtz_fired_triggers
-- ----------------------------
    INSERT INTO `qrtz_fired_triggers` VALUES ('dufy_test', 'NON_CLUSTERED1480921314227', 'trigger1', 'group1', 'NON_CLUSTERED', '1480922508052', '1480922510000', '5', 'ACQUIRED', null, null, '0', '0');

-- ----------------------------
        -- Table structure for qrtz_job_details
-- ----------------------------
    DROP TABLE IF EXISTS `qrtz_job_details`;
    CREATE TABLE `qrtz_job_details` (
            `sched_name` varchar(120) NOT NULL,
  `job_name` varchar(80) NOT NULL,
  `job_group` varchar(80) NOT NULL,
  `description` varchar(120) DEFAULT NULL,
  `job_class_name` varchar(128) NOT NULL,
  `is_durable` int(11) NOT NULL,
  `is_nonconcurrent` int(11) NOT NULL,
  `is_update_data` int(11) NOT NULL,
  `requests_recovery` int(11) NOT NULL,
  `job_data` blob,
    PRIMARY KEY (`sched_name`,`job_name`,`job_group`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
        -- Records of qrtz_job_details
-- ----------------------------
    INSERT INTO `qrtz_job_details` VALUES ('dufy_test', 'job1', 'group1', null, 'com.dufy.learn.HelloJob', '0', '0', '0', '0', 0x230D0A234D6F6E204465632030352031353A30303A34332043535420323031360D0A);

-- ----------------------------
        -- Table structure for qrtz_locks
-- ----------------------------
    DROP TABLE IF EXISTS `qrtz_locks`;
    CREATE TABLE `qrtz_locks` (
            `sched_name` varchar(120) NOT NULL,
  `lock_name` varchar(40) NOT NULL,
    PRIMARY KEY (`sched_name`,`lock_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
        -- Records of qrtz_locks
-- ----------------------------

        -- ----------------------------
        -- Table structure for qrtz_paused_trigger_grps
-- ----------------------------
    DROP TABLE IF EXISTS `qrtz_paused_trigger_grps`;
    CREATE TABLE `qrtz_paused_trigger_grps` (
            `sched_name` varchar(120) NOT NULL,
  `trigger_group` varchar(80) NOT NULL,
    PRIMARY KEY (`sched_name`,`trigger_group`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
        -- Records of qrtz_paused_trigger_grps
-- ----------------------------

        -- ----------------------------
        -- Table structure for qrtz_scheduler_state
-- ----------------------------
    DROP TABLE IF EXISTS `qrtz_scheduler_state`;
    CREATE TABLE `qrtz_scheduler_state` (
            `sched_name` varchar(120) NOT NULL,
  `instance_name` varchar(80) NOT NULL,
  `last_checkin_time` bigint(20) NOT NULL,
  `checkin_interval` bigint(20) NOT NULL,
    PRIMARY KEY (`sched_name`,`instance_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
        -- Records of qrtz_scheduler_state
-- ----------------------------

        -- ----------------------------
        -- Table structure for qrtz_simple_triggers
-- ----------------------------
    DROP TABLE IF EXISTS `qrtz_simple_triggers`;
    CREATE TABLE `qrtz_simple_triggers` (
            `sched_name` varchar(120) NOT NULL,
  `trigger_name` varchar(80) NOT NULL,
  `trigger_group` varchar(80) NOT NULL,
  `repeat_count` bigint(20) NOT NULL,
  `repeat_interval` bigint(20) NOT NULL,
  `times_triggered` bigint(20) NOT NULL,
    PRIMARY KEY (`sched_name`,`trigger_name`,`trigger_group`),
    CONSTRAINT `qrtz_simple_triggers_ibfk_1` FOREIGN KEY (`sched_name`, `trigger_name`, `trigger_group`) REFERENCES `qrtz_triggers` (`sched_name`, `trigger_name`, `trigger_group`)
            ) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
        -- Records of qrtz_simple_triggers
-- ----------------------------

        -- ----------------------------
        -- Table structure for qrtz_simprop_triggers
-- ----------------------------
    DROP TABLE IF EXISTS `qrtz_simprop_triggers`;
    CREATE TABLE `qrtz_simprop_triggers` (
            `sched_name` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `STR_PROP_1` varchar(512) DEFAULT NULL,
  `STR_PROP_2` varchar(512) DEFAULT NULL,
  `STR_PROP_3` varchar(512) DEFAULT NULL,
  `INT_PROP_1` int(11) DEFAULT NULL,
  `INT_PROP_2` int(11) DEFAULT NULL,
  `LONG_PROP_1` bigint(20) DEFAULT NULL,
  `LONG_PROP_2` bigint(20) DEFAULT NULL,
  `DEC_PROP_1` decimal(13,4) DEFAULT NULL,
  `DEC_PROP_2` decimal(13,4) DEFAULT NULL,
  `BOOL_PROP_1` varchar(1) DEFAULT NULL,
  `BOOL_PROP_2` varchar(1) DEFAULT NULL,
    PRIMARY KEY (`sched_name`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
    CONSTRAINT `qrtz_simprop_triggers_ibfk_1` FOREIGN KEY (`sched_name`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`sched_name`, `trigger_name`, `trigger_group`)
            ) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
        -- Records of qrtz_simprop_triggers
-- ----------------------------

        -- ----------------------------
        -- Table structure for qrtz_triggers
-- ----------------------------
    DROP TABLE IF EXISTS `qrtz_triggers`;
    CREATE TABLE `qrtz_triggers` (
            `sched_name` varchar(120) NOT NULL,
  `trigger_name` varchar(80) NOT NULL,
  `trigger_group` varchar(80) NOT NULL,
  `job_name` varchar(80) NOT NULL,
  `job_group` varchar(80) NOT NULL,
  `description` varchar(120) DEFAULT NULL,
  `next_fire_time` bigint(20) DEFAULT NULL,
  `prev_fire_time` bigint(20) DEFAULT NULL,
  `priority` int(11) DEFAULT NULL,
  `trigger_state` varchar(16) NOT NULL,
  `trigger_type` varchar(8) NOT NULL,
  `start_time` bigint(20) NOT NULL,
  `end_time` bigint(20) DEFAULT NULL,
  `calendar_name` varchar(80) DEFAULT NULL,
  `misfire_instr` smallint(6) DEFAULT NULL,
  `job_data` blob,
    PRIMARY KEY (`sched_name`,`trigger_name`,`trigger_group`),
    KEY `sched_name` (`sched_name`,`job_name`,`job_group`),
    CONSTRAINT `qrtz_triggers_ibfk_1` FOREIGN KEY (`sched_name`, `job_name`, `job_group`) REFERENCES `qrtz_job_details` (`sched_name`, `job_name`, `job_group`)
            ) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
        -- Records of qrtz_triggers
-- ----------------------------
    INSERT INTO `qrtz_triggers` VALUES ('dufy_test', 'trigger1', 'group1', 'job1', 'group1', null, '1480922510000', '1480922508000', '5', 'ACQUIRED', 'CRON', '1480921243000', '0', null, '0', '');

    以下两个表是业务需要，不是quartz框架必须的
    DROP TABLE IF EXISTS `TBL_TIMEDTASK_ENTITY`;
    CREATE TABLE `TBL_TIMEDTASK_ENTITY` (
    TASK_ID VARCHAR(50) PRIMARY KEY NOT NULL,
    TASK_NAME VARCHAR(256) MOT NULL,
    TASK_STATUS INT(11) NOT NULL,
    TASK_TYPE INT(11) NOT NULL,
    CRON_EXPRESSION VARCHAR(50),
    CREATOR VARCHAR(50),
    CREATE_TIME CURRENT_DATE ,
    START_TIME CURRENT_DATE ,
    END_TIME CURRENT_DATE ,
    EXE_TIME VARCHAR(10),
    TASK_CYCLE INT(11),
    TASK_CYCLE_VALUE INT(11),
    TASK_PARAMETER COBOL
)ENGINE=InnoDB DEFAULT CHARSET=utf8;
    CREATE INDEX INDEX_TBL_TIMEDTASK_ENTITY ON TBL_TIMEDTASK_ENTITY(
            TASK_ID(50) ASC
);

    DROP TABLE IF EXISTS `TBL_TIMEDTASK_EXECUTERESULT_ENTITY`;
    CREATE TABLE `TBL_TIMEDTASK_EXECUTERESULT_ENTITY` (
    RESULT_ID INT(11) PRIMARY KEY NOT NULL AUTO_INCREMENT,
    TASK_ID VARCHAR(50) NOT NULL,
    EXECUTE_RESULT INT(11) NOT NULL,
    EXECUTE_DESC VARCHAR(2000),
    END_TIME CURRENT_DATE
)ENGINE=InnoDB DEFAULT CHARSET=utf8;
    CREATE INDEX INDEX_TBL_TIMEDTASK_EXECUTERESULT_ENTITY ON TBL_TIMEDTASK_EXECUTERESULT_ENTITY(
            RESULT_ID ASC
    );*/

}
