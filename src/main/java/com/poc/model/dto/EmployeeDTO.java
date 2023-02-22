package com.poc.model.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class EmployeeDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
}
