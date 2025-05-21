package io.sleigh.fleet.views;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.component.button.Button;
import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;

import io.sleigh.fleet.views.models.child;

@Route("parent-dashboard")
@PageTitle("Parent Dashboard")
public class ParentDashboardView extends VerticalLayout {

    private final Grid<child> childGrid = new Grid<>(child.class);
    private final TextField searchField = new TextField("Search Children");

    public ParentDashboardView() {
        setSizeFull();
        addClassName("parent-dashboard");

        HorizontalLayout layout = new HorizontalLayout();
        layout.setSizeFull();

        // Drawer (left sidebar)
        VerticalLayout drawer = createDrawer();
        drawer.setWidth("220px");
        drawer.getStyle().set("background-color", "#f5f5f5");
        drawer.setPadding(true);

        // Main content (search + grid)
        VerticalLayout content = new VerticalLayout();
        content.setSizeFull();
        content.setPadding(true);
        configureSearchBar();
        configureGrid();
        content.add(createSearchBar(), childGrid);

        layout.add(drawer, content);
        add(layout);
    }

    private VerticalLayout createDrawer() {
        VerticalLayout drawer = new VerticalLayout();
        drawer.addClassName("custom-drawer");

        Button kids = new Button("Dashboard", e -> Notification.show("Already here"));
        Button settings = new Button("Dashboard", e -> Notification.show("Already here"));
        kids.setWidthFull();
        kids.getStyle().set("margin-top","20px");
        settings.setWidthFull();
        // Container for top items
        VerticalLayout topItems = new VerticalLayout(
                new Div(new Span("👤 Parent Name")),
                kids,
                settings
        );
        topItems.setPadding(false);
        topItems.setSpacing(true);

// Spacer div that grows to fill space between topItems and logout
        Div spacer = new Div();
        spacer.getStyle().set("flex-grow", "1");

// Logout button at the bottom
        Button logout = new Button("Logout", e -> UI.getCurrent().navigate("logout"));
        logout.setWidthFull();

        drawer.add(topItems, spacer, logout);

        return drawer;
    }

    private void configureSearchBar() {
        searchField.setPlaceholder("Enter child name");
        searchField.setClearButtonVisible(true);
        searchField.setWidth("300px");

        // Add listener to filter the grid
        searchField.addValueChangeListener(event -> {
            String filterText = event.getValue().trim();
            if (filterText.isEmpty()) {
                childGrid.setItems(loadChildren());
            } else {
                childGrid.setItems(
                        loadChildren().stream()
                                .filter(child -> child.getName().toLowerCase().contains(filterText.toLowerCase()))
                                .toList()
                );
            }
        });
    }

    private final Button newChildButton = new Button("Inscript New Child");
    private HorizontalLayout createSearchBar() {
        newChildButton.addClickListener(e -> {
            Notification.show("New Child button clicked");
            // You can navigate to a form or open a dialog here for child inscription
            // UI.getCurrent().navigate("child-inscription");
        });

        HorizontalLayout layout = new HorizontalLayout(searchField, newChildButton);
        layout.setWidthFull();
        layout.setJustifyContentMode(JustifyContentMode.START);
        layout.setAlignItems(Alignment.END);
        layout.setSpacing(true); // add spacing between search field and button
        return layout;
    }

    private void configureGrid() {
        childGrid.setColumns("name", "age", "classroom");
        childGrid.setItems(loadChildren());

        childGrid.addItemClickListener(event -> {
            child child = event.getItem();
            Notification.show("Selected: " + child.getName());
            // Or navigate to child's detailed view if needed
            // UI.getCurrent().navigate("child-details/" + child.getId());
        });
    }

    private List<child> loadChildren() {
        return List.of(
                new child(1, "Ahmed", 10, "5A"),
                new child(2, "Sara", 8, "3B"),
                new child(3, "Yasmine", 12, "6C")
        );
    }
}
