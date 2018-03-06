package org.pcsoft.app.jimix.plugin.common.api.annotation;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface JimixProperty {
    Class fieldType();
    String name();
    String description();
    String category() default "";
    String resourceBundle() default "";
}
