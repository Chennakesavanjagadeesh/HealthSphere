package com.example.healthsphere.Service;

import java.util.List;
import java.util.Optional;

import com.example.healthsphere.Entities.User;
import com.example.healthsphere.Entities.UserRole;
import com.example.healthsphere.Entities.UserStatus;

public interface UserService {
    User createUser(User user);
    User updateUser(Long id, User user);
    Optional<User> getUserById(Long id);
    Optional<User> getUserByEmail(String email);
    List<User> getAllUsers();
    List<User> getUsersByRole(UserRole role);
    List<User> getUsersByStatus(UserStatus status);
    void deleteUser(Long id);
    boolean existsByEmail(String email);
    User changePassword(Long id, String oldPassword, String newPassword);
    User toggleMFA(Long id, Boolean enabled);
}