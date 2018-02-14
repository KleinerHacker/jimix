package org.pcsoft.app.jimix.plugins.api.annotation;

import org.pcsoft.app.jimix.plugins.api.config.JimixRendererConfiguration;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface JimixRendererDescriptor {
    String name();
    String description();
    String iconPath() default "";

    boolean usableForPictures() default true;
    boolean usableForMasks() default false;

    Class<? extends JimixRendererConfiguration> configurationClass();
}
