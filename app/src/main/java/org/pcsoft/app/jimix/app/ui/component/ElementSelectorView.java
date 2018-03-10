package org.pcsoft.app.jimix.app.ui.component;

import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Orientation;
import javafx.scene.control.Label;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import org.pcsoft.app.jimix.plugin.manipulation.manager.type.Jimix2DElementBuilderPlugin;
import org.pcsoft.app.jimix.plugin.manipulation.manager.type.JimixElementBuilderPlugin;
import org.pcsoft.framework.jfex.property.ExtendedWrapperProperty;
import org.pcsoft.framework.jfex.ui.component.DetailTooltip;

import java.net.URL;
import java.util.ResourceBundle;

public class ElementSelectorView implements FxmlView<ElementSelectorViewModel>, Initializable {
    @FXML
    private VBox pnlRoot;

    @InjectViewModel
    private ElementSelectorViewModel viewModel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        refreshPane();
    }

    private void refreshPane() {
        pnlRoot.getChildren().clear();

        final ToggleGroup baseGroup = new ToggleGroup();
        for (final String groupName : viewModel.getBuilderMap().keySet()) {
            final Label label = new Label(groupName);
            label.setStyle("-fx-font-weight: bold");
            pnlRoot.getChildren().add(label);

            final FlowPane pane = new FlowPane(Orientation.HORIZONTAL);
            for (final JimixElementBuilderPlugin elementBuilderPlugin : viewModel.getBuilderMap().get(groupName)) {
                final ToggleButton button = new ToggleButton("", new ImageView(elementBuilderPlugin.getIcon()));
                button.setTooltip(new DetailTooltip(elementBuilderPlugin.getName(), elementBuilderPlugin.getDescription()));
                button.setToggleGroup(baseGroup);
                button.setUserData(elementBuilderPlugin);

                pane.getChildren().add(button);
            }
            pnlRoot.getChildren().add(pane);
        }

        viewModel.selectedElementBuilderProperty().bindBidirectional(
                new ExtendedWrapperProperty<JimixElementBuilderPlugin>(baseGroup.selectedToggleProperty()) {
                    @Override
                    protected JimixElementBuilderPlugin getPseudoValue() {
                        return baseGroup.getSelectedToggle() == null ? null : (JimixElementBuilderPlugin) baseGroup.getSelectedToggle().getUserData();
                    }

                    @Override
                    protected void setPseudoValue(JimixElementBuilderPlugin value) {
                        final Toggle foundToggle = baseGroup.getToggles().stream()
                                .filter(toggle -> toggle.getUserData().equals(value))
                                .findFirst().orElse(null);

                        if (foundToggle == null) {
                            baseGroup.selectToggle(null);
                        } else {
                            baseGroup.selectToggle(foundToggle);
                        }
                    }
                }
        );
    }
}
