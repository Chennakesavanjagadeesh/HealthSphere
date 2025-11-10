package com.example.healthsphere.Service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.healthsphere.Entities.AuditLog;
import com.example.healthsphere.Entities.UserRole;
import com.example.healthsphere.Repositories.AuditLogRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class AuditLogServiceImpl implements AuditLogService {

    @Autowired
    private AuditLogRepository auditLogRepository;

    @Override
    public AuditLog createLog(AuditLog log) {
        return auditLogRepository.save(log);
    }

    @Override
    public Optional<AuditLog> getLogById(Long id) {
        return auditLogRepository.findById(id);
    }

    @Override
    public List<AuditLog> getAllLogs() {
        return auditLogRepository.findAll();
    }

    @Override
    public List<AuditLog> getLogsByUserId(Long userId) {
        return auditLogRepository.findByUserIdOrderByTimestampDesc(userId);
    }

    @Override
    public List<AuditLog> getLogsByUserRole(UserRole role) {
        return auditLogRepository.findByUserRole(role);
    }

    @Override
    public List<AuditLog> getLogsByAction(String action) {
        return auditLogRepository.findByAction(action);
    }

    @Override
    public List<AuditLog> getLogsByEntityType(String entityType) {
        return auditLogRepository.findByEntityType(entityType);
    }

    @Override
    public List<AuditLog> getLogsByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return auditLogRepository.findByDateRange(startDate, endDate);
    }

    @Override
    public List<AuditLog> getLogsByEntityTypeAndId(String entityType, Long entityId) {
        return auditLogRepository.findByEntityTypeAndEntityId(entityType, entityId);
    }

    @Override
    public void logAction(Long userId, String userEmail, UserRole userRole, String action,
                         String entityType, Long entityId, String oldValues, String newValues,
                         String ipAddress, String userAgent) {
        AuditLog log = new AuditLog();
        log.setUserId(userId);
        log.setUserEmail(userEmail);
        log.setUserRole(userRole);
        log.setAction(action);
        log.setEntityType(entityType);
        log.setEntityId(entityId);
        log.setOldValues(oldValues);
        log.setNewValues(newValues);
        log.setIpAddress(ipAddress);
        log.setUserAgent(userAgent);
        
        auditLogRepository.save(log);
    }
}