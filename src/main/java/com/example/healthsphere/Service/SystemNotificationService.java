package com.example.healthsphere.Service;

import java.util.List;
import java.util.Optional;

import com.example.healthsphere.Entities.NotificationStatus;
import com.example.healthsphere.Entities.NotificationType;
import com.example.healthsphere.Entities.SystemNotification;
import com.example.healthsphere.Entities.UserRole;

public interface SystemNotificationService {
    SystemNotification createNotification(SystemNotification notification);
    SystemNotification updateNotification(Long id, SystemNotification notification);
    Optional<SystemNotification> getNotificationById(Long id);
    List<SystemNotification> getAllNotifications();
    List<SystemNotification> getNotificationsByUserId(Long userId);
    List<SystemNotification> getNotificationsByRole(UserRole role);
    List<SystemNotification> getNotificationsByType(NotificationType type);
    List<SystemNotification> getNotificationsByStatus(NotificationStatus status);
    SystemNotification markAsRead(Long id);
    SystemNotification markAsDelivered(Long id);
    void deleteNotification(Long id);
    void sendSystemWideNotification(String title, String message, NotificationType type);
    void sendRoleBasedNotification(String title, String message, UserRole role, NotificationType type);
}
