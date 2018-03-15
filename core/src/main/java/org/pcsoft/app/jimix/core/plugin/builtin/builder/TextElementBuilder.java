package org.pcsoft.app.jimix.core.plugin.builtin.builder;

import javafx.scene.Node;
import org.pcsoft.app.jimix.core.plugin.builtin.model.TextPluginElement;
import org.pcsoft.app.jimix.core.util.TextPluginElementUtils;
import org.pcsoft.app.jimix.plugin.manipulation.api.Jimix2DElementBuilder;
import org.pcsoft.app.jimix.plugin.manipulation.api.annotation.JimixElementBuilderDescriptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@JimixElementBuilderDescriptor(name = "Text", description = "Add a text element", group = "2D", iconPath = "/builtin/icons/ic_element_text16.png",
        elementModelClass = TextPluginElement.class, manualAddable = true)
public class TextElementBuilder implements Jimix2DElementBuilder<TextPluginElement> {
    private static final Logger LOGGER = LoggerFactory.getLogger(TextElementBuilder.class);

    @Override
    public Node buildNode(TextPluginElement elementModel, final int x, final int y) {
        return TextPluginElementUtils.buildShape(x, y, elementModel);
    }
}
