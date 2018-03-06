package org.pcsoft.app.jimix.plugin.common.api.annotation;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface JimixPropertyDoubleRestriction {
    double minValue();
    double maxValue();
}
