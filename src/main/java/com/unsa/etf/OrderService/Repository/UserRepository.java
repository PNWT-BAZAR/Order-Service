package com.unsa.etf.OrderService.Repository;

import com.unsa.etf.OrderService.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
}
