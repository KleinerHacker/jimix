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
import org.pcsoft.app.jimix.plugin.manipulation.manager.type.Jimix2DEffectInstance;
import org.pcsoft.framework.jfex.ui.component.HeaderPane;

public class EffectManagerDialog extends Dialog<EffectManagerDialog.Result> {
    public static final class Result {
        private final Jimix2DEffectInstance instance;

        private Result(Jimix2DEffectInstance instance) {
            this.instance = instance;
        }

        public Jimix2DEffectInstance getInstance() {
            return instance;
        }
    }

    private final EffectManagerDialogView controller;
    private final EffectManagerDialogViewModel viewModel;

    public EffectManagerDialog(final Window owner, final Image image) {
        initOwner(owner);
        initStyle(StageStyle.UTILITY);
        setTitle(LanguageResources.getText("view.dialog.manager.effect.title"));
        setResizable(false);
        getDialogPane().setHeader(new HeaderPane(LanguageResources.getText("view.dialog.manager.effect.title"),
                LanguageResources.getText("view.dialog.manager.effect.descriptor"),
                new Image(getClass().getResourceAsStream("/icons/ic_effect48.png"))));
        getDialogPane().getButtonTypes().setAll(ButtonType.OK, ButtonType.CANCEL);
        //Commit style from owner
        getDialogPane().getStylesheets().addAll(owner.getScene().getRoot().getStylesheets());

        final ViewTuple<EffectManagerDialogView, EffectManagerDialogViewModel> viewTuple =
                FluentViewLoader.fxmlView(EffectManagerDialogView.class).resourceBundle(LanguageResources.getBundle()).load();
        controller = viewTuple.getCodeBehind();
        viewModel = viewTuple.getViewModel();

        viewModel.setSourceImage(image);
        final Button btnOk = (Button) getDialogPane().lookupButton(ButtonType.OK);
        btnOk.disableProperty().bind(viewModel.allowSubmitProperty().not());

        getDialogPane().setContent(viewTuple.getView());

        setResultConverter(param -> {
            if (param != ButtonType.OK)
                return null;

            return new Result(viewModel.getSelectedEffect());
        });
    }
}
