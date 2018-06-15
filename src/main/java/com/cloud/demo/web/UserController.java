package com.cloud.demo.web;
import com.cloud.demo.core.Wrapper;
import com.cloud.demo.core.WrapMapper;
import com.cloud.demo.model.User;
import com.cloud.demo.service.UserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
* Created by Tian Jiguang on 2018/06/15.
*/
@RestController
@RequestMapping(value = "user", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class UserController {
    @Resource
    private UserService userService;

    @PostMapping("/add")
    public Wrapper add(User user) {
        userService.save(user);
        return WrapMapper.genSuccessResult();
    }

    @PostMapping("/delete")
    public Wrapper delete(@RequestParam Integer id) {
        userService.deleteById(id);
        return WrapMapper.genSuccessResult();
    }

    @PostMapping("/update")
    public Wrapper update(User user) {
        userService.update(user);
        return WrapMapper.genSuccessResult();
    }

    @PostMapping("/detail")
    public Wrapper detail(@RequestParam Integer id) {
        User user = userService.findById(id);
        return WrapMapper.genSuccessResult(user);
    }

    @GetMapping("/list")
    public Wrapper list(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "0") Integer size) {
        PageHelper.startPage(page, size);
        List<User> list = userService.selectAll();
        PageInfo pageInfo = new PageInfo(list);
        return WrapMapper.genSuccessResult(pageInfo);
    }
}
