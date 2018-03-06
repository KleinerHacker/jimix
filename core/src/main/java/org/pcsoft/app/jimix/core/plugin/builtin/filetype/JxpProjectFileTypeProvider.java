package org.pcsoft.app.jimix.core.plugin.builtin.filetype;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import org.apache.commons.codec.Charsets;
import org.pcsoft.app.jimix.commons.exception.JimixPluginException;
import org.pcsoft.app.jimix.core.plugin.builtin.blender.OverlayBlender;
import org.pcsoft.app.jimix.plugin.mani.manager.ManipulationPluginManager;
import org.pcsoft.app.jimix.plugin.mani.manager.type.JimixBlenderInstance;
import org.pcsoft.app.jimix.plugin.mani.manager.type.JimixBlenderPlugin;
import org.pcsoft.app.jimix.plugin.system.api.JimixProjectFileTypeProvider;
import org.pcsoft.app.jimix.plugin.system.api.annotation.JimixFileTypeProviderDescriptor;
import org.pcsoft.app.jimix.project.JimixLayerModel;
import org.pcsoft.app.jimix.project.JimixProjectModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.io.*;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

@JimixFileTypeProviderDescriptor(description = "Jimix Project File", extensions = {"*.jxp"}, magicBytesCount = 5)
public class JxpProjectFileTypeProvider implements JimixProjectFileTypeProvider {
    private static final Logger LOGGER = LoggerFactory.getLogger(JxpProjectFileTypeProvider.class);
    private static final String KEY_JIMIX = "JIMIX";

    @Override
    public void save(JimixProjectModel projectModel, File file) throws IOException {
        LOGGER.info("Save as JIMIX project");

        try (final OutputStream out = new FileOutputStream(file)) {
            out.write(KEY_JIMIX.getBytes(Charsets.US_ASCII));

            try (final GZIPOutputStream zipOut = new GZIPOutputStream(out)) {
                try (final ObjectOutputStream oout = new ObjectOutputStream(zipOut)) {
                    oout.writeInt(projectModel.getWidth());
                    oout.writeInt(projectModel.getHeight());

                    saveLayers(projectModel, oout);
                }
            }
        }
    }

    private void saveLayers(JimixProjectModel projectModel, ObjectOutputStream oout) throws IOException {
        oout.writeInt(projectModel.getLayerList().size());
        for (final JimixLayerModel layerModel : projectModel.getLayerList()) {
            oout.writeUTF(layerModel.getName());
            oout.writeDouble(layerModel.getOpacity());
            oout.writeBoolean(layerModel.isVisibility());
            ImageIO.write(SwingFXUtils.fromFXImage(layerModel.getMask(), null), "png", oout);
            //Blender
            oout.writeUTF(layerModel.getBlender().getPlugin().getIdentifier());
        }
    }

    @Override
    public JimixProjectModel load(File file) throws IOException {
        LOGGER.info("Load as JIMIX project");

        try (final InputStream in = new FileInputStream(file)) {
            final byte[] keyBuffer = new byte[KEY_JIMIX.length()];
            new DataInputStream(in).readFully(keyBuffer);
            if (!KEY_JIMIX.equals(new String(keyBuffer, Charsets.US_ASCII)))
                throw new IOException("Unable to find " + KEY_JIMIX + " key");

            try (final GZIPInputStream zipIn = new GZIPInputStream(in)) {
                try (final ObjectInputStream oin = new ObjectInputStream(zipIn)) {
                    final int width = oin.readInt();
                    final int height = oin.readInt();
                    final JimixProjectModel projectModel = new JimixProjectModel(width, height);

                    loadLayers(oin, projectModel);

                    return projectModel;
                }
            }
        }
    }

    private void loadLayers(ObjectInputStream oin, JimixProjectModel projectModel) throws IOException {
        final int layerSize = oin.readInt();
        for (int i = 0; i < layerSize; i++) {
            try {
                final String name = oin.readUTF();
                final double opacity = oin.readDouble();
                final boolean visibile = oin.readBoolean();
                final Image mask = SwingFXUtils.toFXImage(ImageIO.read(oin), null);
                final String blenderIdentifier = oin.readUTF();
                JimixBlenderPlugin blender = ManipulationPluginManager.getInstance().getBlender(blenderIdentifier);
                if (blender == null) {
                    LOGGER.warn("Unable to find blender " + blenderIdentifier + ", used default");
                    blender = new JimixBlenderPlugin(new OverlayBlender());
                }
                final JimixBlenderInstance blenderInstance = blender.createInstance();

                final JimixLayerModel layerModel = new JimixLayerModel(blenderInstance);
                layerModel.setName(name);
                layerModel.setOpacity(opacity);
                layerModel.setVisibility(visibile);
                layerModel.setMask(mask);

                projectModel.getLayerList().add(layerModel);
            } catch (JimixPluginException e) {
                LOGGER.warn("Unable to load layer, skip", e);
            }
        }
    }

    @Override
    public boolean acceptFile(byte[] magicBytes) {
        return KEY_JIMIX.equals(new String(magicBytes, Charsets.US_ASCII));
    }
}
