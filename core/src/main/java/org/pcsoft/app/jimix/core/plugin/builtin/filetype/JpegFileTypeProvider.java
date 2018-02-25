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

@JimixFileTypeProviderDescriptor(description = "plugin.file_type.extension.jpg", resourceBundle = "builtin.language.plugin",
        extensions = {"*.jpeg", "*.jpg"}, magicBytesCount = 4)
public class JpegFileTypeProvider implements JimixFileTypeProvider {
    @Override
    public boolean acceptFile(byte[] magicBytes) {
        return Arrays.equals(magicBytes, new HexBinaryAdapter().unmarshal("FFD8FFDB")) ||
                Arrays.equals(magicBytes, new HexBinaryAdapter().unmarshal("FFD8FFE0")) ||
                Arrays.equals(magicBytes, new HexBinaryAdapter().unmarshal("FFD8FFE1"));
    }

    @Override
    public void save(Image image, File file) throws IOException {
        final BufferedImage bufferedImage = SwingFXUtils.fromFXImage(image, null);
        ImageIO.write(bufferedImage, "jpg", file);
    }

    @Override
    public Image load(File file) throws IOException {
        final BufferedImage bufferedImage = ImageIO.read(file);
        return SwingFXUtils.toFXImage(bufferedImage, null);
    }
}
