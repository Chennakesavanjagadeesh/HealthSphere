package com.example.healthsphere.Entities;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "admins")
@PrimaryKeyJoinColumn(name = "user_id")
public class Admin extends User {
    
    @Column(name = "department")
    private String department;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "admin_level")
    private AdminLevel adminLevel = AdminLevel.STANDARD;
    
    @Column(name = "employee_id", unique = true)
    private String employeeId;
    
    @OneToMany(mappedBy = "admin", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<SystemNotification> notifications;
    
    public Admin() {
        super();
        setRole(UserRole.ADMIN);
    }
    
    // Getters and Setters
    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }
    
    public AdminLevel getAdminLevel() { return adminLevel; }
    public void setAdminLevel(AdminLevel adminLevel) { this.adminLevel = adminLevel; }
    
    public String getEmployeeId() { return employeeId; }
    public void setEmployeeId(String employeeId) { this.employeeId = employeeId; }
    
    public List<SystemNotification> getNotifications() { return notifications; }
    public void setNotifications(List<SystemNotification> notifications) { this.notifications = notifications; }
}
