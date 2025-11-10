package com.example.healthsphere.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.healthsphere.Entities.NotificationStatus;
import com.example.healthsphere.Entities.NotificationType;
import com.example.healthsphere.Entities.SystemNotification;
import com.example.healthsphere.Entities.UserRole;
import com.example.healthsphere.Service.SystemNotificationService;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/notifications")
@CrossOrigin(origins = "*")
public class SystemNotificationController {

    @Autowired
    private SystemNotificationService notificationService;

    @PostMapping
    public ResponseEntity<SystemNotification> createNotification(@Valid @RequestBody SystemNotification notification) {
        SystemNotification createdNotification = notificationService.createNotification(notification);
        return new ResponseEntity<>(createdNotification, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SystemNotification> updateNotification(@PathVariable Long id, @Valid @RequestBody SystemNotification notification) {
        SystemNotification updatedNotification = notificationService.updateNotification(id, notification);
        return ResponseEntity.ok(updatedNotification);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SystemNotification> getNotificationById(@PathVariable Long id) {
        return notificationService.getNotificationById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<SystemNotification>> getAllNotifications() {
        List<SystemNotification> notifications = notificationService.getAllNotifications();
        return ResponseEntity.ok(notifications);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<SystemNotification>> getNotificationsByUserId(@PathVariable Long userId) {
        List<SystemNotification> notifications = notificationService.getNotificationsByUserId(userId);
        return ResponseEntity.ok(notifications);
    }

    @GetMapping("/role/{role}")
    public ResponseEntity<List<SystemNotification>> getNotificationsByRole(@PathVariable UserRole role) {
        List<SystemNotification> notifications = notificationService.getNotificationsByRole(role);
        return ResponseEntity.ok(notifications);
    }

    @GetMapping("/type/{type}")
    public ResponseEntity<List<SystemNotification>> getNotificationsByType(@PathVariable NotificationType type) {
        List<SystemNotification> notifications = notificationService.getNotificationsByType(type);
        return ResponseEntity.ok(notifications);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<SystemNotification>> getNotificationsByStatus(@PathVariable NotificationStatus status) {
        List<SystemNotification> notifications = notificationService.getNotificationsByStatus(status);
        return ResponseEntity.ok(notifications);
    }

    @PutMapping("/{id}/mark-read")
    public ResponseEntity<SystemNotification> markAsRead(@PathVariable Long id) {
        SystemNotification notification = notificationService.markAsRead(id);
        return ResponseEntity.ok(notification);
    }

    @PutMapping("/{id}/mark-delivered")
    public ResponseEntity<SystemNotification> markAsDelivered(@PathVariable Long id) {
        SystemNotification notification = notificationService.markAsDelivered(id);
        return ResponseEntity.ok(notification);
    }

    @PostMapping("/system-wide")
    public ResponseEntity<Void> sendSystemWideNotification(@RequestParam String title,
                                                           @RequestParam String message,
                                                           @RequestParam NotificationType type) {
        notificationService.sendSystemWideNotification(title, message, type);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/role-based")
    public ResponseEntity<Void> sendRoleBasedNotification(@RequestParam String title,
                                                          @RequestParam String message,
                                                          @RequestParam UserRole role,
                                                          @RequestParam NotificationType type) {
        notificationService.sendRoleBasedNotification(title, message, role, type);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNotification(@PathVariable Long id) {
        notificationService.deleteNotification(id);
        return ResponseEntity.noContent().build();
    }
}
