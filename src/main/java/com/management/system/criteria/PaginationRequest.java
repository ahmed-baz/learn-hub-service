package com.management.system.criteria;


import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Data
public class PaginationRequest {

    @PositiveOrZero(message = "invalid page index")
    private Integer index;

    @Positive(message = "page size should be positive")
    private Integer size;

}
