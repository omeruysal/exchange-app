package com.eva.exchange.util;

import com.eva.exchange.entity.User;
import com.eva.exchange.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import static java.util.Arrays.asList;

@Component
@AllArgsConstructor
public class ExchangeInitializeUtil {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @PostConstruct
    public void init() {
        User firstUser = new User("USA", "omer@outlook.com", passwordEncoder.encode("12345"), 5000d);
        User secondUser = new User("Turkey", "ismail@outlook.com", passwordEncoder.encode("12345"), 10000d);
        User thirdUser = new User("Turkey", "ahmet@outlook.com", passwordEncoder.encode("12345"), 10000d);
        userRepository.saveAll(asList(firstUser, secondUser, thirdUser));
    }
}
