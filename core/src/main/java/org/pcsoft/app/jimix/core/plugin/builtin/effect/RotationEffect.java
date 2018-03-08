package org.pcsoft.app.jimix.core.plugin.builtin.effect;

import javafx.geometry.Point3D;
import javafx.scene.Node;
import javafx.scene.transform.Rotate;
import org.pcsoft.app.jimix.plugin.manipulation.api.Jimix3DEffect;
import org.pcsoft.app.jimix.plugin.manipulation.api.annotation.JimixEffectDescriptor;
import org.pcsoft.app.jimix.plugin.manipulation.api.type.JimixEffectVariant;

@JimixEffectDescriptor(name = "3D Rotation", description = "Free 3D rotation", group = "3D",
        configurationClass = RotationEffectConfiguration.class)
public class RotationEffect implements Jimix3DEffect<RotationEffectConfiguration> {
    @Override
    public Node apply(Node node, int x, int y, int width, int height, RotationEffectConfiguration configuration) throws Exception {
        node.getTransforms().add(new Rotate(configuration.getRotateX(), new Point3D(1, 0, 0)));
        node.getTransforms().add(new Rotate(configuration.getRotateY(), new Point3D(0, 1, 0)));
        node.getTransforms().add(new Rotate(configuration.getRotateZ(), new Point3D(0, 0, 1)));

        return node;
    }

    @SuppressWarnings("unchecked")
    @Override
    public JimixEffectVariant<RotationEffectConfiguration>[] getVariants() {
        return new JimixEffectVariant[]{
                JimixEffectVariant.createBuiltin(
                        "Default",
                        new RotationEffectConfiguration()
                )
        };
    }
}
