package com.dj.boot.common.mapper;

import com.dj.boot.common.mapper.convert.CarConvert;
import com.dj.boot.common.mapper.convert.DogAndCarConvert;
import com.dj.boot.common.mapper.convert.DogConvert;
import org.apache.commons.compress.utils.Lists;

import java.util.Date;
import java.util.List;


public class Test {

    public static void main(String[] args) {
        //voToDtoTest();
        dtoToVoTest();
    }

    private static void voToDtoTest() {
        DogAndCarVo dogAndCarVo = new DogAndCarVo();
        List<DogVo> dogVoList = Lists.newArrayList();


        CarVo carVo = new CarVo();
        carVo.setUsername("wj");
        carVo.setCreateTime(new Date());
        carVo.setFlag(true);
        carVo.setUserPassVo("UserPassVo");
        carVo.setUserSexVo("userSexVo");
        carVo.setReceiptsPerformTypeName("调拨入库");


        DogVo dogVo = new DogVo();
        dogVo.setDogName("旺旺");
        dogVo.setDogSex(1);
        DogVo dogVo1 = new DogVo();
        dogVo1.setDogName("狗子");
        dogVo1.setDogSex(2);

        dogVoList.add(dogVo);
        dogVoList.add(dogVo1);


        dogAndCarVo.setCarVo(carVo);
        dogAndCarVo.setDogVoList(dogVoList);


        CarDto carDto = CarConvert.INSTANCE.voToDto(carVo);
        DogDto dogDto = DogConvert.INSTANCE.voToDto(dogVo);
        DogAndCarDto dogAndCarDto = DogAndCarConvert.INSTANCE.voToDto(dogAndCarVo);
        System.out.println(carDto);
        System.out.println(dogDto);
        System.out.println(dogAndCarDto);
    }

    private static void dtoToVoTest() {
        DogAndCarDto dogAndCarDto = new DogAndCarDto();
        List<DogDto> dogDtoList = Lists.newArrayList();


        CarDto carDto = new CarDto();
        carDto.setUsername("wj");
        carDto.setCreateTime("2019-10-13");
        carDto.setFlag(true);
        carDto.setUserPassDto("UserPassDto");
        carDto.setUserSexDto("userSexDto");
        carDto.setReceiptsPerformTypeCode(1);


        DogDto dogDto = new DogDto();
        dogDto.setDogName("旺旺");
        dogDto.setDogSex(1);
        DogDto dogDto1 = new DogDto();
        dogDto1.setDogName("狗子");
        dogDto1.setDogSex(2);

        dogDtoList.add(dogDto);
        dogDtoList.add(dogDto1);


        dogAndCarDto.setCarDto(carDto);
        dogAndCarDto.setDogDtoList(dogDtoList);


        CarVo carVo = CarConvert.INSTANCE.dtoToVo(carDto);
        DogVo dogVo = DogConvert.INSTANCE.dtoToVo(dogDto);
        DogAndCarVo dogAndCarVo = DogAndCarConvert.INSTANCE.dtoToVo(dogAndCarDto);
        System.out.println(carVo);
        System.out.println(dogVo);
        System.out.println(dogAndCarVo);
    }
}
