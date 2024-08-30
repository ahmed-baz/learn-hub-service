package com.management.system.service;

import java.util.List;


public interface CacheManagerService {

    void clearCacheByName(String key);

    void clearAllCachedKeys();

    List<String> getAllCachedKeys();

}