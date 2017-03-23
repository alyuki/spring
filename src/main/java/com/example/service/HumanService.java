package com.example.service;

import java.time.LocalDate;
import java.time.Period;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import com.example.domain.Human;
import com.example.domain.User;
import com.example.repository.HumanRepository;


@Service
@Transactional
public class HumanService {
	@Autowired
	HumanRepository humanRepository;
	
	public List<Human> findAll() {
        return humanRepository.findAllOrderByName();
    }
	
	public List<Human> findAllOrderByIdDesc() {
        return humanRepository.findAllOrderByIdDesc();
    }

    public Page<Human> findAll(Pageable pageable) {
        return humanRepository.findAllOrderByName(pageable);
    }

    public Human findOne(Integer id) {
        return humanRepository.findOne(id);
    }

    public Human create(Human human,User user) {
    	human.setUser(user);
        return humanRepository.save(human);
    }

    public Human update(Human human,User user) {
    	human.setUser(user);
        return humanRepository.save(human);
    }

    public void delete(Integer id) {
        humanRepository.delete(id);
    }
    
    // 誕生日から年齢を計算する
    public Integer getAge(LocalDate birthday){
        LocalDate today = LocalDate.now();
        return Period.between(LocalDate.parse(birthday.toString()), today).getYears();
    }
}
