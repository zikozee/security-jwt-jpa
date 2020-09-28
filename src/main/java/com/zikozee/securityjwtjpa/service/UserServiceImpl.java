package com.zikozee.securityjwtjpa.service;

import com.zikozee.securityjwtjpa.Exceptions.UserNotFoundException;
import com.zikozee.securityjwtjpa.Domain.User;
import com.zikozee.securityjwtjpa.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;

    @Override
    public User findByUsername(String username) {
        boolean userExists = userRepository.existsByUsername(username);
        if(!userExists) throw new UserNotFoundException("User with username: " + username + " not found");
        return userRepository.findByUsername(username);
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public void deleteById(Long adminId) {
        userRepository.deleteById(adminId);
    }
}
