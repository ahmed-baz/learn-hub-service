package com.learn.hub.payload;

import lombok.*;

import java.util.List;

@Setter
@Getter
@Builder
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
