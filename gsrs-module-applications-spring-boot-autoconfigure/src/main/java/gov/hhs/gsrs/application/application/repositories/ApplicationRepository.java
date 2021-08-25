package gov.hhs.gsrs.application.application.repositories;

import gov.hhs.gsrs.application.application.models.Application;
import gov.hhs.gsrs.application.application.models.additional.ApplicationHistory;
import gov.hhs.gsrs.application.application.models.additional.ClinicalTrial;
import gov.hhs.gsrs.application.application.models.additional.ProductEffected;
import gov.hhs.gsrs.application.application.models.additional.ProductTechnicalEffect;

import gsrs.repository.GsrsVersionedRepository;

import org.springframework.context.annotation.*;
import org.springframework.data.jpa.repository.Query;
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
