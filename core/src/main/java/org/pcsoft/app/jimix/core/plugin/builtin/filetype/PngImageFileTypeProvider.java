package org.pcsoft.app.jimix.core.plugin.builtin.filetype;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import org.pcsoft.app.jimix.plugin.system.api.JimixImageFileTypeProvider;
import org.pcsoft.app.jimix.plugin.system.api.annotation.JimixFileTypeProviderDescriptor;

import javax.imageio.ImageIO;
import javax.xml.bind.annotation.adapters.HexBinaryAdapter;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

@JimixFileTypeProviderDescriptor(description = "plugin.file_type.extension.png", resourceBundle = "builtin.language.plugin",
        extensions = {"*.png"}, magicBytesCount = 8)
public class PngImageFileTypeProvider implements JimixImageFileTypeProvider {
    @Override
    public boolean acceptFile(byte[] magicBytes) {
        return Arrays.equals(magicBytes, new HexBinaryAdapter().unmarshal("89504E470D0A1A0A"));
    }

    @Override
    public void save(Image image, File file) throws IOException {
        final BufferedImage bufferedImage = SwingFXUtils.fromFXImage(image, null);
        ImageIO.write(bufferedImage, "png", file);
    }

    @Override
    public Image load(File file) throws IOException {
        final BufferedImage bufferedImage = ImageIO.read(file);
        return SwingFXUtils.toFXImage(bufferedImage, null);
    }
}
