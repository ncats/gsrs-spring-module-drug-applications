package gov.nih.ncats.application.searchcount.repositories;

import gov.nih.ncats.application.searchcount.models.*;
import gov.nih.ncats.application.searchcount.models.*;

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
public interface SearchCountRepository extends GsrsVersionedRepository<SubstanceSearchCount, SearchCountCompositePrimaryKeyId> {

    @Query("SELECT a FROM SubstanceSearchCount a WHERE a.substanceId = ?1")
    List<SubstanceSearchCount> findSearchCountBySubstanceUuid(String substanceUuid);

}
