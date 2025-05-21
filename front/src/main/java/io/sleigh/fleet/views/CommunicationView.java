package io.sleigh.fleet.views;

import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import io.sleigh.fleet.views.MainLayout;
@Route(value = "communication", layout = MainLayout.class)
public class CommunicationView extends VerticalLayout {
    public CommunicationView() {
        add(new H2("Communication"));
    }
}
