package com.learn.hub.mapper;

import com.learn.hub.entity.UserEntity;
import com.learn.hub.payload.PageResponse;
import com.learn.hub.vo.Instructor;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper(uses = CourseMapper.class, componentModel = "spring")
public interface InstructorMapper {

    Instructor toInstructor(UserEntity user);

    List<Instructor> toInstructor(List<UserEntity> users);

    default PageResponse<Instructor> toInstructor(Page<UserEntity> page) {
        return PageResponse.<Instructor>builder()
                .list(toInstructor(page.getContent()))
                .isLast(page.isLast())
                .number(page.getNumber())
                .size(page.getSize())
                .totalElements(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .build();
    }
}
