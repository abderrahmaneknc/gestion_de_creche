package io.sleigh.fleet.views;

import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import io.sleigh.fleet.views.MainLayout;

@Route(value = "finance-hr", layout = MainLayout.class)
public class FinanceHRView extends VerticalLayout {
    public FinanceHRView() {
        add(new H2("Finance & HR"));
    }
}
