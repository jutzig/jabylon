package de.jutzig.jabylon.rest.ui.security;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.PACKAGE, ElementType.TYPE })
@Documented
@Inherited
public @interface ModelAuthorization {

    /**
     * The permissions that are required to execute this action
     *
     * @return The permissions that are required to execute this action. Returns a zero length array by default
     */
    String[] value() default { };

}
