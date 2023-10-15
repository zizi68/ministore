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

public class UserDetailOutput {
    private int page;
    private int totalPage;
    private List<UserDTO> listResult = new ArrayList<UserDTO>();
}
