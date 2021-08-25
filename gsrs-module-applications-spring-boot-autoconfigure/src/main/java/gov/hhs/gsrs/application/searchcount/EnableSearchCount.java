package gov.hhs.gsrs.application.searchcount;

import gov.hhs.gsrs.application.searchcount.SearchCountConfiguration;
import gov.hhs.gsrs.application.searchcount.SearchCountSelector;

import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Import({SearchCountSelector.class, SearchCountConfiguration.class})
public @interface EnableSearchCount {
}
