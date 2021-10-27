package com.ttpsc.signingintheair.parcel.entity;

import lombok.Data;

import java.util.List;

@Data
public class GridResponse<T> {
    private Integer page;
    private Integer total;
    private List<T> rows;
}
