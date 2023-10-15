package com.moht1.webapi.dto;

import com.moht1.webapi.model.ReturnDetail;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReturnSummaryDTO {
    private ReturnDetail returnDetail;
    private int size;
}
