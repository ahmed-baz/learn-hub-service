package com.management.system.mapper;

import com.management.system.entity.UserEntity;
import com.management.system.security.vo.AppUserDetails;
import org.mapstruct.Mapper;

@Mapper
public interface UserMapper {

    AppUserDetails toUserDetails(UserEntity userEntity);
}
