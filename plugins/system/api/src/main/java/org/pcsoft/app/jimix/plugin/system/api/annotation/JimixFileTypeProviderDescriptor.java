package org.pcsoft.app.jimix.plugin.system.api.annotation;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface JimixFileTypeProviderDescriptor {
    String description();
    String[] extensions();
    String resourceBundle() default "";

    int magicBytesCount();
}
