package org.pcsoft.app.jimix.app.util;

import javafx.beans.value.ObservableValue;
import javafx.scene.control.Slider;
import org.apache.commons.lang.StringUtils;
import org.controlsfx.control.PropertySheet;
import org.controlsfx.property.editor.AbstractPropertyEditor;
import org.pcsoft.app.jimix.app.language.LanguageResources;
import org.pcsoft.app.jimix.app.ui.component.prop_sheet.JimixPropertySheet;
import org.pcsoft.app.jimix.app.ui.component.prop_sheet.editor.FontPropertyEditor;
import org.pcsoft.app.jimix.commons.exception.JimixPropertyException;
import org.pcsoft.app.jimix.plugin.common.api.annotation.JimixProperty;
import org.pcsoft.app.jimix.plugin.common.api.annotation.JimixPropertyDoubleRestriction;
import org.pcsoft.app.jimix.plugin.common.api.annotation.JimixPropertyFontRestriction;
import org.pcsoft.app.jimix.plugin.common.api.annotation.JimixPropertyIntegerRestriction;
import org.pcsoft.framework.jfex.property.SimpleWrapperProperty;
import org.pcsoft.framework.jfex.type.SimpleProperty;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.util.ResourceBundle;

public final class PropertyUtils {
    public static void addProperties(final JimixPropertySheet propertySheet, final Object model) {
        Class clazz = model.getClass();
        while (clazz != null) {
            for (final Field field : clazz.getDeclaredFields()) {
                final JimixProperty property = field.getAnnotation(JimixProperty.class);
                final JimixPropertyIntegerRestriction integerRestriction = field.getAnnotation(JimixPropertyIntegerRestriction.class);
                final JimixPropertyDoubleRestriction doubleRestriction = field.getAnnotation(JimixPropertyDoubleRestriction.class);
                final JimixPropertyFontRestriction fontRestriction = field.getAnnotation(JimixPropertyFontRestriction.class);

                if (property != null) {
                    final ResourceBundle resourceBundle;
                    if (!StringUtils.isEmpty(property.resourceBundle())) {
                        resourceBundle = ResourceBundle.getBundle(property.resourceBundle());
                    } else {
                        resourceBundle = null;
                    }

                    final ObservableValue observable;
                    if (ObservableValue.class.isAssignableFrom(field.getType())) {
                        try {
                            field.setAccessible(true);
                            observable = (ObservableValue) field.get(model);
                        } catch (IllegalAccessException e) {
                            throw new JimixPropertyException("Unable to get observable from field: " + field.getDeclaringClass().getName() + "#" + field.getName(), e);
                        }
                    } else {
                        observable = null;
                    }

                    final SimpleProperty<Object> sheetItem = new SimpleProperty<Object>(property.fieldType(),
                            resourceBundle == null ? property.name() : resourceBundle.getString(property.name()),
                            resourceBundle == null ? property.description() : resourceBundle.getString(property.description()),
                            StringUtils.isEmpty(property.category()) ? LanguageResources.getText("common.property.category.default") :
                                    resourceBundle == null ? property.category() : resourceBundle.getString(property.category()),
                            () -> getValueFromField(model, field), v -> setValueToField(model, field, v), observable);

                    if (integerRestriction != null) {
                        buildIntegerRestriction(propertySheet, sheetItem, integerRestriction);
                    } else if (doubleRestriction != null) {
                        buildDoubleRestriction(propertySheet, sheetItem, doubleRestriction);
                    } else if (fontRestriction != null) {
                        buildFontRestriction(propertySheet, sheetItem, fontRestriction);
                    }

                    propertySheet.getItems().add(sheetItem);
                }
            }

            clazz = clazz.getSuperclass();
        }
    }

    //<editor-fold desc="Restriction Methods">
    private static void buildIntegerRestriction(final JimixPropertySheet propertySheet, final PropertySheet.Item sheetItem, final JimixPropertyIntegerRestriction restriction) {
        final Slider slider = new Slider();
        slider.setMin(restriction.minValue());
        slider.setMax(restriction.maxValue());
        slider.setValue((int) sheetItem.getValue());

        propertySheet.addItemSupport(
                sheetItem, item -> new AbstractPropertyEditor<Number, Slider>(item, slider) {
                    @Override
                    protected ObservableValue<Number> getObservableValue() {
                        return new SimpleWrapperProperty<Number, Number>(getEditor().valueProperty()) {
                            @Override
                            protected Number convertTo(Number value) {
                                return value.intValue();
                            }

                            @Override
                            protected Number convertFrom(Number value) {
                                return value.doubleValue();
                            }
                        };
                    }

                    @Override
                    public void setValue(Number value) {
                        getEditor().setValue(value.intValue());
                    }
                }
        );
    }

    private static void buildDoubleRestriction(final JimixPropertySheet propertySheet, final PropertySheet.Item sheetItem, final JimixPropertyDoubleRestriction restriction) {
        final Slider slider = new Slider();
        slider.setMin(restriction.minValue());
        slider.setMax(restriction.maxValue());
        slider.setValue((double) sheetItem.getValue());

        propertySheet.addItemSupport(
                sheetItem, item -> new AbstractPropertyEditor<Number, Slider>(item, slider) {
                    @Override
                    protected ObservableValue<Number> getObservableValue() {
                        return getEditor().valueProperty();
                    }

                    @Override
                    public void setValue(Number value) {
                        getEditor().setValue(value.doubleValue());
                    }
                }
        );
    }

    private static void buildFontRestriction(final JimixPropertySheet propertySheet, final PropertySheet.Item sheetItem, final JimixPropertyFontRestriction restriction) {
        propertySheet.addItemSupport(sheetItem, FontPropertyEditor::new);
    }
    //</editor-fold>

    //<editor-fold desc="Helper Methods">
    private static void setValueToField(Object model, Field field, Object v) throws JimixPropertyException {
        try {
            final PropertyDescriptor descriptor = org.apache.commons.beanutils.PropertyUtils.getPropertyDescriptor(model, field.getName());
            descriptor.getWriteMethod().invoke(model, v);
        } catch (Exception e) {
            throw new JimixPropertyException("Unable to set value to field: " + field.getDeclaringClass().getName() + "#" + field.getName(), e);
        }
    }

    private static Object getValueFromField(Object model, Field field) throws JimixPropertyException {
        try {
            final PropertyDescriptor descriptor = org.apache.commons.beanutils.PropertyUtils.getPropertyDescriptor(model, field.getName());
            return descriptor.getReadMethod().invoke(model);
        } catch (Exception e) {
            throw new JimixPropertyException("Unable to get value from field: " + field.getDeclaringClass().getName() + "#" + field.getName(), e);
        }
    }
    //</editor-fold>

    private PropertyUtils() {
    }
}
