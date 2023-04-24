package ru.kata.spring.boot_security.demo.services;


import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.entities.User;
import ru.kata.spring.boot_security.demo.repositories.UserRepository;

import java.util.List;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;


   public UserService(UserRepository userRepository) {
        this.userRepository=userRepository;
    }

    @Transactional(readOnly = true)
    public User findByUserName(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        user.getRoles().size();
        if (user == null) {
            throw new UsernameNotFoundException(String.format("User '%s' not found", username));
    }
        return user;

    }


    @Transactional(readOnly = true)
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Transactional
    public void update(Long id, User user) {
        User user1 = findUserById(id);
        user1.setName(user.getName());
        user1.setRoles(user.getRoles());
        user1.setUsername(user.getUsername());
        user1.setPassword(user.getPassword());
        userRepository.save(user1);
    }

    @Transactional(readOnly = true)
    public User findUserById(Long userId) {
        return userRepository.findById(userId).orElse(null);
    }

    @Transactional
    public void delete(Long id) {
        User user2 = findUserById(id);
        userRepository.delete(user2);
    }

    @Transactional
    public void saveUser(User user) {
        userRepository.save(user);
    }

}
