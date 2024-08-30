package com.management.system.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PageResponse<T> {

    private List<T> list;
    private int number;
    private int size;
    private long totalElements;
    private int totalPages;
    private boolean isLast;

}
