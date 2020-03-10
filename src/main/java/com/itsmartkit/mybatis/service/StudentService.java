package com.itsmartkit.mybatis.service;

import com.itsmartkit.mybatis.domain.Student;
import com.itsmartkit.mybatis.mapper.StudentMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class StudentService {
    @Resource
    private StudentMapper studentMapper;

    public Student get(Long id) {
        return studentMapper.get(id);
    }

    public List<Student> list(Student student) {
        return studentMapper.list(student);
    }

}
