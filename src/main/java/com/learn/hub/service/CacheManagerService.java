package com.learn.hub.service;

import java.util.List;


public interface CacheManagerService {

    void clearCacheByName(String key);

    void clearAllCachedKeys();

    List<String> getAllCachedKeys();

}