package com.chinatsp.device.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PageResponse extends Response {
    private int pageSize = 20;
    private int totalRows = 0;
    private int totalPages = 0;
}
