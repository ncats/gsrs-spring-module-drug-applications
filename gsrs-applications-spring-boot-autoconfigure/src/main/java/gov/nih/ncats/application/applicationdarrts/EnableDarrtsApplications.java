package gov.nih.ncats.application.applicationdarrts;

import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Import({DarrtsSelector.class, DarrtsApplicationConfiguration.class})
public @interface EnableDarrtsApplications {
}
