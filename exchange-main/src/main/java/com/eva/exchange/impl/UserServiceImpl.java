package com.eva.exchange.impl;

import com.eva.exchange.entity.User;
import com.eva.exchange.repository.UserRepository;
import com.eva.exchange.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public void updateUserBalance(User user, double newBalance) {
        user.setBalance(newBalance);
        userRepository.save(user);
    }

    @Override
    public User getAuthenticatedUser() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }


    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow();

    }
}
