package com.learn.hub.utils;

import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;

import java.net.InetAddress;

@UtilityClass
public class NetworkUtil {

    @SneakyThrows
    public String getHostAddress() {
        InetAddress ip = InetAddress.getLocalHost();
        return ip.getHostAddress();
    }
}
