package gov.hhs.gsrs.application.fda;

import gov.hhs.gsrs.application.application.ApplicationConfiguration;
import gov.hhs.gsrs.application.application.ApplicationSelector;

import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface EnableFdaApplication {
}
