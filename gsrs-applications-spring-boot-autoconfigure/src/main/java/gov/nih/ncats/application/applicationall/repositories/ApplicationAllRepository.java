package gov.nih.ncats.application.applicationall.repositories;

import gov.nih.ncats.application.applicationall.models.ApplicationAll;
import gsrs.repository.GsrsVersionedRepository;
import org.springframework.context.annotation.*;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface ApplicationAllRepository extends GsrsVersionedRepository<ApplicationAll, String> {

    Optional<ApplicationAll> findById(String id);

    @Query("SELECT DISTINCT a.center || ' ' || a.fromTable FROM ApplicationAll a WHERE a.id in (SELECT p.applicationId FROM ProductSrsAll p LEFT JOIN AppIngredientAll i on p.id = i.productId where i.substanceKey = ?1)")
    List<String> findCenterBySubstanceKey(String substanceKey);
}
