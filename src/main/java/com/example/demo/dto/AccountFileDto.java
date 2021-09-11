package com.example.demo.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Data
public class AccountFileDto implements Serializable {

    private Long id;

    private String username;

    private String password;
}
