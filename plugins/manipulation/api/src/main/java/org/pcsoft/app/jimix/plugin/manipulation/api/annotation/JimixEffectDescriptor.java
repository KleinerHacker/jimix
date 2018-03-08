package org.pcsoft.app.jimix.plugin.manipulation.api.annotation;

import org.pcsoft.app.jimix.plugin.manipulation.api.config.JimixEffectConfiguration;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface JimixEffectDescriptor {
    String name();
    String description();
    String group() default "";
    String resourceBundle() default "";
    String iconPath() default "";

    Class<? extends JimixEffectConfiguration> configurationClass();
}
