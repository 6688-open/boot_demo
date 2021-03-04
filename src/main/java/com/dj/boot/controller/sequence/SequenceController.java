package com.dj.boot.controller.sequence;

import com.dj.boot.service.pk.SequenceService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @ProjectName: boot_demo
 * @PackageName: com.dj.boot.controller.sequence
 * @User: ext.wangjia
 * @Author: wangJia
 * @Date: 2020-08-13-11-53
 */

@Api(value = "获取主键")
@RestController
@RequestMapping("/sequence/")
public class SequenceController {


    @Autowired
    private SequenceService sequenceService;

    @PostMapping("test")
    public void test () {

        long id = sequenceService.insertAndGetSequence("dj_user");
        System.out.println(id);
        List<String> list = sequenceService.insertAndGetBatchSequence("dj_user", 2);


        List<String> list1 = sequenceService.insertAndGetBatchSequence("user", 10);


        long l = sequenceService.insertAndGetMergeSequence("dj_user", 100000000L);
        System.out.println(l);
        System.out.println(l);
    }

}
