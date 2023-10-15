package com.moht1.webapi.dto;

import com.moht1.webapi.model.Brand;
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
public class BrandOutput {

    private int page;
    private int totalPage;
    private List<Brand> listResult = new ArrayList<Brand>();

}
