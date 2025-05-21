package io.sleigh.fleet.views;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;
import io.sleigh.fleet.views.MainLayout;

@Route(value = "admin-dashboard", layout = MainLayout.class)
public class AdminDashboard extends VerticalLayout implements BeforeEnterObserver {

    public AdminDashboard() {
        setSizeFull();
        setPadding(false);
        setSpacing(false);
        // No UI components added here: empty dashboard
    }

    // This method runs before entering this view
    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        // Redirect to first drawer element, e.g. Children Management
        event.rerouteTo("children-management");
    }
}
