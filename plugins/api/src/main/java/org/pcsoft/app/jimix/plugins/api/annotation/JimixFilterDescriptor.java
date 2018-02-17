package org.pcsoft.app.jimix.plugins.api.annotation;

import org.pcsoft.app.jimix.plugins.api.config.JimixFilterConfiguration;
import org.pcsoft.app.jimix.plugins.api.type.JimixFilterType;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface JimixFilterDescriptor {
    String name();
    String description();
    String iconPath() default "";
    JimixFilterType type() default JimixFilterType.ColorFilter;

    boolean onTopLevel() default false;

    Class<? extends JimixFilterConfiguration> configurationClass();
}
