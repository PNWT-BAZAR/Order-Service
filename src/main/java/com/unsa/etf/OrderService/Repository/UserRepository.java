package com.unsa.etf.OrderService.Repository;

import com.unsa.etf.OrderService.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
}
