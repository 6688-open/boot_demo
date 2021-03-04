package com.dj.boot.common.mapper.convert;

import com.dj.boot.common.enums.ReceiptsPerformTypeEnum;
import com.dj.boot.common.mapper.CarDto;
import com.dj.boot.common.mapper.CarVo;
import com.dj.boot.common.mapper.base.ConvertVoDto;
import com.dj.boot.common.mapper.base.DateConvert;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(uses = {DateConvert.class, ReceiptsPerformTypeEnum.class})
public interface CarConvert extends ConvertVoDto<CarVo, CarDto> {

    CarConvert INSTANCE = Mappers.getMapper( CarConvert.class);

    /**
     *  vo   dto 属性相同 无需映射
     *  属性名不相同时  @Mapping  进行映射
     *  多个映射 多个mapping就可以
     *  当一个类中的属性是对象是
     *  mappings {
     *       mapping,
     *       mapping
     *  }
     *  org.mapstruct.Qualifier @qualifiedBy 可以对值进行逻辑处理再赋值
     *   比如 Date String 需要一个类进行转换
     *   当使用 @qualifiedBy    必须要指定使用的类
     *   @Mapper(uses = {DateConvert.class})      不是注解。calss
     *
     *
     */
    @Mapping(source = "userPassDto", target = "userPassVo")
    @Mapping(source = "userSexDto", target = "userSexVo")
    @Mapping(source = "createTime", target = "createTime", qualifiedBy = DateConvert.StringToDate.class)
    @Mapping(source = "receiptsPerformTypeCode", target = "receiptsPerformTypeName", qualifiedBy = ReceiptsPerformTypeEnum.GetNameByCode.class)
    @Override
    CarVo dtoToVo(CarDto carDto);



    @Mapping(source = "userPassVo", target = "userPassDto")
    @Mapping(source = "userSexVo", target = "userSexDto")
    @Mapping(source = "createTime", target = "createTime", qualifiedBy = DateConvert.DateToString.class)
    @Mapping(source = "receiptsPerformTypeName", target = "receiptsPerformTypeCode", qualifiedBy = ReceiptsPerformTypeEnum.GetCodeByName.class)
    @Override
    CarDto voToDto(CarVo carVo);



}
