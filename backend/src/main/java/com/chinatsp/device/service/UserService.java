package com.chinatsp.device.service;

import com.chinatsp.device.entity.po.User;

import java.util.Map;

/**
 * @author lizhe
 * @date 2020/11/26 9:42
 **/
public interface UserService {
    Map<String, Object> login(User user);
}
