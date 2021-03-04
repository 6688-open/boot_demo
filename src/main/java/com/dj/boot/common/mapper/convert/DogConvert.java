package com.dj.boot.common.mapper.convert;


import com.dj.boot.common.mapper.DogDto;
import com.dj.boot.common.mapper.DogVo;
import com.dj.boot.common.mapper.base.ConvertVoDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface DogConvert extends ConvertVoDto<DogVo, DogDto> {
    DogConvert INSTANCE = Mappers.getMapper( DogConvert.class);

    @Override
    DogVo dtoToVo(DogDto var1);


    @Override
    DogDto voToDto(DogVo var1);




}
