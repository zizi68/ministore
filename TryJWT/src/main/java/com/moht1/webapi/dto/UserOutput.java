package com.moht1.webapi.dto;

import com.moht1.webapi.model.User;
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
public class UserOutput {
    private int page;
    private int totalPage;
    private List<User> listResult = new ArrayList<User>();
}
