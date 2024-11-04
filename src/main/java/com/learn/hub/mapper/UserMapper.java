package com.learn.hub.mapper;

import com.learn.hub.entity.UserEntity;
import com.learn.hub.security.vo.UserRegisterResponse;
import org.mapstruct.Mapper;

@Mapper
public interface UserMapper {


    UserRegisterResponse toRegisterResponse(UserEntity user);
}
