package com.dj.boot.common.mapper.base;
/**
 * @ClassName ConvertVoDto
 * @Description TODO
 * @Author wj
 * @Date 2020/05/22 19:04
 * @Version 1.0
 **/
public interface ConvertVoDto<VO, DTO> {

    VO dtoToVo(DTO var1);

    DTO voToDto(VO var1);
}
