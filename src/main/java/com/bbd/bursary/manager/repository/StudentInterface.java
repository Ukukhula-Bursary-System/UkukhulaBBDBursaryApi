package com.bbd.bursary.manager.repository;

import com.bbd.bursary.manager.model.Student;

import java.util.List;

public interface StudentInterface  {

    int save(Student student);

    int update(long id, Student student);

    Student findById(Long studentId);

    int deleteById(Long id);

    int updateStudentStatus(long id, int statusID);


    List<Student> getAll();

    List<Student> getAllApproved();

    List<Student> getAllRejected();

    List<Student> getAllPending();
}
