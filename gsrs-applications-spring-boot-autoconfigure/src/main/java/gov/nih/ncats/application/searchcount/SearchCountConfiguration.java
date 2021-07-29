package gov.nih.ncats.application.searchcount;

import gov.nih.ncats.application.searchcount.SearchCountStarterEntityRegistrar;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@AutoConfigureAfter(JpaRepositoriesAutoConfiguration.class)
@Import(SearchCountStarterEntityRegistrar.class)
public class SearchCountConfiguration {
}
