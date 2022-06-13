package com.unsa.etf.OrderService.Service;

import com.unsa.etf.OrderService.Repository.ProductRepository;
import com.unsa.etf.OrderService.Repository.UserRepository;
import com.unsa.etf.OrderService.Responses.PaginatedObjectResponse;
import com.unsa.etf.OrderService.model.Product;
import com.unsa.etf.OrderService.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public List<User> getUsers() {
        return userRepository.findAll();
    }


    public User getUserById(String userId) {
        if(userRepository.existsById(userId)) {
            return userRepository.findById(userId).get();
        }
        return null;
    }

    public User addNewUser(User user) {
        userRepository.save(user);
        return user;
    }

    public User updateUser(User user){
        userRepository.save(user);
        return user;
    }

    public boolean deleteUser(String userId) {
        if (userRepository.existsById(userId)) {
            userRepository.deleteById(userId);
            return true;
        }
        return false;
    }
}
