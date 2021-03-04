package com.dj.boot.pojo;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.NotEmpty;

import java.util.Date;
import java.util.List;

/**
 * 仓库表
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Warehouse  {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Long id;

    /**
     * 所属配送中心ID
     */
    private Long distributionId;
    /**
     * 所属配送中心编号
     */
    private String distributionNo;
    /**
     * 所属机构ID
     */
    private Long orgId;
    /**
     * 所属机构编号
     */
    private String orgNo;
    /**
     * 所属机构名称
     */
    private String orgName;
    /**
     * 所属配送中心名称
     */
    private String distributionName;
    /**
     * 仓库号（erp统一编号）
     */
    private String erpWarehouseNo;

    /**
     * 库房编号（clps库房编号）
     */
    private String warehouseNo;

    /**
     * 库房名称
     */
    private String warehouseName;

    /**
     * 库房状态（1-初始，2-启用，3-停用）
     */
    private byte status;

    private String statusStr;
    /**
     * 库房来源（1-WMS，2-CLPS管理端）
     */
    private byte source;

    private String sourceStr;
    /**
     * 库房类型
     */
    private byte type;

    private String typeStr;
    /**
     * 库房所属
     */
    private Long sellerId;

    /**
     * 商家名称
     */
    private String sellerName;

    /**
     * 业务模式（1-京东自营仓，2-开放平台仓）
     */
    private byte businessModel;

    private String businessModelStr;

    /**
     * 库房面积(平方米)
     */
    private Double area ;

    /**
     * 备注
     */
    private String remark;

    /**
     * 省ID
     */
    private Long provinceId;

    /**
     * 市ID
     */
    private Long cityId;

    /**
     * 县ID
     */
    private Long countyId;

    /**
     * 区ID
     */
    private Long townId;


    /**
     * 经度
     */
    private Double longitude;

    /**
     * 纬度
     */
    private Double latitude ;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 创建人
     */
    private String createUser;

    /**
     * 更新人
     */
    private String updateUser;

    /**
     * 删除标志
     */
    private byte yn;
    /**
     * 库房覆盖范围
     */
    private String coverScope;

    private String warehouseLogType;

    private String changeStatus;

    /**
     * 新接口仓库查询接口添加参数
     * @return
     */
    private List<String> warehouseNoList;
    private List<String> statusList;

    /**
     * 库房类型
     */
    private String wareType;

    private String wareTypeStr;

    private String createTimeStr;

    private String updateTimeStr;

    private String region;


    /**
     * 航空标记（1否，2是 3待审核）
     */
    private Byte airMark;

    /**
     * 合作伙伴主键
     */
    private Long partnerId;

    /**
     * 合作伙伴编码
     */
    private String partnerNo;

    /**
     * 合作伙伴名称
     */
    private String partnerName;
    /**
     *业务范围
     */
    private Integer businessScope;
    private String businessScopeStr;

    /**
     *物品类型
     */
    private Integer goodsType;
    private String goodsTypeStr;

    /**
     *保管类型
     */
    private Integer storageType;
    private String storageTypeStr;

    /**
     *建筑结构
     */
    private Integer buildStructure;
    private String buildStructureStr;

    /**
     * 生效日期
     */
    private String effectiveDate;
    /**
     * 失效日期
     */
    private String expiryDate;


    /**
     * 软件产品
     */
    private Integer softwareProduct;
    private String softwareProductStr;

    /**
     * WMS对接类型
     */
    private Integer connectType;

    /**
     * 是否线上运营（1-是 2-否）
     */
    private Byte isOnline;

    /**
     * 是否是京配转三方
     */
    private String jd2other;

    /**
     * 三方交货方式
     */
    private String threePartyDelivery;

    /**
     * 接货仓编码
     */
    private String cargoWarehouseNo;

    /**
     * 是否京配搜索
     * @return
     */
    private Byte jpSearch;

    /**
     * 是否京配搜索字符
     * @return
     */
    private String jpSearchStr;
    /**
     * 运营等级
     */
    private String manageLevel;

    /**
     * 最大设计入库单量
     */
    private Integer maxPoQty;
    /**
     * 最大设计生产单量
     */
    private Integer maxProQty;
    /**
     * 最大涉及库存量
     */
    private Integer maxStoreQty;

    /** 1:系统部署中 2:系统部署完成 3：线上正式运营**/
    private Byte manageStatus;

    /** 仓库生产结束时间**/
    private String endProductTime;

    /** 运营类型 1：云仓 2：系统仓**/
    private Byte manageType;

    /** 可销售面积**/
    private Double saleableArea;

    /** 库房属性**/
    private Byte warehouseProperty;

    /** 库房属性字符串**/
    private String warehousePropertyStr;

    /** 沧源编号**/
    private String warehouseSourceNo;

    /** 1:系统部署中 2:系统部署完成 3：线上正式运营**/
    private String manageStatusDesc;

    //是否同步给eclp 1：是 0：否
    private Byte isToEclp;

    private String isOnlineStr;

    /**
     * 监管仓（1-启用 2-停用）
     */
    private Byte superviseWarehouse;

    //众包标记
    private Byte crowdSourcing;

    /**
     * 是否开启装卸队 1：是    0：否
     */
    private Byte isPorterTeam;

    /**
     * 仓库提供的服务，服务代码逗号分隔
     */
    private String warehouseServices;

    /**
     * 是否自营入云仓/VMI
     */
    private Byte isVmi;

    private String isVmiStr;

    /**
     * 是否支持众包描述
     */
    private String crowdSourcingStr;

    /**
     * 门店类型
     */
    private Byte storeType;

    /**
     * 门店类型描述
     */
    private String storeTypeStr;

    /**
     * 是否支持退货
     */
    private Byte isSalesReturn;

    /**
     * 是否支持退货描述
     */
    private String isSalesReturnStr;

    /**
     * 是否支持自提
     */
    private Byte isSelfPickUp;

    /**
     * 是否支持自提描述
     */
    private String isSelfPickUpStr;

    /**
     * 接单方式
     */
    private Byte orderTakingMode;

    /**
     * 接单方式描述
     */
    private String orderTakingModeStr;

    /**
     * 优先配送范围
     */
    private Double priorDeliveryScope;

    /**
     * 门店仓库编码
     */
    private String storeWarehouseNo;

    /**
     * 营业开始时间
     */
    private String openTime;

    /**
     * 营业结束时间
     */
    private String closeTime;

    /**
     * 门店图片
     */
    private String photoUrl;

    /**
     * 二级属性
     */
    private Byte twoLevelProperty;

    /**
     * 物理区域
     */
    private Byte physicsRegion;


    /**
     * 是否测试仓 默认是0,0-否，1-是
     */
    private String isTestStr;

    /**
     * 二级属性
     */
    private String twoLevelPropertyStr;
    /**
     * 物理区域
     */
    private String physicsRegionStr;

    private String manageTypeStr;

    /**
     * 是否测试仓 默认是0,0-否，1-是
     */
    private Byte isTest;

    /**
     * 代贴条码服务；0支持代贴条码服务；1不支持代贴条码服务
     */
    private Byte stickerCode;

    /**
     *仓库编码
     */
    private String downstreamNo;

    /**
     * 仓库重量必采 0否，1是
     */
    private Byte weightMustPick;

    /**
     * 硬件版本（0-无，1-设备系统）
     */
    private Byte hardwareVersion;

    /** 硬件版本字符串**/
    private String hardwareVersionStr;

    /**
     * 成本部门
     */
    private String costDept;

    /**
     * 仓库别名
     */
    private String warehouseAlias;

}
