package org.pcsoft.app.jimix.app.util;

import org.controlsfx.control.PropertySheet;
import org.pcsoft.app.jimix.app.ex.SimpleProperty;
import org.pcsoft.app.jimix.plugins.api.annotation.JimixProperty;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;

public final class PropertyUtils {
    public static void addProperties(final PropertySheet propertySheet, final Object model) {
        Class clazz = model.getClass();
        while (clazz != null) {
            for (final Field field : clazz.getDeclaredFields()) {
                final JimixProperty property = field.getAnnotation(JimixProperty.class);
                if (property != null) {
                    //TODO: use resource bundle for texts
                    propertySheet.getItems().add(new SimpleProperty(property.fieldType(),
                            property.name(), property.description(), property.category(),
                            () -> getValueFromField(model, field), v -> setValueToField(model, field, v)));
                }
            }

            clazz = clazz.getSuperclass();
        }
    }

    private static void setValueToField(Object model, Field field, Object v) {
        try {
            final PropertyDescriptor descriptor = org.apache.commons.beanutils.PropertyUtils.getPropertyDescriptor(model, field.getName());
            descriptor.getWriteMethod().invoke(model, v);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static Object getValueFromField(Object model, Field field) {
        try {
            final PropertyDescriptor descriptor = org.apache.commons.beanutils.PropertyUtils.getPropertyDescriptor(model, field.getName());
            return descriptor.getReadMethod().invoke(model);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private PropertyUtils() {
    }
}
