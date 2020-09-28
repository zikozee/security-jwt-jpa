package com.zikozee.securityjwtjpa.student;

import com.zikozee.securityjwtjpa.student.dtos.QueryStudentDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/students")
public class StudentController {
    private final StudentService studentService;

    @GetMapping(path = "{studentId}")
    public QueryStudentDTO getStudent(@PathVariable("studentId") Integer studentId){
        return studentService.getStudent(studentId);
    }
}
