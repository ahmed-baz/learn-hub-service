package com.learn.hub.vo;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import org.springframework.data.domain.Sort;

import java.util.Date;

import static com.learn.hub.handler.ErrorCode.*;


@Data
public class FilterCourseRequest {

    @NotNull(message = PAGE_INDEX_REQUIRED)
    private Integer index;
    @Positive(message = PAGE_SIZE_INVALID)
    @NotNull(message = PAGE_SIZE_REQUIRED)
    private Integer size;
    private String keyword = "all";
    private Date from;
    private Date to;
    private String sortBy = "createdAt";
    private Sort.Direction sort = Sort.Direction.DESC;
}
