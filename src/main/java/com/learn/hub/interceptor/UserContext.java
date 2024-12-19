package com.learn.hub.interceptor;


import com.learn.hub.entity.UserEntity;


public class UserContext {

    private static final ThreadLocal<UserEntity> userThreadLocal = new ThreadLocal<>();

    private UserContext() {
    }

    public static void setGuestEmail(String email) {
        UserEntity user = new UserEntity();
        user.setEmail(email);
        userThreadLocal.set(user);
    }

    public static UserEntity getUser() {
        return userThreadLocal.get();
    }

    public static void setUser(UserEntity user) {
        userThreadLocal.set(user);
    }


    public static String getEmail() {
        return userThreadLocal.get() != null ? userThreadLocal.get().getEmail() : null;
    }

    public static void clear() {
        userThreadLocal.remove();
    }

}