package com.dj.boot.juc.cas;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.concurrent.atomic.AtomicReference;

@Data
@Builder
class UserClass{
    String userName;
    int age;

}
public class AtomicReferenceDemo {

    public static void main(String[] args) {
        UserClass zs = UserClass.builder().age(1).userName("zs").build();
        UserClass lisi = UserClass.builder().age(1).userName("lisi").build();
        //原子引用 主物理内存 共享变量是zs    ----》 CAS
        AtomicReference<UserClass> atomicReference = new AtomicReference<>();
        atomicReference.set(zs);


        System.out.println(atomicReference.compareAndSet(zs, lisi )+ "\t" + atomicReference.get().toString());
        System.out.println(atomicReference.compareAndSet(zs, lisi )+ "\t" + atomicReference.get().toString());



    }
}
