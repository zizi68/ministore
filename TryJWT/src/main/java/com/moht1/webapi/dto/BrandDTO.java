package com.moht1.webapi.dto;

import com.moht1.webapi.model.Brand;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BrandDTO {
    private Brand brand;
    private int soldQuantity;
}
