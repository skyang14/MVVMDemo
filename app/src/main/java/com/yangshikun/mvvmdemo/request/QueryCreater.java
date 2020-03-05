package com.yangshikun.mvvmdemo.request;

import com.blankj.utilcode.util.EncryptUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by yang.shikun on 2020/3/5 13:35
 */

public class QueryCreater {
    //用户查询
    public static Map<String, Object> createFindUser(String account) {
        Map<String, Object> query = new HashMap<>();
        query.put("name", account);
        return query;
    }

    //创建账号
    public static Map<String, Object> createRegister(String account, String password) {
        password = EncryptUtils.encryptMD5ToString(password);
        Map<String, Object> query = new HashMap<>();
        query.put("name", account);
        query.put("password", password);
        return query;
    }

    //修改密码
    public static Map<String, Object> createChangePassword(String account, String oldPassword, String newPassword) {
        oldPassword = EncryptUtils.encryptMD5ToString(oldPassword);
        newPassword = EncryptUtils.encryptMD5ToString(newPassword);
        Map<String, Object> query = new HashMap<>();
        query.put("username", account);
        query.put("oldpassword", oldPassword);
        query.put("newpassword", newPassword);
        return query;
    }

    //忘记密码
    public static Map<String, Object> createForgetpassword(String account, String newpassword) {
        newpassword = EncryptUtils.encryptMD5ToString(newpassword);
        Map<String, Object> query = new HashMap<>();
        query.put("name", account);
        query.put("password", newpassword);
        return query;
    }
}

