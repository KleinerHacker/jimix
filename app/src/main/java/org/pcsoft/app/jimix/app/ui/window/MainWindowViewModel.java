package org.pcsoft.app.jimix.app.ui.window;

import de.saxsys.mvvmfx.ViewModel;
import javafx.beans.property.ReadOnlyListProperty;
import javafx.beans.property.ReadOnlyListWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.pcsoft.app.jimix.core.project.JimixProject;

public class MainWindowViewModel implements ViewModel {
    private final ReadOnlyListProperty<JimixProject> projectList =
            new ReadOnlyListWrapper<JimixProject>(FXCollections.observableArrayList()).getReadOnlyProperty();

    public ObservableList<JimixProject> getProjectList() {
        return projectList.get();
    }

    public ReadOnlyListProperty<JimixProject> projectListProperty() {
        return projectList;
    }
}
