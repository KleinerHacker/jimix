package org.pcsoft.app.jimix.app.language;

import sun.misc.CompoundEnumeration;

import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.ResourceBundle;

public class MultipleResourceBundle extends ResourceBundle {

    private final List<ResourceBundle> resourceBundleList;

    public MultipleResourceBundle(ResourceBundle... resourceBundleList) {
        this.resourceBundleList = Arrays.asList(resourceBundleList);
    }

    public MultipleResourceBundle(List<ResourceBundle> resourceBundleList) {
        this.resourceBundleList = resourceBundleList;
    }

    @Override
    protected Object handleGetObject(String key) {
        for (final ResourceBundle resourceBundle : resourceBundleList) {
            if (resourceBundle.containsKey(key))
                return resourceBundle.getObject(key);
        }

        return null;
    }

    @Override
    public Enumeration<String> getKeys() {
        final Enumeration<String>[] keySets = new Enumeration[resourceBundleList.size()];
        for (int i=0; i<resourceBundleList.size(); i++) {
            keySets[i] = resourceBundleList.get(i).getKeys();
        }

        return new CompoundEnumeration<>(keySets);
    }
}
