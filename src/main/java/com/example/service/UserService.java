package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.Human;
import com.example.domain.User;
import com.example.repository.HumanRepository;
import com.example.repository.UserRepository;

@Service
@Transactional
public class UserService {
	@Autowired
	UserRepository userRepository;
	
	public List<User> findAll() {
        return userRepository.findAll();
    }
	public User create(User user) {    	
        return userRepository.save(user);
    }
	public void delete(String id) {
        userRepository.delete(id);
    }
}
