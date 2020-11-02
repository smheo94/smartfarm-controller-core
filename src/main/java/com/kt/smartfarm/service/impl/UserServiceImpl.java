package com.kt.smartfarm.service.impl;

import com.kt.smartfarm.config.MyPasswordEncoder;
import com.kt.smartfarm.mapper.UserInfoMapper;
import com.kt.smartfarm.service.UserInfoVO;
import com.kt.smartfarm.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service("userService")
public class UserServiceImpl implements UserService {
    private final String localUserName = "kt-smartfarm";
    @Autowired
    private UserInfoMapper userInfoMapper;

    @Autowired
    private MyPasswordEncoder passwordEncoder;

    @Override
    public void modifyUser(UserInfoVO userInfo){
        userInfo.setPwd(passwordEncoder.encode(userInfo.getPwd()));
        userInfoMapper.updatePassword(userInfo);
    }

    public UserInfoVO getUser() {
        return userInfoMapper.getUserInfo(localUserName);
    }
}