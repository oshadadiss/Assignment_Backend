package com.oshada.student_management_system.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.oshada.student_management_system.model.Students;
import com.oshada.student_management_system.repository.studentsRepository;

@RestController
public class studentsController {

    @Autowired
    private studentsRepository studentsRepository;

    @GetMapping("/getAllStudents")
    public ResponseEntity<List<Students>> getAllStudents() {

        try {

            List<Students> studentsList = new ArrayList<>();
            studentsRepository.findAll().forEach(studentsList::add);

            if (studentsList.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else {
                return new ResponseEntity<>(studentsList, HttpStatus.OK);
            }

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getStudentById/{id}")
    public ResponseEntity<Students> getStudentById(@PathVariable Long id) {

        Optional<Students> studentData = studentsRepository.findById(id);

        if (studentData.isPresent()) {
            return new ResponseEntity<>(studentData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    @PostMapping("/addStudent")
    public ResponseEntity<Students> addStudent(@RequestBody Students students) {
        Students studentsObj = studentsRepository.save(students);
        return new ResponseEntity<>(studentsObj, HttpStatus.ACCEPTED);
    }

    @PostMapping("/udateStudentById/{id}")
    public ResponseEntity<Students> updateStudentById(@PathVariable Long id, @RequestBody Students newStudentData) {

        Optional<Students> oldStudentData = studentsRepository.findById(id);

        if (oldStudentData.isPresent()) {

            Students updatedStudentData = oldStudentData.get();

            updatedStudentData.setName(newStudentData.getName());
            updatedStudentData.setAge(newStudentData.getAge());
            updatedStudentData.setGrade(newStudentData.getGrade());

            Students studentObj = studentsRepository.save(updatedStudentData);
            return new ResponseEntity<>(studentObj, HttpStatus.ACCEPTED);

        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    @DeleteMapping("/deleteStudentById/{id}")
    public ResponseEntity<HttpStatus> deleteStudentById(@PathVariable Long id) {
        studentsRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
