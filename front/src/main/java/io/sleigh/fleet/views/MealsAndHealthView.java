package io.sleigh.fleet.views;

import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import io.sleigh.fleet.views.MainLayout;

@Route(value = "meals-health", layout = MainLayout.class)
public class MealsAndHealthView extends VerticalLayout {
    public MealsAndHealthView() {
        add(new H2("Meals and Health"));
    }
}
