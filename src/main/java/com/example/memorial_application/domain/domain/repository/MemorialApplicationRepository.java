package com.example.memorial_application.domain.domain.repository;

import com.example.memorial_application.domain.domain.MemorialApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemorialApplicationRepository extends JpaRepository<MemorialApplication, Long> {

}
