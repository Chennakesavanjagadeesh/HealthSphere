package com.example.healthsphere.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.example.healthsphere.Entities.EHR;
import com.example.healthsphere.Entities.EHRAttachment;
import com.example.healthsphere.Repositories.EHRAttachmentRepository;
import com.example.healthsphere.Repositories.EHRRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class EHRAttachmentServiceImpl implements EHRAttachmentService {

    @Autowired
    private EHRAttachmentRepository attachmentRepository;
    
    @Autowired
    private EHRRepository ehrRepository;
    
    @Value("${file.upload-dir:./uploads/ehr-attachments}")
    private String uploadDir;

    @Override
    public EHRAttachment uploadAttachment(Long ehrId, MultipartFile file, String description) {
        EHR ehr = ehrRepository.findById(ehrId)
                .orElseThrow(() -> new RuntimeException("EHR not found with id: " + ehrId));
        
        if (file.isEmpty()) {
            throw new RuntimeException("File is empty");
        }
        
        try {
            // Create upload directory if it doesn't exist
            Path uploadPath = Paths.get(uploadDir);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }
            
            // Generate unique filename
            String originalFilename = file.getOriginalFilename();
            String extension = originalFilename != null && originalFilename.contains(".") 
                ? originalFilename.substring(originalFilename.lastIndexOf(".")) 
                : "";
            String uniqueFilename = UUID.randomUUID().toString() + extension;
            
            // Save file
            Path filePath = uploadPath.resolve(uniqueFilename);
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
            
            // Create attachment record
            EHRAttachment attachment = new EHRAttachment();
            attachment.setEhr(ehr);
            attachment.setFileName(originalFilename);
            attachment.setFilePath(filePath.toString());
            attachment.setFileType(file.getContentType());
            attachment.setFileSize(file.getSize());
            attachment.setDescription(description);
            
            return attachmentRepository.save(attachment);
            
        } catch (IOException e) {
            throw new RuntimeException("Failed to upload file: " + e.getMessage(), e);
        }
    }

    @Override
    public Optional<EHRAttachment> getAttachmentById(Long id) {
        return attachmentRepository.findById(id);
    }

    @Override
    public List<EHRAttachment> getAttachmentsByEHRId(Long ehrId) {
        return attachmentRepository.findByEhrId(ehrId);
    }

    @Override
    public List<EHRAttachment> getAttachmentsByPatientId(Long patientId) {
        return attachmentRepository.findByPatientIdOrderByUploadedAtDesc(patientId);
    }

    @Override
    public void deleteAttachment(Long id) {
        EHRAttachment attachment = attachmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Attachment not found with id: " + id));
        
        try {
            // Delete physical file
            Path filePath = Paths.get(attachment.getFilePath());
            Files.deleteIfExists(filePath);
            
            // Delete database record
            attachmentRepository.deleteById(id);
            
        } catch (IOException e) {
            throw new RuntimeException("Failed to delete file: " + e.getMessage(), e);
        }
    }

    @Override
    public byte[] downloadAttachment(Long id) {
        EHRAttachment attachment = attachmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Attachment not found with id: " + id));
        
        try {
            Path filePath = Paths.get(attachment.getFilePath());
            return Files.readAllBytes(filePath);
        } catch (IOException e) {
            throw new RuntimeException("Failed to read file: " + e.getMessage(), e);
        }
    }
}