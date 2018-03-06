package org.pcsoft.app.jimix.plugin.mani.api.annotation;

import org.pcsoft.app.jimix.plugin.mani.api.type.JimixPluginElement;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface JimixElementDrawerDescriptor {
    Class<? extends JimixPluginElement> elementModelClass();
}
