package com.itsmartkit.mybatis.mapper;

import com.itsmartkit.mybatis.annotations.Mapper;
import com.itsmartkit.mybatis.annotations.Param;
import com.itsmartkit.mybatis.annotations.Select;
import com.itsmartkit.mybatis.domain.Student;

import java.util.List;

@Mapper
public interface StudentMapper {
    @Select("select * from t_student where id = :id")
    Student get(@Param("id") Long id);

    @Select("select * from t_student where id>:id and age =:age limit 10")
    List<Student> list(@Param("stu") Student student);
}
