package com.wh.wenda.service;

import org.springframework.stereotype.Service;

@Service
public class WendaService {
    public String getMessage(int userId){
        return "我的ID是 "+userId;
    }
}
