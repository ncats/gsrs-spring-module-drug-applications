package gov.nih.ncats.application.fda;

import gov.nih.ncats.application.application.ApplicationConfiguration;
import gov.nih.ncats.application.application.ApplicationSelector;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface EnableFdaApplication {
}
