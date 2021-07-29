package gov.nih.ncats.application.applicationall;

import gov.nih.ncats.application.application.ApplicationStarterEntityRegistrar;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@AutoConfigureAfter(JpaRepositoriesAutoConfiguration.class)
@Import(ApplicationAllStarterEntityRegistrar.class)
public class ApplicationAllConfiguration {
}
