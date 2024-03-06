package com.findmygym.findMyGymbackend.repository;

import com.findmygym.findMyGymbackend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long>{
}
