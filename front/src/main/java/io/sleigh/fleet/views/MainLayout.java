package io.sleigh.fleet.views;

import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.RouterLink;

import java.util.ArrayList;
import java.util.List;

public class MainLayout extends AppLayout {

    private final List<Div> linkContainers = new ArrayList<>();

    public MainLayout() {
        H1 logo = new H1("Crèche Admin");
        logo.getStyle().set("font-size", "24px").set("margin", "20px");
        addToNavbar(logo);

        VerticalLayout drawerLayout = new VerticalLayout();
        drawerLayout.setPadding(true);
        drawerLayout.setSpacing(false);

        drawerLayout.add(new Span("Navigation"));

        drawerLayout.add(
                createLinkContainer("Children Management", ChildrenManagementView.class),
                createLinkContainer("User Management", UserManagementView.class),
                createLinkContainer("Daily Tracking", DailyTrackingView.class),
                createLinkContainer("Meals & Health", MealsAndHealthView.class),
                createLinkContainer("Communication", CommunicationView.class),
                createLinkContainer("Finance & HR", FinanceHRView.class)
        );

        addToDrawer(drawerLayout);
    }

    private Div createLinkContainer(String text, Class<? extends com.vaadin.flow.component.Component> navigationTarget) {
        RouterLink link = new RouterLink(text, navigationTarget);

        // Style the RouterLink itself
        link.getStyle()
                .set("display", "block")
                .set("width", "100%")             // full width
                .set("box-sizing", "border-box") // include padding in width/height
                .set("color", "#333")
                .set("font-weight", "500")
                .set("text-decoration", "none")
                .set("cursor", "pointer")
                .set("padding", "12px 16px")
                .set("border-radius", "6px")
                .set("transition", "color 0.3s ease");

        // Container wraps the RouterLink
        Div container = new Div(link);
        container.getStyle()
                .set("width", "100%")             // fill full width of drawer
                .set("box-sizing", "border-box") // include padding/border in sizing
                .set("border-radius", "6px")
                .set("transition", "background-color 0.3s ease")
                .set("cursor", "pointer")
                .set("margin-bottom", "4px");     // spacing between links

        // Hover effect on container
        container.getElement().executeJs(
                "this.addEventListener('mouseover', e => this.style.backgroundColor = '#e0e0e0');" +
                        "this.addEventListener('mouseout', e => this.style.backgroundColor = " +
                        "(this.classList.contains('selected') ? '#c0c0c0' : 'transparent'))");

        // Click listener to update selection
        container.addClickListener(event -> selectContainer(container));

        // Save container to list for selection management
        linkContainers.add(container);

        return container;
    }

    private void selectContainer(Div selected) {
        for (Div container : linkContainers) {
            if (container.equals(selected)) {
                container.getStyle().set("background-color", "#c0c0c0");  // selected background
                // Change text color inside RouterLink to indicate selection
                container.getChildren().findFirst().ifPresent(component ->
                        component.getElement().getStyle().set("color", "#0078d7").set("font-weight", "700"));
                container.addClassName("selected");
            } else {
                container.getStyle().set("background-color", "transparent");
                container.getChildren().findFirst().ifPresent(component ->
                        component.getElement().getStyle().set("color", "#333").set("font-weight", "500"));
                container.removeClassName("selected");
            }
        }
    }
}
