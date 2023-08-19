package org.goafabric.core.ui;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import org.goafabric.core.ui.adapter.SearchAdapter;

import java.util.HashMap;

public abstract class GridView<T> extends VerticalLayout {
    private final Grid<T> grid;
    private final TextField filterText = new TextField("", "search ...");
    private final SearchAdapter<T> logic;

    public GridView(Grid<T> grid, SearchAdapter<T> logic) {
        this.grid = grid;
        this.logic = logic;
        createView();
        this.addAttachListener((ComponentEventListener<AttachEvent>) event -> updateList());
    }

    private void createView() {
        setSizeFull();

        addFilterText();
        addGrid();
    }

    private void addFilterText() {
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(e -> updateList());
        this.add(filterText);
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
            configureSaveDialog(item);
            if (!mapDialog.isEmpty()) {
                var dialog = new Dialog();
                var layout = new VerticalLayout();
                dialog.add(layout);
                mapDialog.values().forEach(layout::add);

                var saveButton = new Button("save");
                dialog.add(saveButton);

                saveButton.addClickListener(evt -> {
                    onSave(item);
                    dialog.close();
                    updateList();
                });
                dialog.open();
            }
        });

    }

    protected void configureSaveDialog(T item) {}

    protected void onSave(T item) {}

    protected final HashMap<String, TextField> mapDialog = new HashMap();

    protected void put(TextField textField) {
        mapDialog.put(textField.getLabel(), textField);
    }
}
