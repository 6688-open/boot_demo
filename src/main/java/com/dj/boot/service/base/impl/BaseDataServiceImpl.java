package com.dj.boot.service.base.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dj.boot.mapper.base.BaseDataMapper;
import com.dj.boot.pojo.base.BaseData;
import com.dj.boot.service.base.BaseDataService;
import org.springframework.stereotype.Service;



@Service
public class BaseDataServiceImpl extends ServiceImpl<BaseDataMapper, BaseData> implements BaseDataService {

}
