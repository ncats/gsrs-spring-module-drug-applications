package gov.nih.ncats.application.application.repositories;

import gov.nih.ncats.application.application.models.*;
import gov.nih.ncats.application.application.models.additional.*;

import gov.nih.ncats.application.applicationall.models.ApplicationAll;
import gsrs.repository.GsrsVersionedRepository;
import gsrs.repository.GsrsRepository;

import ix.core.models.ETag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.context.annotation.*;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface ApplicationRepository extends GsrsVersionedRepository<Application, Long> {

    Optional<Application> findById(Long id);

    @Query("SELECT a FROM ApplicationHistory a WHERE a.applicationId = ?1")
    List<ApplicationHistory> findApplicationHistoryByApplicationId(String applicationId);

    @Query("SELECT a FROM ProductTechnicalEffect a WHERE a.applicationId = ?1")
    List<ProductTechnicalEffect> findProductTechnicalEffectByApplicationId(String applicationId);

    @Query("SELECT a FROM ProductEffected a WHERE a.applicationId = ?1")
    List<ProductEffected> findProductEffectedByApplicationId(String applicationId);

    @Query("SELECT a FROM ClinicalTrial a WHERE a.nctn in (select b.nctNumber from ClinicalTrialApplication b WHERE b.applicationId = ?1)")
    List<ClinicalTrial> findClinicalTrialByApplicationId(String applicationId);


}
