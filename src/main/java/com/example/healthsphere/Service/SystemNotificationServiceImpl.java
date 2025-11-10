package com.example.healthsphere.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.healthsphere.Entities.NotificationStatus;
import com.example.healthsphere.Entities.NotificationType;
import com.example.healthsphere.Entities.SystemNotification;
import com.example.healthsphere.Entities.UserRole;
import com.example.healthsphere.Repositories.SystemNotificationRepository;
import com.example.healthsphere.Repositories.AdminRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class SystemNotificationServiceImpl implements SystemNotificationService {

    @Autowired
    private SystemNotificationRepository notificationRepository;
    
    @Autowired
    private AdminRepository adminRepository;

    @Override
    public SystemNotification createNotification(SystemNotification notification) {
        if (!adminRepository.existsById(notification.getAdmin().getId())) {
            throw new RuntimeException("Admin not found");
        }
        notification.setStatus(NotificationStatus.SENT);
        notification.setSentAt(LocalDateTime.now());
        return notificationRepository.save(notification);
    }

    @Override
    public SystemNotification updateNotification(Long id, SystemNotification notification) {
        SystemNotification existing = notificationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Notification not found with id: " + id));
        
        existing.setTitle(notification.getTitle());
        existing.setMessage(notification.getMessage());
        existing.setNotificationType(notification.getNotificationType());
        existing.setTargetRole(notification.getTargetRole());
        existing.setTargetUserId(notification.getTargetUserId());
        existing.setScheduledAt(notification.getScheduledAt());
        
        return notificationRepository.save(existing);
    }

    @Override
    public Optional<SystemNotification> getNotificationById(Long id) {
        return notificationRepository.findById(id);
    }

    @Override
    public List<SystemNotification> getAllNotifications() {
        return notificationRepository.findAll();
    }

    @Override
    public List<SystemNotification> getNotificationsByUserId(Long userId) {
        return notificationRepository.findByTargetUserIdOrderByCreatedAtDesc(userId);
    }

    @Override
    public List<SystemNotification> getNotificationsByRole(UserRole role) {
        return notificationRepository.findByRoleOrGlobal(role);
    }

    @Override
    public List<SystemNotification> getNotificationsByType(NotificationType type) {
        return notificationRepository.findByNotificationType(type);
    }

    @Override
    public List<SystemNotification> getNotificationsByStatus(NotificationStatus status) {
        return notificationRepository.findByStatus(status);
    }

    @Override
    public SystemNotification markAsRead(Long id) {
        SystemNotification notification = notificationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Notification not found with id: " + id));
        
        notification.setStatus(NotificationStatus.READ);
        return notificationRepository.save(notification);
    }

    @Override
    public SystemNotification markAsDelivered(Long id) {
        SystemNotification notification = notificationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Notification not found with id: " + id));
        
        notification.setStatus(NotificationStatus.DELIVERED);
        return notificationRepository.save(notification);
    }

    @Override
    public void deleteNotification(Long id) {
        if (!notificationRepository.existsById(id)) {
            throw new RuntimeException("Notification not found with id: " + id);
        }
        notificationRepository.deleteById(id);
    }

    @Override
    public void sendSystemWideNotification(String title, String message, NotificationType type) {
        // Get first admin or create a system admin notification
        // This is a simplified implementation
        List<com.example.healthsphere.Entities.Admin> admins = adminRepository.findAll();
        if (admins.isEmpty()) {
            throw new RuntimeException("No admin found to create system notification");
        }
        
        SystemNotification notification = new SystemNotification();
        notification.setAdmin(admins.get(0));
        notification.setTitle(title);
        notification.setMessage(message);
        notification.setNotificationType(type);
        notification.setStatus(NotificationStatus.SENT);
        notification.setSentAt(LocalDateTime.now());
        // targetRole is null for system-wide
        
        notificationRepository.save(notification);
    }

    @Override
    public void sendRoleBasedNotification(String title, String message, UserRole role, NotificationType type) {
        List<com.example.healthsphere.Entities.Admin> admins = adminRepository.findAll();
        if (admins.isEmpty()) {
            throw new RuntimeException("No admin found to create notification");
        }
        
        SystemNotification notification = new SystemNotification();
        notification.setAdmin(admins.get(0));
        notification.setTitle(title);
        notification.setMessage(message);
        notification.setNotificationType(type);
        notification.setTargetRole(role);
        notification.setStatus(NotificationStatus.SENT);
        notification.setSentAt(LocalDateTime.now());
        
        notificationRepository.save(notification);
    }
}