package com.example.healthsphere.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.healthsphere.Entities.User;
import com.example.healthsphere.Entities.UserRole;
import com.example.healthsphere.Entities.UserStatus;
import com.example.healthsphere.Repositories.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    // Note: Uncomment when Spring Security is added
    // @Autowired
    // private PasswordEncoder passwordEncoder;

    @Override
    public User createUser(User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("User with email " + user.getEmail() + " already exists");
        }
        // user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public User updateUser(Long id, User user) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
        
        existingUser.setFirstName(user.getFirstName());
        existingUser.setLastName(user.getLastName());
        existingUser.setPhoneNumber(user.getPhoneNumber());
        existingUser.setStreetAddress(user.getStreetAddress());
        existingUser.setCity(user.getCity());
        existingUser.setState(user.getState());
        existingUser.setZipCode(user.getZipCode());
        existingUser.setCountry(user.getCountry());
        
        return userRepository.save(existingUser);
    }

    @Override
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public List<User> getUsersByRole(UserRole role) {
        return userRepository.findByRole(role);
    }

    @Override
    public List<User> getUsersByStatus(UserStatus status) {
        return userRepository.findByStatus(status);
    }

    @Override
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("User not found with id: " + id);
        }
        userRepository.deleteById(id);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public User changePassword(Long id, String oldPassword, String newPassword) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
        
        // Add password verification logic here when Spring Security is implemented
        // if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
        //     throw new RuntimeException("Old password is incorrect");
        // }
        
        // user.setPassword(passwordEncoder.encode(newPassword));
        user.setPassword(newPassword); // Temporary until Spring Security is added
        return userRepository.save(user);
    }

    @Override
    public User toggleMFA(Long id, Boolean enabled) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
        
        user.setMfaEnabled(enabled);
        return userRepository.save(user);
    }
}
