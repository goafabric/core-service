package org.goafabric.core.ui;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBoxBase;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import org.goafabric.core.organization.controller.vo.types.PermissionType;
import org.goafabric.core.ui.adapter.SearchAdapter;
import org.goafabric.core.ui.extension.UserHolder;

import java.util.HashMap;

public abstract class GridView<T> extends VerticalLayout {
    private final Grid<T> grid;
    private final TextField filterText = new TextField("", "search ...");
    private final SearchAdapter<T> logic;
    private boolean isNewItem = false;

    public GridView(Grid<T> grid, SearchAdapter<T> logic) {
        this.grid = grid;
        this.logic = logic;
        createView();
        this.addAttachListener((ComponentEventListener<AttachEvent>) event -> updateList());
    }

    private void createView() {
        setSizeFull();

        addButtonBar();
        addGrid();
    }

    private void addButtonBar() {
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(e -> updateList());


        var editButton = new Button(new Icon(VaadinIcon.EDIT));
        editButton.addClickListener(event -> {
            isNewItem = false;
            grid.getSelectedItems().forEach(this::showSaveDialog);
            updateList();
        });

        var addButton = new Button(new Icon(VaadinIcon.FILE_ADD));
        addButton.addClickListener(event -> {
            isNewItem = true;
            grid.getSelectedItems().forEach(this::showSaveDialog);
            isNewItem = false;
            updateList();
        });

        var removeButton = new Button(new Icon(VaadinIcon.FILE_REMOVE));
        removeButton.addClickListener(event -> {
            grid.getSelectedItems().forEach(this::onDelete);
            updateList();
        });

        removeButton.setEnabled(UserHolder.userHasPermission(PermissionType.READ_WRITE_DELETE.getValue()));
        this.add(new HorizontalLayout(filterText, editButton, addButton, removeButton));
    }

    private void addGrid() {
        grid.setSizeFull();
        grid.setColumns();
        addColumns(grid);
        grid.getColumns().forEach(col -> col.setAutoWidth(true));
        this.add(grid);

        addSaveDialog();
    }



    protected abstract void addColumns(Grid<T> grid);

    protected void updateList() {
        long start = System.currentTimeMillis();
        grid.setItems(logic.search(filterText.getValue()));
        Notification.show("Search took " + (System.currentTimeMillis() -start) + " ms");
    }

    protected SearchAdapter<T> getAdapter() {
        return logic;
    }

    /**/

    private void addSaveDialog() {
        grid.addItemDoubleClickListener(event -> {
            T item = event.getItem();
            showSaveDialog(item);
        });
    }

    private void showSaveDialog(T item) {
        configureSaveDialog(item);
        if (!mapDialog.isEmpty()) {
            createDialog(item);
        }
    }

    private void createDialog(T item) {
        var dialog = new Dialog();
        var layout = new VerticalLayout();
        dialog.add(layout);
        mapDialog.values().forEach(layout::add);
        mapDialogCombo.values().forEach(layout::add);
        addButtons(item, layout, dialog);
        dialog.open();
    }

    private void addButtons(T item, VerticalLayout layout, Dialog dialog) {
        var saveButton = new Button("OK");
        saveButton.addClickListener(event -> {
            onSave(item);
            dialog.close();
            updateList();
        });

        var cancelButton = new Button("Cancel");
        cancelButton.addClickListener(event -> {
            dialog.close();
            //updateList();
        });

        /*
        var newButton = new Button(new Icon(VaadinIcon.FILE_ADD));
        newButton.addClickListener(event -> {
            isNewItem = true;
            mapDialog.values().forEach(textField -> textField.setValue(""));
        });

        var cancelButton = new Button(new Icon(VaadinIcon.CLOSE));
        cancelButton.addClickListener(event -> dialog.close());

         */

        layout.add(new HorizontalLayout(saveButton, cancelButton));

    }

    protected void onSave(T item) {}

    protected void onDelete(T item) {}

    protected void configureSaveDialog(T item) {}


    protected final HashMap<String, TextField> mapDialog = new HashMap();
    protected final HashMap<String, ComboBoxBase> mapDialogCombo = new HashMap();

    protected void put(TextField textField) {
        mapDialog.put(textField.getLabel(), textField);
    }

    protected void put(ComboBoxBase comboBox) {
        mapDialogCombo.put(comboBox.getLabel(), comboBox);
    }

    protected boolean isNewItem() {
        return isNewItem;
    }
}
