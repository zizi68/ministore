package com.moht1.webapi.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductOutput {

    private int page;
    private int totalPage;
    private List<FullProduct> listResult = new ArrayList<>();

}
