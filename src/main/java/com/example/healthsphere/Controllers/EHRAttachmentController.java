package com.example.healthsphere.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.example.healthsphere.Entities.EHRAttachment;
import com.example.healthsphere.Service.EHRAttachmentService;

import java.util.List;

@RestController
@RequestMapping("/api/ehr-attachments")
@CrossOrigin(origins = "*")
public class EHRAttachmentController {

    @Autowired
    private EHRAttachmentService attachmentService;

    @PostMapping("/upload")
    public ResponseEntity<EHRAttachment> uploadAttachment(@RequestParam Long ehrId,
                                                         @RequestParam("file") MultipartFile file,
                                                         @RequestParam(required = false) String description) {
        EHRAttachment attachment = attachmentService.uploadAttachment(ehrId, file, description);
        return new ResponseEntity<>(attachment, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EHRAttachment> getAttachmentById(@PathVariable Long id) {
        return attachmentService.getAttachmentById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/ehr/{ehrId}")
    public ResponseEntity<List<EHRAttachment>> getAttachmentsByEHRId(@PathVariable Long ehrId) {
        List<EHRAttachment> attachments = attachmentService.getAttachmentsByEHRId(ehrId);
        return ResponseEntity.ok(attachments);
    }

    @GetMapping("/patient/{patientId}")
    public ResponseEntity<List<EHRAttachment>> getAttachmentsByPatientId(@PathVariable Long patientId) {
        List<EHRAttachment> attachments = attachmentService.getAttachmentsByPatientId(patientId);
        return ResponseEntity.ok(attachments);
    }

    @GetMapping("/{id}/download")
    public ResponseEntity<byte[]> downloadAttachment(@PathVariable Long id) {
        byte[] fileData = attachmentService.downloadAttachment(id);
        EHRAttachment attachment = attachmentService.getAttachmentById(id)
                .orElseThrow(() -> new RuntimeException("Attachment not found"));
        
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", attachment.getFileName());
        
        return new ResponseEntity<>(fileData, headers, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAttachment(@PathVariable Long id) {
        attachmentService.deleteAttachment(id);
        return ResponseEntity.noContent().build();
    }
}
