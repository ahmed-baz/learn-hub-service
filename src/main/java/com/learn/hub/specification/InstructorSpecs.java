package com.learn.hub.specification;

import com.learn.hub.entity.UserEntity;
import com.learn.hub.enums.UserRoleEnum;
import com.learn.hub.vo.FilterInstructorRequest;
import lombok.experimental.UtilityClass;
import org.springframework.data.jpa.domain.Specification;

@UtilityClass
public class InstructorSpecs {

    public Specification<UserEntity> createInstructorSpec(FilterInstructorRequest request) {
        Specification<UserEntity> propertySpec = LearHubSpecs.propertySpec(request.getKeyword(), "firstName", "lastName", "email");
        return Specification.where(propertySpec)
                .and(userRoleSpec())
                .and(LearHubSpecs.distinct());
    }

    private Specification<UserEntity> userRoleSpec() {
        return (root, query, builder) -> builder.isMember(UserRoleEnum.INSTRUCTOR, root.get("roles"));
    }
}
