package org.pcsoft.app.jimix.plugin.mani.api.annotation;


import org.pcsoft.app.jimix.plugin.mani.api.config.JimixFilterConfiguration;
import org.pcsoft.app.jimix.plugin.mani.api.type.JimixFilterType;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface JimixFilterDescriptor {
    String name();
    String description();
    String group() default "";
    String iconPath() default "";
    String resourceBundle() default "";
    JimixFilterType type() default JimixFilterType.ColorFilter;

    boolean usableForPictures() default true;
    boolean usableForMasks() default false;

    boolean onTopLevel() default false;

    Class<? extends JimixFilterConfiguration> configurationClass();
}
