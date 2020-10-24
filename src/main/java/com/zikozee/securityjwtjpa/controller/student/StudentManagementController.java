package com.zikozee.securityjwtjpa.controller.student;

import com.zikozee.securityjwtjpa.controller.student.dtos.CreateStudentDTO;
import com.zikozee.securityjwtjpa.controller.student.dtos.QueryStudentDTO;
import com.zikozee.securityjwtjpa.controller.student.dtos.UpdateStudentDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("management/api/v1/students")
public class StudentManagementController {
    private final StudentService studentService;

    // hasRole('ROLE_')  hasAnyRole('ROLE_') hasAuthority('permission') hasAnyAuthority('permission')

    @GetMapping
    //@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_ADMIN_TRAINEE', 'ROLE_SUPER_ADMIN')") //role base authentication appending with ROLE_
    @PreAuthorize("hasAuthority('student:read')") //This will be most appropriate i.e any role that has student:read permission, though student should not be able to read all students, good enough student has no permission
    public List<QueryStudentDTO> getAllStudents(){
        log.info("getAllStudents");
        return studentService.getAllStudent();
    }

    @PostMapping()
    @PreAuthorize("hasAuthority('student:write')") // permission based authentication, added just as it is
    public QueryStudentDTO registerNewStudent(@RequestBody CreateStudentDTO createStudentDTO){
        log.info("registerNewStudent: " +createStudentDTO.toString());
        return studentService.save(createStudentDTO);
    }

    @DeleteMapping(path = "{studentId}")
    @PreAuthorize("hasAuthority('student:write')")
    public void deleteStudent(@PathVariable("studentId") Integer studentId){
        log.info("deleteStudent: " + "studentId: " +studentId);
        studentService.deleteStudent(studentId);
    }

    @PutMapping
    @PreAuthorize("hasAuthority('student:write')")
    public QueryStudentDTO updateStudent(@RequestBody UpdateStudentDTO updateStudentDTO){
        log.info("updating Student with id: " + updateStudentDTO.getStudentId() + " " + updateStudentDTO);
        return studentService.updateStudent(updateStudentDTO);
    }
}
