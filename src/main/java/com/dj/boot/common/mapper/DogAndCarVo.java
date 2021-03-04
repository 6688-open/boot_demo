package com.dj.boot.common.mapper;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DogAndCarVo {

    private CarVo carVo;
    private List<DogVo> dogVoList;
}
