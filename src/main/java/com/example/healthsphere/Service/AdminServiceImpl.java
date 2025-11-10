package com.example.healthsphere.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.healthsphere.Entities.Admin;
import com.example.healthsphere.Entities.AdminLevel;
import com.example.healthsphere.Entities.UserStatus;
import com.example.healthsphere.Repositories.AdminRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminRepository adminRepository;

    @Override
    public Admin createAdmin(Admin admin) {
        if (adminRepository.existsByEmail(admin.getEmail())) {
            throw new RuntimeException("Admin with email " + admin.getEmail() + " already exists");
        }
        if (admin.getEmployeeId() != null && adminRepository.findByEmployeeId(admin.getEmployeeId()).isPresent()) {
            throw new RuntimeException("Admin with employee ID " + admin.getEmployeeId() + " already exists");
        }
        admin.setStatus(UserStatus.ACTIVE);
        return adminRepository.save(admin);
    }

    @Override
    public Admin updateAdmin(Long id, Admin admin) {
        Admin existingAdmin = adminRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Admin not found with id: " + id));
        
        existingAdmin.setFirstName(admin.getFirstName());
        existingAdmin.setLastName(admin.getLastName());
        existingAdmin.setPhoneNumber(admin.getPhoneNumber());
        existingAdmin.setStreetAddress(admin.getStreetAddress());
        existingAdmin.setCity(admin.getCity());
        existingAdmin.setState(admin.getState());
        existingAdmin.setZipCode(admin.getZipCode());
        existingAdmin.setCountry(admin.getCountry());
        existingAdmin.setDepartment(admin.getDepartment());
        existingAdmin.setAdminLevel(admin.getAdminLevel());
        
        return adminRepository.save(existingAdmin);
    }

    @Override
    public Optional<Admin> getAdminById(Long id) {
        return adminRepository.findById(id);
    }

    @Override
    public Optional<Admin> getAdminByEmail(String email) {
        return adminRepository.findByEmail(email);
    }

    @Override
    public Optional<Admin> getAdminByEmployeeId(String employeeId) {
        return adminRepository.findByEmployeeId(employeeId);
    }

    @Override
    public List<Admin> getAllAdmins() {
        return adminRepository.findAll();
    }

    @Override
    public List<Admin> getAdminsByLevel(AdminLevel level) {
        return adminRepository.findByAdminLevel(level);
    }

    @Override
    public void deleteAdmin(Long id) {
        if (!adminRepository.existsById(id)) {
            throw new RuntimeException("Admin not found with id: " + id);
        }
        adminRepository.deleteById(id);
    }
}
