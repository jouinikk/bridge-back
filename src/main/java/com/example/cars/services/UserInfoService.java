package com.example.cars.services;

import com.example.cars.entities.UserInfo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserInfoService {

    public List<UserInfo> getAllUserInfos() {
        // Temporary mock - replace with real users later
        List<UserInfo> users = new ArrayList<>();

        UserInfo u1 = new UserInfo(1L, "maissahermessi2@gmail.com", "");
        UserInfo u2 = new UserInfo(2L, "maissa.yassine.hermessi@gmail.com", "");

        users.add(u1);
        users.add(u2);
        return users;
    }
}
