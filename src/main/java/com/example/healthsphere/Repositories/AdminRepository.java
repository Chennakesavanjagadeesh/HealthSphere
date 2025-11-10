package com.example.healthsphere.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.healthsphere.Entities.Admin;
import com.example.healthsphere.Entities.AdminLevel;

import java.util.List;
import java.util.Optional;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {
	boolean existsByEmail(String email);
    Optional<Admin> findByEmail(String email);
    Optional<Admin> findByEmployeeId(String employeeId);
    List<Admin> findByAdminLevel(AdminLevel adminLevel);
    List<Admin> findByDepartment(String department);
}