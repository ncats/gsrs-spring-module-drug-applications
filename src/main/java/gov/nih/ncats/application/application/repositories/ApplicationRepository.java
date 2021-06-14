package gov.nih.ncats.application.application.repositories;

import gov.nih.ncats.application.application.models.*;

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

    @Query("SELECT DISTINCT a.center || ' GSRS' FROM Application a WHERE a.id in (SELECT p.applicationId FROM ApplicationProduct p LEFT JOIN ApplicationIngredient i on p.id = i.productId where i.substanceKey = ?1)")
    List<String> findCenterBySubstanceKey(String substanceKey);

}
