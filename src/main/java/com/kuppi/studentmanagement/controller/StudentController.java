package com.kuppi.studentmanagement.controller;

import com.kuppi.studentmanagement.model.Course;
import com.kuppi.studentmanagement.model.Student;
import com.kuppi.studentmanagement.repository.CourseRepository;
import com.kuppi.studentmanagement.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/student")
public class StudentController {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private CourseRepository courseRepository;

    @PostMapping("/add")
    public ResponseEntity<?> addStudent(@RequestBody Student student) {
        Optional<Course> course =  courseRepository.findById(student.getCourseId());
        if(course.isPresent()){
          Student newStudent = studentRepository.save(student);

          List<Student> students;
          students  = course.get().getStudents();
          students.add(newStudent);

          courseRepository.save(course.get());
          return new ResponseEntity<Student>(student, HttpStatus.OK);
        }else{
            return new ResponseEntity<String>("Course not found", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/")
    public ResponseEntity<?> getStudents() {
        List<Student> students = studentRepository.findAll();
        if(students.size() > 0){
            return new ResponseEntity<List<Student>>(students, HttpStatus.OK);
        }else {
            return new ResponseEntity<>("No Students",HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getStudentById(@PathVariable String id) {
        Optional<Student> student =  studentRepository.findById(id);
        if(student.isPresent()){
            return new ResponseEntity<Student>(student.get(), HttpStatus.OK);
        }else{
            return new ResponseEntity<String>("Student not found", HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateStudentById(@PathVariable String id, @RequestBody Student student){
        Optional<Student> existingStudent =  studentRepository.findById(id);
        if(existingStudent.isPresent()){
            Student updateStudent = existingStudent.get();
            updateStudent.setName(student.getName());
            updateStudent.setAge(student.getAge());
            updateStudent.setNic(student.getNic());
            return new ResponseEntity<>(studentRepository.save(updateStudent), HttpStatus.OK);
        }else{
            return new ResponseEntity<>("Student Update Error",HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteStudentById(@PathVariable String id){
        try{
            Optional<Student> existingStudent =  studentRepository.findById(id);
            if(existingStudent.isPresent()){
                Optional<Course> course =  courseRepository.findById(existingStudent.get().getCourseId());
                if(course.isPresent()){
                    List<Student> students = course.get().getStudents();
                    students.removeIf(student -> student.getId().equals(id));
                    courseRepository.save(course.get());
                    studentRepository.deleteById(id);
                    return new ResponseEntity<>("Success deleted with " + id,HttpStatus.OK);
                }else{
                    return new ResponseEntity<String>("Course not found", HttpStatus.NOT_FOUND);
                }
            }else{
                return new ResponseEntity<>("Student Update Error",HttpStatus.NOT_FOUND);
            }
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
        }
    }

}
