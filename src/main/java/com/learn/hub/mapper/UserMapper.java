package com.learn.hub.mapper;

import com.learn.hub.entity.UserEntity;
import com.learn.hub.security.vo.AppUserDetails;
import org.mapstruct.Mapper;

@Mapper
public interface UserMapper {

    AppUserDetails toUserDetails(UserEntity userEntity);
}
