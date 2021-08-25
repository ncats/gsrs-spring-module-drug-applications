package gov.hhs.gsrs.application.searchcount.repositories;

import gov.hhs.gsrs.application.searchcount.models.SearchCountCompositePrimaryKeyId;
import gov.hhs.gsrs.application.searchcount.models.SubstanceSearchCount;

import gsrs.repository.GsrsVersionedRepository;

import org.springframework.context.annotation.*;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SearchCountRepository extends GsrsVersionedRepository<SubstanceSearchCount, SearchCountCompositePrimaryKeyId> {

    @Query("SELECT a FROM SubstanceSearchCount a WHERE a.substanceId = ?1")
    List<SubstanceSearchCount> findSearchCountBySubstanceUuid(String substanceUuid);

}
