package org.pcsoft.app.jimix.plugin.mani.manager;

import javafx.beans.property.ReadOnlyListProperty;
import javafx.beans.property.ReadOnlyListWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import org.pcsoft.app.jimix.commons.JimixConstants;
import org.pcsoft.app.jimix.plugin.mani.api.config.JimixEffectConfiguration;
import org.pcsoft.app.jimix.plugin.mani.api.config.JimixFilterConfiguration;
import org.pcsoft.app.jimix.plugin.mani.api.type.JimixEffectVariant;
import org.pcsoft.app.jimix.plugin.mani.api.type.JimixFilterVariant;
import org.pcsoft.app.jimix.plugin.mani.manager.type.JimixEffectPlugin;
import org.pcsoft.app.jimix.plugin.mani.manager.type.JimixFilterPlugin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

public final class ManipulationPluginVariantManager {
    private static final Logger LOGGER = LoggerFactory.getLogger(ManipulationPluginVariantManager.class);
    private static final String KEY_FILTER = "FILTER";
    private static final String KEY_EFFECT = "EFFECT";

    private static final ReadOnlyListProperty<JimixFilterVariant> filterVariantList =
            new ReadOnlyListWrapper<JimixFilterVariant>(FXCollections.observableArrayList()).getReadOnlyProperty();
    private static final ReadOnlyListProperty<JimixEffectVariant> effectVariantList =
            new ReadOnlyListWrapper<JimixEffectVariant>(FXCollections.observableArrayList()).getReadOnlyProperty();

    static {
        load();

        filterVariantList.addListener((ListChangeListener<JimixFilterVariant>) c -> save());
        effectVariantList.addListener((ListChangeListener<JimixEffectVariant>) c -> save());
    }

    public static ObservableList<JimixFilterVariant> getFilterVariantList() {
        return filterVariantList.get();
    }

    public static ReadOnlyListProperty<JimixFilterVariant> filterVariantListProperty() {
        return filterVariantList;
    }

    public static ObservableList<JimixEffectVariant> getEffectVariantList() {
        return effectVariantList.get();
    }

    public static ReadOnlyListProperty<JimixEffectVariant> effectVariantListProperty() {
        return effectVariantList;
    }

    //<editor-fold desc="Load">
    private static void load() {
        rebuildLists();

        try (final InputStream in = new FileInputStream(JimixConstants.DEFAULT_MANIPULATION_VARIANT_FILE)) {
            try (final ObjectInputStream oin = new ObjectInputStream(in)) {
                readFilter(oin);
                readEffect(oin);
            }
        } catch (IOException | AssertionError | ClassNotFoundException e) {
            LOGGER.warn("Unable to load variant file " + JimixConstants.DEFAULT_MANIPULATION_VARIANT_FILE.getAbsolutePath(), e);
        }
    }

    private static void readEffect(ObjectInputStream oin) throws IOException, ClassNotFoundException {
        assert oin.readUTF().equals(KEY_EFFECT);
        final int effectSize = oin.readInt();
        for (int i = 0; i < effectSize; i++) {
            final String name = oin.readUTF();
            final JimixEffectConfiguration configuration = (JimixEffectConfiguration) oin.readObject();
            effectVariantList.add(JimixEffectVariant.createCustom(name, configuration));
        }
    }

    private static void readFilter(ObjectInputStream oin) throws IOException, ClassNotFoundException {
        assert oin.readUTF().equals(KEY_FILTER);
        final int filterSize = oin.readInt();
        for (int i = 0; i < filterSize; i++) {
            final String name = oin.readUTF();
            final JimixFilterConfiguration configuration = (JimixFilterConfiguration) oin.readObject();
            filterVariantList.add(JimixFilterVariant.createCustom(name, configuration));
        }
    }
    //</editor-fold>

    //<editor-fold desc="Save">
    private static void save() {
        try (final OutputStream out = new FileOutputStream(JimixConstants.DEFAULT_MANIPULATION_VARIANT_FILE)) {
            try (final ObjectOutputStream oout = new ObjectOutputStream(out)) {
                saveFilter(oout);
                saveEffect(oout);
            }
        } catch (IOException e) {
            LOGGER.warn("Unable to save variant file " + JimixConstants.DEFAULT_MANIPULATION_VARIANT_FILE.getAbsolutePath(), e);
        }
    }

    private static void saveEffect(ObjectOutputStream oout) throws IOException {
        oout.writeUTF(KEY_EFFECT);
        for (final JimixEffectVariant variant : effectVariantList) {
            oout.writeUTF(variant.getName());
            oout.writeObject(variant.getConfiguration());
        }
    }

    private static void saveFilter(ObjectOutputStream oout) throws IOException {
        oout.writeUTF(KEY_FILTER);
        oout.writeInt(filterVariantList.size());
        for (final JimixFilterVariant variant : filterVariantList) {
            oout.writeUTF(variant.getName());
            oout.writeObject(variant.getConfiguration());
        }
    }
    //</editor-fold>

    private static void rebuildLists() {
        filterVariantList.clear();
        for (final JimixFilterPlugin filterPlugin : ManipulationPluginManager.getInstance().getAllFilters()) {
            filterVariantList.addAll(filterPlugin.getVariants());
        }

        effectVariantList.clear();
        for (final JimixEffectPlugin effectPlugin : ManipulationPluginManager.getInstance().getAllEffects()) {
            effectVariantList.addAll(effectPlugin.getVariants());
        }
    }

    private ManipulationPluginVariantManager() {
    }
}
