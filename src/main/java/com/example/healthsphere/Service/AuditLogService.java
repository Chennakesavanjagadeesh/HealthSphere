package com.example.healthsphere.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.example.healthsphere.Entities.AuditLog;
import com.example.healthsphere.Entities.UserRole;

public interface AuditLogService {
    AuditLog createLog(AuditLog log);
    Optional<AuditLog> getLogById(Long id);
    List<AuditLog> getAllLogs();
    List<AuditLog> getLogsByUserId(Long userId);
    List<AuditLog> getLogsByUserRole(UserRole role);
    List<AuditLog> getLogsByAction(String action);
    List<AuditLog> getLogsByEntityType(String entityType);
    List<AuditLog> getLogsByDateRange(LocalDateTime startDate, LocalDateTime endDate);
    List<AuditLog> getLogsByEntityTypeAndId(String entityType, Long entityId);
    void logAction(Long userId, String userEmail, UserRole userRole, String action, 
                   String entityType, Long entityId, String oldValues, String newValues, 
                   String ipAddress, String userAgent);
}
