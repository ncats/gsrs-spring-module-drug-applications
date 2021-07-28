package gov.nih.ncats.application.applicationdarrts.repositories;

import gov.nih.ncats.application.applicationdarrts.models.ApplicationDarrts;
import gov.nih.ncats.application.applicationdarrts.models.ApplicationDarrtsCompositePrimaryKeyId;
import gov.nih.ncats.application.applicationdarrts.models.SubstanceKeyParentConcept;
import gsrs.repository.GsrsVersionedRepository;
import org.springframework.context.annotation.*;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ApplicationDarrtsRepository extends GsrsVersionedRepository<ApplicationDarrts, ApplicationDarrtsCompositePrimaryKeyId> {

   // Optional<ApplicationDarrts> findById(String appType, String appNumber);

    @Query("SELECT a FROM ApplicationDarrts a WHERE a.appType = ?1 and a.appNumber = ?2")
    List<ApplicationDarrts> getApplicationByApptypeAndAppnumber(String appType, String appNumber);

    @Query("SELECT a FROM SubstanceKeyParentConcept a WHERE a.substanceKey = ?1")
    List<SubstanceKeyParentConcept> getSubstanceKeyParentConcept(String substanceKey);

}
