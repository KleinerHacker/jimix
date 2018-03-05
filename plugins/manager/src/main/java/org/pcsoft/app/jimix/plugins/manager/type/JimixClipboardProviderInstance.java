package org.pcsoft.app.jimix.plugins.manager.type;

import javafx.scene.input.Clipboard;
import org.pcsoft.app.jimix.plugins.api.type.JimixPluginElement;

public final class JimixClipboardProviderInstance implements JimixInstance<JimixClipboardProviderPlugin> {
    private final JimixClipboardProviderPlugin plugin;

    JimixClipboardProviderInstance(JimixClipboardProviderPlugin plugin) {
        this.plugin = plugin;
    }

    public boolean acceptClipboardContent(Clipboard clipboard) {
        return plugin.acceptClipboardContent(clipboard);
    }

    public JimixPluginElement createElementFromClipboard(Clipboard clipboard) {
        return plugin.createElementFromClipboard(clipboard);
    }

    @Override
    public JimixClipboardProviderPlugin getPlugin() {
        return plugin;
    }
}
