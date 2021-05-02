package gov.nih.ncats.application.repositories;

import gov.nih.ncats.application.models.Application;

import gsrs.repository.GsrsVersionedRepository;
import gsrs.repository.GsrsRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.context.annotation.*;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface ApplicationRepository extends GsrsVersionedRepository<Application, Long> {

    Optional<Application> findById(Long id);
}
