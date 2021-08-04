package gov.nih.ncats.application.application;

import gov.nih.ncats.application.applicationdarrts.DarrtsStarterEntityRegistrar;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@AutoConfigureAfter(JpaRepositoriesAutoConfiguration.class)
@Import(ApplicationStarterEntityRegistrar.class)
public class ApplicationConfiguration {
}
