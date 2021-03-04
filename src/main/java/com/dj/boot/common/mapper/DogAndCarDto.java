package com.dj.boot.common.mapper;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DogAndCarDto {

    private CarDto carDto;
    private List<DogDto> dogDtoList;
}
