package com.dj.boot.common.mapper.convert;


import com.dj.boot.common.mapper.DogAndCarDto;
import com.dj.boot.common.mapper.DogAndCarVo;
import com.dj.boot.common.mapper.base.ConvertVoDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper(uses = {CarConvert.class, DogConvert.class})
public interface DogAndCarConvert extends ConvertVoDto<DogAndCarVo, DogAndCarDto> {
    DogAndCarConvert INSTANCE = Mappers.getMapper( DogAndCarConvert.class);

    @Mappings({
            @Mapping(source = "carDto", target = "carVo"),
            @Mapping(source = "dogDtoList", target = "dogVoList")
    })
    @Override
    DogAndCarVo dtoToVo(DogAndCarDto dogAndCarDto);


    @Mappings({
            @Mapping(source = "carVo", target = "carDto"),
            @Mapping(source = "dogVoList", target = "dogDtoList")
    })
    @Override
    DogAndCarDto voToDto(DogAndCarVo dogAndCarVo);




}
