package com.example.healthsphere.Service;

import java.util.List;
import java.util.Optional;

import com.example.healthsphere.Entities.Admin;
import com.example.healthsphere.Entities.AdminLevel;

public interface AdminService {
    Admin createAdmin(Admin admin);
    Admin updateAdmin(Long id, Admin admin);
    Optional<Admin> getAdminById(Long id);
    Optional<Admin> getAdminByEmail(String email);
    Optional<Admin> getAdminByEmployeeId(String employeeId);
    List<Admin> getAllAdmins();
    List<Admin> getAdminsByLevel(AdminLevel level);
    void deleteAdmin(Long id);
}