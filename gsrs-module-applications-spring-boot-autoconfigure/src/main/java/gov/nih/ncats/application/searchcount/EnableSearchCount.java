package gov.nih.ncats.application.searchcount;

import gov.nih.ncats.application.searchcount.SearchCountConfiguration;
import gov.nih.ncats.application.searchcount.SearchCountSelector;
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
