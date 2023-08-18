package org.goafabric.core.ui.catalogs.tabs;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.router.PageTitle;
import org.goafabric.core.ui.GridView;
import org.goafabric.core.ui.SearchAdapter;
import org.goafabric.core.ui.adapter.vo.ChargeItem;

@PageTitle("ChargeItem")
public class ChargeItemView extends GridView<ChargeItem> {

    public ChargeItemView(SearchAdapter<ChargeItem> logic) {
        super(new Grid<>(ChargeItem.class), logic);
    }

    @Override
    protected void addColumns(Grid<ChargeItem> grid) {
        grid.addColumn(ChargeItem::code).setHeader("code");
        grid.addColumn(ChargeItem::display).setHeader("description");
        grid.addColumn(ChargeItem::price).setHeader("price");
    }
}
