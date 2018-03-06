package org.pcsoft.app.jimix.plugin.mani.api.annotation;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface JimixClipboardProviderDescriptor {
    String name();
    String description();
    String resourceBundle() default "";
    String iconPath() default "";
}
