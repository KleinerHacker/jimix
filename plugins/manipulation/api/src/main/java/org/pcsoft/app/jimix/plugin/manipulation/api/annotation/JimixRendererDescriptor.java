package org.pcsoft.app.jimix.plugin.manipulation.api.annotation;

import org.pcsoft.app.jimix.plugin.manipulation.api.config.JimixRendererConfiguration;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface JimixRendererDescriptor {
    String name();
    String description();
    String group() default "";
    String resourceBundle() default "";
    String iconPath() default "";

    boolean usableForPictures() default true;
    boolean usableForMasks() default false;

    Class<? extends JimixRendererConfiguration> configurationClass();
}
