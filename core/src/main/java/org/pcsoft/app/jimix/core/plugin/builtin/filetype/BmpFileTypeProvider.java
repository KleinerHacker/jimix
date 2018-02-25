package org.pcsoft.app.jimix.core.plugin.builtin.filetype;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import org.pcsoft.app.jimix.plugins.api.JimixFileTypeProvider;
import org.pcsoft.app.jimix.plugins.api.annotation.JimixFileTypeProviderDescriptor;

import javax.imageio.ImageIO;
import javax.xml.bind.annotation.adapters.HexBinaryAdapter;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

@JimixFileTypeProviderDescriptor(description = "plugin.file_type.extension.bmp", resourceBundle = "builtin.language.plugin",
        extensions = {"*.bmp"}, magicBytesCount = 2)
public class BmpFileTypeProvider implements JimixFileTypeProvider {
    @Override
    public boolean acceptFile(byte[] magicBytes) {
        return Arrays.equals(magicBytes, new HexBinaryAdapter().unmarshal("424D"));
    }

    @Override
    public void save(Image image, File file) throws IOException {
        final BufferedImage bufferedImage = SwingFXUtils.fromFXImage(image, null);
        ImageIO.write(bufferedImage, "bmp", file);
    }

    @Override
    public Image load(File file) throws IOException {
        final BufferedImage bufferedImage = ImageIO.read(file);
        return SwingFXUtils.toFXImage(bufferedImage, null);
    }
}
