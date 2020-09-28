package com.zikozee.securityjwtjpa.student;

import com.zikozee.securityjwtjpa.student.dtos.CreateStudentDTO;
import com.zikozee.securityjwtjpa.student.dtos.QueryStudentDTO;

import com.zikozee.securityjwtjpa.student.dtos.UpdateStudentDTO;

import java.util.List;

public interface StudentService {
    List<QueryStudentDTO> getAllStudent();
    QueryStudentDTO getStudent(Integer id);
    QueryStudentDTO save(CreateStudentDTO createStudentDTO);
    QueryStudentDTO updateStudent(UpdateStudentDTO updateStudentDTO);
    void deleteStudent(Integer studentId);
}
