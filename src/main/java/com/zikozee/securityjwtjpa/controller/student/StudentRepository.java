package com.zikozee.securityjwtjpa.controller.student;

import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Integer> {
    boolean existsByStudentId(Integer id);

    Student findByStudentId(Integer id);
}
