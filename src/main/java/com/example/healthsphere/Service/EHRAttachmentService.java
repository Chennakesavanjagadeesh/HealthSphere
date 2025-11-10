package com.example.healthsphere.Service;


import org.springframework.web.multipart.MultipartFile;

import com.example.healthsphere.Entities.EHRAttachment;

import java.util.List;
import java.util.Optional;

public interface EHRAttachmentService {
    EHRAttachment uploadAttachment(Long ehrId, MultipartFile file, String description);
    Optional<EHRAttachment> getAttachmentById(Long id);
    List<EHRAttachment> getAttachmentsByEHRId(Long ehrId);
    List<EHRAttachment> getAttachmentsByPatientId(Long patientId);
    void deleteAttachment(Long id);
    byte[] downloadAttachment(Long id);
}