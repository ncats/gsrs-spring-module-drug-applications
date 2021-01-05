package gov.nih.ncats.application;

import gov.nih.ncats.application.model.Application;
import gsrs.repository.GsrsRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface ApplicationRepository extends GsrsRepository<Application, Long> {

    Optional<Application> findByTitle(String title);
}
