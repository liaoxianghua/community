package com.onepiece.community.model;

import lombok.Data;

@Data
public class User {
    private int id;
    private String accountId;
    private String name;
    private String token;
    private long gmtCreate;
    private long gmtModified;

}
