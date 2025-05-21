package io.sleigh.fleet.views.admin;

import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route(value = "user-management", layout = MainLayoutAdmin.class)
public class UserManagementView extends VerticalLayout {
    public UserManagementView() {
        add(new H2("User Management"));
    }
}
