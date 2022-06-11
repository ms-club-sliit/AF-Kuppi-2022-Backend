package com.kuppi.studentmanagement.controller;

import com.kuppi.studentmanagement.model.Course;
import com.kuppi.studentmanagement.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/course")
public class CourseController {

    @Autowired
    private CourseRepository courseRepository;

    @PostMapping("/add")
    public ResponseEntity<?> addCourse(@RequestBody Course course) {
        try{
            courseRepository.save(course);
            return new ResponseEntity<Course>(course, HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/")
    public ResponseEntity<?> getCourses() {
        List<Course> courses = courseRepository.findAll();
        if(courses.size() > 0){
            return new ResponseEntity<List<Course>>(courses, HttpStatus.OK);
        }else {
            return new ResponseEntity<>("No Courses",HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCourseById(@PathVariable String id) {
        Optional<Course> course =  courseRepository.findById(id);
        if(course.isPresent()){
            return new ResponseEntity<Course>(course.get(), HttpStatus.OK);
        }else{
            return new ResponseEntity<String>("Course not found", HttpStatus.NOT_FOUND);
        }
    }

}
