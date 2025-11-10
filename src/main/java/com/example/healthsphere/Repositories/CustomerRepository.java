package com.example.healthsphere.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.healthsphere.Entities.Patient;

public interface CustomerRepository extends JpaRepository<Patient, Long> {

}
