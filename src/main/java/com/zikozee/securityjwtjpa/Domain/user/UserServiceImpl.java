package com.zikozee.securityjwtjpa.Domain.user;

import com.zikozee.securityjwtjpa.Exceptions.UserNotFoundException;
import com.zikozee.securityjwtjpa.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;

    @Override
    public User findByUsername(String username) {

        if(!this.userExists(username)) throw new UserNotFoundException("User with username: " + username + " not found");
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

    @Override
    public boolean userExists(String username) {
        return userRepository.existsByUsername(username);
    }
}
