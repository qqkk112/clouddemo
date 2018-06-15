package com.cloud.demo.service.impl;

import com.cloud.demo.dao.UserMapper;
import com.cloud.demo.model.User;
import com.cloud.demo.service.UserService;
import com.cloud.demo.core.BaseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


/**
 * Created by Tian Jiguang on 2018/06/15.
 */
@Service
@Transactional
public class UserServiceImpl extends BaseService<User> implements UserService {
    @Resource
    private UserMapper userMapper;

}
