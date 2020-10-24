package com.zikozee.securityjwtjpa.controller.student.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateStudentDTO {
    private Integer studentId;

    private String studentName;

    private String email;
}
