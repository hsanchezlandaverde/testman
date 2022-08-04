package com.github.hsanchezlandaverde.testman.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.github.hsanchezlandaverde.testman.entities.Exam;

@Repository
public interface IExamRepository extends JpaRepository<Exam, UUID> {

}
