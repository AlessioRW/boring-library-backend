package com.example.boringLibrary.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.boringLibrary.model.Users;

@Repository
public interface UserRepo extends JpaRepository <Users, Integer>{
}