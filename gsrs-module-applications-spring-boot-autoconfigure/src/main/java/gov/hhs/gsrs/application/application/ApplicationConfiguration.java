package gov.hhs.gsrs.application.application;

import gov.hhs.gsrs.application.application.ApplicationStarterEntityRegistrar;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@AutoConfigureAfter(JpaRepositoriesAutoConfiguration.class)
@Import(ApplicationStarterEntityRegistrar.class)
public class ApplicationConfiguration {
}
