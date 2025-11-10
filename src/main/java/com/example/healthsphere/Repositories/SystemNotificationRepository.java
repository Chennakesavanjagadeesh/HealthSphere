package com.example.healthsphere.Repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.healthsphere.Entities.NotificationStatus;
import com.example.healthsphere.Entities.NotificationType;
import com.example.healthsphere.Entities.SystemNotification;
import com.example.healthsphere.Entities.UserRole;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface SystemNotificationRepository extends JpaRepository<SystemNotification, Long> {
    List<SystemNotification> findByStatus(NotificationStatus status);
    List<SystemNotification> findByNotificationType(NotificationType type);
    List<SystemNotification> findByTargetRole(UserRole role);
    
    @Query("SELECT sn FROM SystemNotification sn WHERE sn.targetUserId = :userId ORDER BY sn.createdAt DESC")
    List<SystemNotification> findByTargetUserIdOrderByCreatedAtDesc(@Param("userId") Long userId);
    
    @Query("SELECT sn FROM SystemNotification sn WHERE sn.targetRole = :role OR sn.targetRole IS NULL ORDER BY sn.createdAt DESC")
    List<SystemNotification> findByRoleOrGlobal(@Param("role") UserRole role);
    
    @Query("SELECT sn FROM SystemNotification sn WHERE sn.scheduledAt <= :now AND sn.status = 'SENT'")
    List<SystemNotification> findScheduledNotifications(@Param("now") LocalDateTime now);
}