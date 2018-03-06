package org.pcsoft.app.jimix.plugin.mani.manager;

import javafx.beans.property.ReadOnlyListProperty;
import javafx.beans.property.ReadOnlyListWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import org.apache.commons.codec.Charsets;
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
import java.util.List;
import java.util.stream.Collectors;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public final class ManipulationPluginVariantManager {
    private static final Logger LOGGER = LoggerFactory.getLogger(ManipulationPluginVariantManager.class);
    private static final String KEY_VARIANT = "VARIANT";
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
            final byte[] keyBuffer = new byte[KEY_VARIANT.length()];
            new DataInputStream(in).readFully(keyBuffer);
            assert KEY_VARIANT.equals(new String(keyBuffer, Charsets.US_ASCII));

            try (final GZIPInputStream zipIn = new GZIPInputStream(in)) {
                try (final ObjectInputStream oin = new ObjectInputStream(zipIn)) {
                    readFilter(oin);
                    readEffect(oin);
                }
            }
        } catch (IOException | AssertionError | ClassNotFoundException e) {
            LOGGER.warn("Unable to load variant file " + JimixConstants.DEFAULT_MANIPULATION_VARIANT_FILE.getAbsolutePath(), e);
        }
    }

    @SuppressWarnings("unchecked")
    private static void readEffect(ObjectInputStream oin) throws IOException, ClassNotFoundException {
        final String key = oin.readUTF();
        assert key.equals(KEY_EFFECT);
        final int effectSize = oin.readInt();
        for (int i = 0; i < effectSize; i++) {
            final String name = oin.readUTF();
            final String configurationClassName = oin.readUTF();
            try {
                final Class<JimixEffectConfiguration> configurationClass = (Class<JimixEffectConfiguration>) Class.forName(configurationClassName,
                        true, ManipulationPluginManager.getInstance().getClassLoader());
                final JimixEffectConfiguration configuration = configurationClass.newInstance();
                configuration.load(oin);
                effectVariantList.add(JimixEffectVariant.createCustom(name, configuration));
            } catch (InstantiationException | IllegalAccessException e) {
                LOGGER.warn("Unable to find effect configuration " + configurationClassName + ", skip", e);
            }
        }
    }

    @SuppressWarnings("unchecked")
    private static void readFilter(ObjectInputStream oin) throws IOException, ClassNotFoundException {
        final String key = oin.readUTF();
        assert key.equals(KEY_FILTER);
        final int filterSize = oin.readInt();
        for (int i = 0; i < filterSize; i++) {
            final String name = oin.readUTF();
            final String configurationClassName = oin.readUTF();
            try {
                final Class<JimixFilterConfiguration> configurationClass = (Class<JimixFilterConfiguration>) Class.forName(configurationClassName,
                        true, ManipulationPluginManager.getInstance().getClassLoader());
                final JimixFilterConfiguration configuration = configurationClass.newInstance();
                configuration.load(oin);
                filterVariantList.add(JimixFilterVariant.createCustom(name, configuration));
            } catch (InstantiationException | IllegalAccessException e) {
                LOGGER.warn("Unable to find filter configuration " + configurationClassName + ", skip", e);
            }
        }
    }
    //</editor-fold>

    //<editor-fold desc="Save">
    private static void save() {
        try (final OutputStream out = new FileOutputStream(JimixConstants.DEFAULT_MANIPULATION_VARIANT_FILE)) {
            out.write(KEY_VARIANT.getBytes(Charsets.US_ASCII));

            try (final GZIPOutputStream zipOut = new GZIPOutputStream(out)) {
                try (final ObjectOutputStream oout = new ObjectOutputStream(zipOut)) {
                    saveFilter(oout);
                    saveEffect(oout);
                }
            }
        } catch (IOException e) {
            LOGGER.warn("Unable to save variant file " + JimixConstants.DEFAULT_MANIPULATION_VARIANT_FILE.getAbsolutePath(), e);
        }
    }

    private static void saveEffect(ObjectOutputStream oout) throws IOException {
        //Filter builtin out
        final List<JimixEffectVariant> collect = effectVariantList.stream()
                .filter(item -> !item.isBuiltin())
                .collect(Collectors.toList());

        oout.writeUTF(KEY_EFFECT);
        oout.writeInt(collect.size());
        for (final JimixEffectVariant variant : collect) {
            oout.writeUTF(variant.getName());
            oout.writeUTF(variant.getConfiguration().getClass().getName());
            variant.getConfiguration().save(oout);
        }
    }

    private static void saveFilter(ObjectOutputStream oout) throws IOException {
        //Filter builtin out
        final List<JimixFilterVariant> collect = filterVariantList.stream()
                .filter(item -> !item.isBuiltin())
                .collect(Collectors.toList());

        oout.writeUTF(KEY_FILTER);
        oout.writeInt(collect.size());
        for (final JimixFilterVariant variant : collect) {
            oout.writeUTF(variant.getName());
            oout.writeUTF(variant.getConfiguration().getClass().getName());
            variant.getConfiguration().save(oout);
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
