package gov.hhs.gsrs.application.applicationall;

import gov.hhs.gsrs.application.applicationall.ApplicationAllStarterEntityRegistrar;

import gsrs.services.PrivilegeService;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@AutoConfigureAfter(JpaRepositoriesAutoConfiguration.class)
@Import({ApplicationAllStarterEntityRegistrar.class, PrivilegeService.class})
public class ApplicationAllConfiguration {
}
