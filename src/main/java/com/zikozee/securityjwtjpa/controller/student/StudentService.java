package com.zikozee.securityjwtjpa.controller.student;

import com.zikozee.securityjwtjpa.controller.student.dtos.CreateStudentDTO;
import com.zikozee.securityjwtjpa.controller.student.dtos.QueryStudentDTO;

import com.zikozee.securityjwtjpa.controller.student.dtos.UpdateStudentDTO;

import java.util.List;

public interface StudentService {
    List<QueryStudentDTO> getAllStudent();
    QueryStudentDTO getStudent(Integer id);
    QueryStudentDTO save(CreateStudentDTO createStudentDTO);
    QueryStudentDTO updateStudent(UpdateStudentDTO updateStudentDTO);
    void deleteStudent(Integer studentId);
}
