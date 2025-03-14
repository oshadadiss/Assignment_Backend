package com.oshada.student_management_system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.oshada.student_management_system.model.Students;

@Repository
public interface studentsRepository extends JpaRepository<Students, Long> {

}
