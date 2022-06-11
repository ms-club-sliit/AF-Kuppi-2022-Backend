package com.kuppi.studentmanagement.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.lang.Nullable;

import java.util.ArrayList;
import java.util.List;

@Document( collection = "course")
public class Course {

    @Id
    private String id;

    private String courseName;

    private Long courseFee;

    private List<Student> students;

    public Course() {
    }

    public Course(String id, String courseName, Long courseFee, List<Student> students) {
        this.id = id;
        this.courseName = courseName;
        this.courseFee = courseFee;
        this.students = students;
    }

    public Course(String id, String courseName, Long courseFee) {
        this.id = id;
        this.courseName = courseName;
        this.courseFee = courseFee;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public Long getCourseFee() {
        return courseFee;
    }

    public void setCourseFee(Long courseFee) {
        this.courseFee = courseFee;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }
}
