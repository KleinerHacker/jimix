package org.pcsoft.app.jimix.app;

import de.saxsys.mvvmfx.cdi.MvvmfxCdiApplication;
import javafx.stage.Stage;
import org.apache.commons.lang.SystemUtils;
import org.pcsoft.app.jimix.app.ui.window.MainWindow;
import org.pcsoft.app.jimix.commons.JimixConstants;
import org.pcsoft.app.jimix.core.plugin.PluginManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class App extends MvvmfxCdiApplication {
    private static final Logger LOGGER = LoggerFactory.getLogger(App.class);

    public static void main(String[] args) {
        LOGGER.info("Startup Jimix");
        LOGGER.info(">>> Java Version: " + SystemUtils.JAVA_VERSION);
        LOGGER.info(">>> Java Home   : " + SystemUtils.JAVA_HOME);
        LOGGER.info(">>> Java Opts   : " + ManagementFactory.getRuntimeMXBean().getInputArguments());
        LOGGER.info(">>> Detected OS : " + SystemUtils.OS_NAME + " (Version: " + SystemUtils.OS_VERSION + ")");

        LOGGER.info("Load plugins...");
        try {
            final List<File> pluginPathList = Stream.of(args)
                    .filter(arg -> arg.toLowerCase().startsWith("--pluginpath="))
                    .map(arg -> arg.substring(13))
                    .map(File::new)
                    .collect(Collectors.toList());
            pluginPathList.add(JimixConstants.DEFAULT_PLUGIN_PATH);
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
        new MainWindow().show();
    }
}
