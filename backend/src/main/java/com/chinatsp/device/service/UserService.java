package com.chinatsp.device.service;

import com.chinatsp.device.entity.po.User;
import com.chinatsp.device.utils.JwtTokenUtil;

import java.util.HashMap;
import java.util.Map;

public class UserService {

    public Map<String, Object> login(User user) {
        Map<String, Object> map = new HashMap<>();
        try {
            Map<String, String> payload = new HashMap<>();
            payload.put("id", String.valueOf(user.getId()));
            payload.put("name", user.getUsername());
            // 生成JWT的令牌
            String token = JwtTokenUtil.getToken(payload);
            map.put("status", true);
            map.put("msg", "认证成功");
            map.put("token", token); //响应TOKEN
        } catch (Exception e) {
            map.put("status", false);
            map.put("msg", e.getMessage());
        }
        return map;
    }
}
