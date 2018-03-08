package org.pcsoft.app.jimix.plugin.manipulation.api.annotation;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface JimixScalerDescriptor {
    String name();
    String description();
    String resourceBundle() default "";

    boolean usableForPictures() default true;
    boolean usableForMasks() default true;
}
