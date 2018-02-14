package org.pcsoft.app.jimix.plugins.api.annotation;

import org.pcsoft.app.jimix.plugins.api.config.JimixEffectConfiguration;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface JimixEffectDescriptor {
    String name();
    String description();
    String iconPath() default "";

    boolean usableForPictures() default true;
    boolean usableForMasks() default false;

    Class<? extends JimixEffectConfiguration> configurationClass();
}
