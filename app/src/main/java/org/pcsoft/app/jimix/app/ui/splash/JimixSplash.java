package org.pcsoft.app.jimix.app.ui.splash;

import org.pcsoft.app.jimix.app.language.LanguageResources;
import org.pcsoft.framework.jfex.ui.splash.FXSplash;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;

public class JimixSplash {
    public static FXSplash.SplashHolder show(final boolean interminable) {
        try {
            return FXSplash.show(interminable, ImageIO.read(JimixSplash.class.getResourceAsStream("/image/img_splash.png")), LanguageResources.getText("app.title"),
                    null, (gc, bounds) -> {
                        gc.setFont(new Font("Arial", Font.BOLD, 12));
                        gc.setPaint(Color.WHITE);
                        gc.drawString("Version " + LanguageResources.getText("app.version"), 15, bounds.height - 30);
                        gc.setFont(new Font("Arial", Font.BOLD, 10));
                        gc.drawString("Date " + LanguageResources.getText("app.timestamp"), 15, bounds.height - 15);
                    });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
