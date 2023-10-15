package com.moht1.webapi.dto;

import com.moht1.webapi.model.Category;
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
public class CategoryOutput {

    private int page;
    private int totalPage;
    private List<Category> listResult = new ArrayList<Category>();

}
