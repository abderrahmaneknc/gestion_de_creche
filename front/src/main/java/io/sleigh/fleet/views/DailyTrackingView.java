package io.sleigh.fleet.views;

import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import io.sleigh.fleet.views.MainLayout;
@Route(value = "daily-tracking", layout = MainLayout.class)
public class DailyTrackingView extends VerticalLayout {
    public DailyTrackingView() {
        add(new H2("Daily Tracking"));
    }
}
