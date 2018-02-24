package org.pcsoft.app.jimix.app;

import de.saxsys.mvvmfx.cdi.MvvmfxCdiApplication;
import javafx.stage.Stage;
import org.apache.commons.lang.SystemUtils;
import org.pcsoft.app.jimix.app.ui.splash.JimixSplash;
import org.pcsoft.app.jimix.app.ui.window.MainWindow;
import org.pcsoft.app.jimix.commons.JimixConstants;
import org.pcsoft.app.jimix.core.plugin.PluginManager;
import org.pcsoft.framework.jfex.splash.FXSplash;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.util.ArrayList;
import java.util.List;

public class App extends MvvmfxCdiApplication {
    private static final Logger LOGGER = LoggerFactory.getLogger(App.class);
    private static File fileToOpen = null;
    private static FXSplash.SplashHolder splashHolder = null;

    public static void main(String[] args) {
        LOGGER.info("Startup Jimix");
        LOGGER.info(">>> Java Version: " + SystemUtils.JAVA_VERSION);
        LOGGER.info(">>> Java Home   : " + SystemUtils.JAVA_HOME);
        LOGGER.info(">>> Java Opts   : " + ManagementFactory.getRuntimeMXBean().getInputArguments());
        LOGGER.info(">>> Detected OS : " + SystemUtils.OS_NAME + " (Version: " + SystemUtils.OS_VERSION + ")");

        splashHolder = JimixSplash.show(true);

        //Detect args
        final List<File> pluginPathList = new ArrayList<>();
        for (final String arg : args) {
            if (arg.toLowerCase().startsWith("--pluginpath=")) {
                pluginPathList.add(new File(arg.substring(13)));
            } else {
                fileToOpen = new File(arg);
            }
        }
        pluginPathList.add(JimixConstants.DEFAULT_PLUGIN_PATH);

        LOGGER.info("Load plugins...");
        try {
            PluginManager.getInstance().init(pluginPathList);
        } catch (IOException e) {
            LOGGER.error("FATAL: Unable to load any plugin, app is closed immediately", e);
            System.exit(-1);
            return;
        }

        launch(args);
    }

    @Override
    public void startMvvmfx(Stage stage) throws Exception {
        splashHolder.dismiss();

        final MainWindow mainWindow = new MainWindow(fileToOpen);
        mainWindow.show();
    }
}
