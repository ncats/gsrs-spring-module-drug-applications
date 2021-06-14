package gov.nih.ncats.application.applicationdarrts.repositories;

import gov.nih.ncats.application.applicationdarrts.models.*;
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
public interface ApplicationDarrtsRepository extends GsrsVersionedRepository<ApplicationDarrts, String> {

    Optional<ApplicationDarrts> findById(String id);

}
