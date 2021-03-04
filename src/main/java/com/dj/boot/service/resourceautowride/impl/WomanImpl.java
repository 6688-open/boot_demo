package com.dj.boot.service.resourceAutowride.impl;


import com.dj.boot.service.resourceAutowride.Person;
import org.springframework.stereotype.Service;

@Service
public class WomanImpl implements Person {
    @Override
    public String runMarathon() {
        return "WomanImpl";
    }
}
