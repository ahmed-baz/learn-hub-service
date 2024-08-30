package com.management.system.vo;

import jakarta.validation.constraints.NotNull;
import lombok.Data;


@Data
public class RegisterCourse {

    @NotNull(message = "course id is required to can register")
    private Long id;
}
