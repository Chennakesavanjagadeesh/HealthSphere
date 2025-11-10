package com.example.healthsphere.Repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.healthsphere.Entities.EHR;
import com.example.healthsphere.Entities.EHRAttachment;

import java.util.List;

@Repository
public interface EHRAttachmentRepository extends JpaRepository<EHRAttachment, Long> {
    List<EHRAttachment> findByEhr(EHR ehr);
    List<EHRAttachment> findByEhrId(Long ehrId);
    
    @Query("SELECT ea FROM EHRAttachment ea WHERE ea.ehr.patient.id = :patientId ORDER BY ea.uploadedAt DESC")
    List<EHRAttachment> findByPatientIdOrderByUploadedAtDesc(@Param("patientId") Long patientId);
    
    @Query("SELECT ea FROM EHRAttachment ea WHERE ea.fileType = :fileType")
    List<EHRAttachment> findByFileType(@Param("fileType") String fileType);
}