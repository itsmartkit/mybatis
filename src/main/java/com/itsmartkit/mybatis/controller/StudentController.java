package com.itsmartkit.mybatis.controller;

import com.itsmartkit.mybatis.domain.Student;
import com.itsmartkit.mybatis.service.StudentService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping(value = "/stu")
public class StudentController {

    @Resource
    private StudentService studentService;

    @RequestMapping(value = "/get/{id}")
    public Student get(@PathVariable Long id) {
        return studentService.get(id);
    }

    @RequestMapping(value = "/list")
    public List<Student> list(Student student) {
        return studentService.list(student);
    }

}
