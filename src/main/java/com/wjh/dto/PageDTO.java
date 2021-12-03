package com.wjh.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class PageDTO<T> {

    private List<T> content;
    private int totalPages;
    private int number;
    private boolean first;
    private boolean last;
}
