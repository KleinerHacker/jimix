package org.pcsoft.app.jimix.plugin.manipulation.api.annotation;

import org.pcsoft.app.jimix.plugin.common.api.type.JimixPluginElement;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface JimixElementBuilderDescriptor {
    String name();
    String description();
    String group() default "";
    String resourceBundle() default "";
    String iconPath() default "";

    /**
     * Is element addable via element choicer in editor
     * @return
     */
    boolean manualAddable() default false;

    Class<? extends JimixPluginElement> elementModelClass();
}
