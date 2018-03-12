package org.pcsoft.app.jimix.plugin.system.manager.type;

import javafx.scene.input.Clipboard;
import org.pcsoft.app.jimix.plugin.common.manager.type.JimixInstance;
import org.pcsoft.app.jimix.plugin.common.api.type.JimixPlugin2DElement;

public final class JimixClipboardProviderInstance implements JimixInstance<JimixClipboardProviderPlugin, JimixClipboardProviderInstance> {
    private final JimixClipboardProviderPlugin plugin;

    JimixClipboardProviderInstance(JimixClipboardProviderPlugin plugin) {
        this.plugin = plugin;
    }

    public boolean acceptClipboardContent(Clipboard clipboard) {
        return plugin.acceptClipboardContent(clipboard);
    }

    public JimixPlugin2DElement createElementFromClipboard(Clipboard clipboard) {
        return plugin.createElementFromClipboard(clipboard);
    }

    @Override
    public JimixClipboardProviderPlugin getPlugin() {
        return plugin;
    }

    @Override
    public JimixClipboardProviderInstance copy() {
        return new JimixClipboardProviderInstance(plugin);
    }
}
