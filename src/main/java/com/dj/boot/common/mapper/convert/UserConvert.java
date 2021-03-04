package com.dj.boot.common.mapper.convert;

import com.dj.boot.common.enums.ReceiptsPerformTypeEnum;
import com.dj.boot.common.mapper.base.ConvertVoDto;
import com.dj.boot.common.mapper.base.DateConvert;
import com.dj.boot.pojo.User;
import com.dj.boot.pojo.UserDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;


@Mapper(uses = {UserDto.class})
public interface UserConvert extends ConvertVoDto<User, UserDto> {

    UserConvert INSTANCE = Mappers.getMapper( UserConvert.class);



    @Override
    @Mapping(source = "userName", target = "userName", qualifiedBy = UserDto.IntegerToString.class)
    User dtoToVo(UserDto userDto);


    @Override
    @Mapping(source = "userName", target = "userName", qualifiedBy = UserDto.StringToInteger.class)
    UserDto voToDto(User user);
}
