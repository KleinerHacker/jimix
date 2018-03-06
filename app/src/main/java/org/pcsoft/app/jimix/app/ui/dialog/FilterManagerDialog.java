package org.pcsoft.app.jimix.app.ui.dialog;

import de.saxsys.mvvmfx.FluentViewLoader;
import de.saxsys.mvvmfx.ViewTuple;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.image.Image;
import javafx.stage.StageStyle;
import javafx.stage.Window;
import org.pcsoft.app.jimix.app.language.LanguageResources;
import org.pcsoft.app.jimix.plugin.mani.manager.type.JimixFilterInstance;
import org.pcsoft.framework.jfex.component.HeaderPane;

public class FilterManagerDialog extends Dialog<FilterManagerDialog.Result> {
    public static final class Result {
        private final JimixFilterInstance instance;

        private Result(JimixFilterInstance instance) {
            this.instance = instance;
        }

        public JimixFilterInstance getInstance() {
            return instance;
        }
    }

    private final FilterManagerDialogView controller;
    private final FilterManagerDialogViewModel viewModel;

    public FilterManagerDialog(final Window owner, final Image image) {
        initOwner(owner);
        initStyle(StageStyle.UTILITY);
        setTitle(LanguageResources.getText("view.dialog.manager.filter.title"));
        setResizable(false);
        getDialogPane().setHeader(new HeaderPane(LanguageResources.getText("view.dialog.manager.filter.title"),
                LanguageResources.getText("view.dialog.manager.filter.descriptor"),
                new Image(getClass().getResourceAsStream("/icons/ic_filter48.png"))));
        getDialogPane().getButtonTypes().setAll(ButtonType.OK, ButtonType.CANCEL);
        //Commit style from owner
        getDialogPane().getStylesheets().addAll(owner.getScene().getRoot().getStylesheets());

        final ViewTuple<FilterManagerDialogView, FilterManagerDialogViewModel> viewTuple =
                FluentViewLoader.fxmlView(FilterManagerDialogView.class).resourceBundle(LanguageResources.getBundle()).load();
        controller = viewTuple.getCodeBehind();
        viewModel = viewTuple.getViewModel();

        viewModel.setSourceImage(image);
        final Button btnOk = (Button) getDialogPane().lookupButton(ButtonType.OK);
        btnOk.disableProperty().bind(viewModel.allowSubmitProperty().not());

        getDialogPane().setContent(viewTuple.getView());

        setResultConverter(param -> {
            if (param != ButtonType.OK)
                return null;

            return new Result(viewModel.getSelectedFilter());
        });
    }
}
