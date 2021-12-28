package com.convert;

import lombok.Data;

@Data
public class UserDTO {
    private Integer userId;

    private String userAge;

    private String userName;

    // runtime
    private int age;
}
