package org.pcsoft.app.jimix.plugins.api.annotation;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface JimixScalerDescriptor {
    String name();
    String description();

    boolean usableForPictures() default true;
    boolean usableForMasks() default true;
}
