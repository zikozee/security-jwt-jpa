package com.zikozee.securityjwtjpa.student;

import com.zikozee.securityjwtjpa.Exceptions.UserNotFoundException;
import com.zikozee.securityjwtjpa.student.dtos.CreateStudentDTO;
import com.zikozee.securityjwtjpa.student.dtos.QueryStudentDTO;
import com.zikozee.securityjwtjpa.student.dtos.UpdateStudentDTO;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl  implements StudentService{
    private final StudentRepository repository;
    private final ModelMapper modelMapper;

    @Override
    public List<QueryStudentDTO> getAllStudent() {
        return repository.findAll().stream()
                .map(student -> modelMapper.map(student, QueryStudentDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public QueryStudentDTO getStudent(Integer id) {
        boolean studentExist = repository.existsByStudentId(id);
        if(!studentExist) throw new UserNotFoundException("Student not found with id: " + id);

        return modelMapper.map(repository.findByStudentId(id), QueryStudentDTO.class);
    }

    @Override
    public QueryStudentDTO save(CreateStudentDTO createStudentDTO) {
        Student student = modelMapper.map(createStudentDTO, Student.class);
        return modelMapper.map(repository.save(student), QueryStudentDTO.class);
    }

    @Override
    public QueryStudentDTO updateStudent(UpdateStudentDTO updateStudentDTO) {
        boolean studentExist = repository.existsByStudentId(updateStudentDTO.getStudentId());
        if(!studentExist) throw new UserNotFoundException("Student not found with id: " + updateStudentDTO.getStudentId());
        Student patchedStudent = Student.builder()
                .studentId(updateStudentDTO.getStudentId())
                .studentName(updateStudentDTO.getStudentName())
                .email(updateStudentDTO.getEmail())
                .build();
        return modelMapper.map(repository.save(patchedStudent), QueryStudentDTO.class);
    }


    @Override
    public void deleteStudent(Integer studentId) {
        repository.deleteById(studentId);
    }
}
