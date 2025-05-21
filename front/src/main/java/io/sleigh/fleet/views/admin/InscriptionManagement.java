package io.sleigh.fleet.views.admin;

import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route(value = "daily-tracking", layout = MainLayoutAdmin.class)
public class InscriptionManagement extends VerticalLayout {
    public InscriptionManagement() {
        add(new H2("Daily Tracking"));
    }
}
