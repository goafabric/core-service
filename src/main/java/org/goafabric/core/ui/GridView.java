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
import org.goafabric.core.organization.controller.dto.types.PermissionType;
import org.goafabric.core.ui.adapter.SearchAdapter;
import org.goafabric.core.ui.extension.UserHolder;
import org.springframework.util.StringUtils;

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
            grid.getSelectedItems().forEach(this::showSaveDialog);
            updateList();
        });

        var addButton = new Button(new Icon(VaadinIcon.FILE_ADD));
        addButton.addClickListener(event -> {
            var item = logic.search("").get(0);
            showSaveDialog(item, true);
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

        addDoubleClick();
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

    private void addDoubleClick() {
        grid.addItemDoubleClickListener(event -> {
            T item = event.getItem();
            showSaveDialog(item);
        });
    }

    private void showSaveDialog(T item) {
        showSaveDialog(item, false);
    }

    private void showSaveDialog(T item, boolean isNewItem) {
        this.isNewItem = isNewItem;
        configureSaveDialog(item);
        if (!mapDialogText.isEmpty()) {
            createDialog(item);
        }
    }

    private void createDialog(T item) {
        var dialog = new Dialog();
        var layout = new VerticalLayout();
        dialog.add(layout);
        mapDialogText.values().forEach(textField -> {
                //textField.setRequired(true);
                layout.add(textField);
            }
        );
        mapDialogCombo.values().forEach(layout::add);
        addButtons(item, layout, dialog);
        dialog.open();
    }

    private void addButtons(T item, VerticalLayout layout, Dialog dialog) {
        var saveButton = new Button("OK");
        saveButton.addClickListener(event -> {
            if (mapDialogText.values().stream().anyMatch(
                    textField -> textField.isRequired() && !StringUtils.hasText(textField.getValue())) ) {
                Notification.show("Please enter required fields");
            } else {
                onSave(item);
                dialog.close();
                updateList();
            }
        });

        var cancelButton = new Button("Cancel");
        cancelButton.addClickListener(event -> {
            dialog.close();
            //updateList();
        });

        if (isNewItem) {
            mapDialogText.values().forEach(textField -> textField.setValue(""));
        }
        
        layout.add(new HorizontalLayout(saveButton, cancelButton));

    }

    protected void onSave(T item) {}

    protected void onDelete(T item) {}

    protected void configureSaveDialog(T item) {}


    protected final HashMap<String, TextField> mapDialogText = new HashMap();
    protected final HashMap<String, ComboBoxBase> mapDialogCombo = new HashMap();

    protected void put(TextField textField) {
        mapDialogText.put(textField.getLabel(), textField);
    }

    protected void put(ComboBoxBase comboBox) {
        mapDialogCombo.put(comboBox.getLabel(), comboBox);
    }

    protected boolean isNewItem() {
        return isNewItem;
    }
}
