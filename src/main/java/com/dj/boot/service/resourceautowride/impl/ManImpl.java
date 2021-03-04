package com.dj.boot.service.resourceAutowride.impl;


import com.dj.boot.service.resourceAutowride.Person;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public class ManImpl implements Person {
    @Override
    public String runMarathon() {
        return "ManImpl";
    }
}
