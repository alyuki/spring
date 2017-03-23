package com.example.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.domain.Human;


public interface HumanRepository extends JpaRepository<Human, Integer> {
    @Query("SELECT x FROM Human x ORDER BY x.name, x.sex")
    List<Human> findAllOrderByName();

    @Query("SELECT x FROM Human x ORDER BY x.name, x.sex")
    Page<Human> findAllOrderByName(Pageable pageable);
    
}
