package com.dj.boot.common.exs;

/**
 * @author wuhuagang
 * @date 2019/1/23
 * @desc 统一定义常量
 */
public interface Constant {

    int ZERO = 0;

    int ONE = 1;

    int TWO = 2;

    int THREE = 3;

    int FOUR = 4;

    int FIVE = 5;

    int SIX = 6;

    /**
     * 以下为多数据源的index,详情在application.yml中查看
     */
    //国资数据
    int ODS_GZSJ_SIRI_INDEX = 13;

    //领导安排
    String LDAP = "http://op.sasac.gov.cn/sgccmain.nsf/GetDpData?openagent&type=ldgzap";
    //每日情况
    String MRQK = "http://op.sasac.gov.cn/sgccmain.nsf/GetDpData?openagent&type=mrqk";
    //每日舆情
    String MRYQ = "http://op.sasac.gov.cn/sgccmain.nsf/GetDpData?openagent&type=mryq";
    String WBDSBG = "http://op.sasac.gov.cn/sgccmain.nsf/GetDpData?openagent&type=wbdsbg";
    //每日经济指标
    String MRJJZB = "http://irs.sasac.gov.cn:8888/km/bigscreen/0/getDailyEconomy";
    //文件下载地址
    String FILEDOWNLOAD = "http://irs.sasac.gov.cn:8888/km/bigscreen/0/download?";
    String OA_LOGIN_URL = "http://op.sasac.gov.cn/names.nsf?login";
    //企业动态
    String QYDT = "http://irs.sasac.gov.cn:8888/km/bigscreen/0/getEnterpriseDynamics";
    String DYBJ = "http://irs.sasac.gov.cn:8888/km/bigscreen/0/getResearchMaterial";
    // 获取巡视整改数据的路径
    String XSZG = "http://op.sasac.gov.cn/sgccmain.nsf/GetDpData?openagent&type=xszg";
    // 获取本周已完成和已完成阶段性工作整改措施
    String BZWCXSZG = "http://op.sasac.gov.cn/sgccmain.nsf/GetDpData?openagent&type=bzwcXszg";

    // 以下路径是42家中管企业
    // 中管名录
    String ZGML = "http://op.sasac.gov.cn/sgccmain.nsf/GetDpData?openagent&type=zgml";
    // 整改成效
    String ZGCX = "http://op.sasac.gov.cn/sgccmain.nsf/GetDpData?openagent&type=zgcx";
    // 重点央企重点问题
    String YQZDXSZG = "http://op.sasac.gov.cn/sgccmain.nsf/GetDpData?openagent&type=yqzdxszg";
    // 整改举措台账
    String YQXSZG = "http://op.sasac.gov.cn/sgccmain.nsf/GetDpData?openagent&type=yqxszg";

    // 主题教育专项整治暨初步检视问题整改工作台账
    String ZTXSZG = "http://op.sasac.gov.cn/sgccmain.nsf/GetDpData?openagent&type=ZTxszg";
    // 获取本周已完成整改措施
    String ZTBZWCXSZG = "http://op.sasac.gov.cn/sgccmain.nsf/GetDpData?openagent&type=ZtbzwcXszg";

    // 以下三个路径是统筹督促
    // 重点督促中管企业整改工作台账
    String QYZG = "http://op.sasac.gov.cn/sgccmain.nsf/GetDpData?openagent&type=Zddcxszg";
    // 办理中央巡视办移交意见建议工作台账
    String YJJY = "http://op.sasac.gov.cn/sgccmain.nsf/GetDpData?openagent&type=Suggestion";
    // 研究运用专题报告任务分工
    String RWFG = "http://op.sasac.gov.cn/sgccmain.nsf/GetDpData?openagent&type=Division";


    //116外网
//    String OA_LOGIN_USERNAME = "jszc";
//    String OA_LOGIN_PWD = "1";
    //内网
    String OA_LOGIN_USERNAME = "gzw";
    String OA_LOGIN_PWD = "1q2w3e4r5t";


}
