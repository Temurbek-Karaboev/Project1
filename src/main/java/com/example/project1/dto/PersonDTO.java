package com.example.project1.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.relational.core.mapping.Column;

@Getter
@Setter
public class PersonDTO {
    private String name;
    private String password;
    private String phoneNumber;
    private Long roomId;
}
