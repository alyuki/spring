package com.example.repository;

import java.util.List;


import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import com.example.domain.User;

public interface UserRepository  extends JpaRepository<User, String> {
    
}
