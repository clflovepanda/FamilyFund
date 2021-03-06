package com.panda.family.utils;

import com.panda.family.domain.User;
import lombok.Getter;
import lombok.Setter;

import javax.servlet.http.Cookie;
import java.util.HashMap;
import java.util.Map;

public class ThreadLocalUtil {

    public static ThreadLocal<ThreadLocalObject> threadLocal = new ThreadLocal<>();

    private static ThreadLocalObject getThreadLocalObject() {
        ThreadLocalObject threadLocalObject = threadLocal.get();
        if (threadLocalObject  == null) {
            threadLocalObject = new ThreadLocalObject();
            threadLocal.set(threadLocalObject);
        }
        return threadLocalObject;
    }

    public static void addCookie(String key, Cookie cookie) {
        ThreadLocalObject threadLocalObject = getThreadLocalObject();
        Map<String, Cookie> cookieMap = threadLocalObject.getCookieMap();
        if (cookieMap == null) {
            cookieMap = new HashMap<>();
            threadLocalObject.setCookieMap(cookieMap);
        }
        cookieMap.put(key, cookie);
    }

    public static Cookie getCookie(String key) {
        ThreadLocalObject threadLocalObject = getThreadLocalObject();
        Map<String, Cookie> cookieMap = threadLocalObject.getCookieMap();
        if (cookieMap == null) {
            return null;
        }
        return cookieMap.get(key);
    }

    public static void setUser(User user) {
        ThreadLocalObject threadLocalObject = getThreadLocalObject();
        threadLocalObject.setUser(user);
    }

    public static User getUser() {
        ThreadLocalObject threadLocalObject = getThreadLocalObject();
        return threadLocalObject.getUser();
    }

    @Getter
    @Setter
    public static class ThreadLocalObject {
        private Map<String, Cookie> cookieMap;
        private User user;
    }
}
