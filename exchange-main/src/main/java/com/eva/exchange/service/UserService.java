package com.eva.exchange.service;

import com.eva.exchange.entity.User;

public interface UserService {
    void updateUserBalance(User user, double newBalance);
    User getAuthenticatedUser();
    User findByEmail(String email);
}
