package org.pcsoft.app.jimix.core.plugin.type;

import javafx.scene.input.Clipboard;
import org.pcsoft.app.jimix.plugins.api.model.JimixElementModel;

public final class JimixClipboardProviderInstance implements JimixInstance<JimixClipboardProviderPlugin> {
    private final JimixClipboardProviderPlugin plugin;

    JimixClipboardProviderInstance(JimixClipboardProviderPlugin plugin) {
        this.plugin = plugin;
    }

    public boolean acceptClipboardContent(Clipboard clipboard) {
        return plugin.acceptClipboardContent(clipboard);
    }

    /**
     * <b>Use always with {@link org.pcsoft.app.jimix.core.project.ProjectManager#createElementFromClipboardForLayer(org.pcsoft.app.jimix.core.project.JimixLayer, JimixClipboardProviderPlugin)}</b>
     * @param clipboard
     * @return
     */
    public JimixElementModel createElementFromClipboard(Clipboard clipboard) {
        return plugin.createElementFromClipboard(clipboard);
    }

    @Override
    public JimixClipboardProviderPlugin getPlugin() {
        return plugin;
    }
}
