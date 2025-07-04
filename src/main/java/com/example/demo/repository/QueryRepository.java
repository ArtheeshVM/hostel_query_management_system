package com.example.demo.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.demo.modal.Query;

@Repository
public interface QueryRepository extends JpaRepository<Query, Long> {
    List<Query> findByStatus(String status);
}
