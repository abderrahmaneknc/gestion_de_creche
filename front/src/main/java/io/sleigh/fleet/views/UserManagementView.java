package io.sleigh.fleet.views;

import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import io.sleigh.fleet.views.MainLayout;

@Route(value = "user-management", layout = MainLayout.class)
public class UserManagementView extends VerticalLayout {
    public UserManagementView() {
        add(new H2("User Management"));
    }
}
