package org.pcsoft.app.jimix.app.ui.dialog;

import de.saxsys.mvvmfx.FluentViewLoader;
import de.saxsys.mvvmfx.ViewTuple;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.paint.Paint;
import javafx.stage.StageStyle;
import javafx.stage.Window;
import org.pcsoft.app.jimix.app.language.LanguageResources;
import org.pcsoft.app.jimix.plugin.manipulation.manager.type.JimixBlenderInstance;
import org.pcsoft.framework.jfex.ui.component.HeaderPane;

public class LayerCreateSimpleDialog extends Dialog<LayerCreateSimpleDialog.Result> {
    public static final class Result {
        private final String name;
        private final Paint paint;
        private final JimixBlenderInstance blender;

        private Result(String name, Paint paint, JimixBlenderInstance blender) {
            this.name = name;
            this.paint = paint;
            this.blender = blender;
        }

        public Paint getPaint() {
            return paint;
        }

        public String getName() {
            return name;
        }

        public JimixBlenderInstance getBlender() {
            return blender;
        }
    }

    private final LayerCreateSimpleDialogViewModel viewModel;
    private final LayerCreateSimpleDialogView controller;

    public LayerCreateSimpleDialog(final Window owner) {
        initOwner(owner);
        initStyle(StageStyle.UTILITY);
        setTitle("Create Simple Layer");
        setResizable(false);
        getDialogPane().setHeader(new HeaderPane("Create Simple Layer", "Create s simple layer and add it to project tree."));
        getDialogPane().getButtonTypes().setAll(ButtonType.OK, ButtonType.CANCEL);

        final ViewTuple<LayerCreateSimpleDialogView, LayerCreateSimpleDialogViewModel> viewTuple =
                FluentViewLoader.fxmlView(LayerCreateSimpleDialogView.class).resourceBundle(LanguageResources.getBundle()).load();
        viewModel = viewTuple.getViewModel();
        controller = viewTuple.getCodeBehind();

        final Button btnOk = (Button) getDialogPane().lookupButton(ButtonType.OK);
        btnOk.disableProperty().bind(viewModel.allowOKProperty().not());
        getDialogPane().setContent(viewTuple.getView());
        setResultConverter(param -> {
            if (param != ButtonType.OK)
                return null;

            return new Result(viewModel.getName(), viewModel.getPaint(), viewModel.getBlender().createInstance());
        });
    }
}
