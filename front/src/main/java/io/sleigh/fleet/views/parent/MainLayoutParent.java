package io.sleigh.fleet.views.parent;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.RouterLink;
import io.sleigh.fleet.views.parent.MykidsView;

public class MainLayoutParent extends AppLayout {

    public MainLayoutParent() {
        H1 logo = new H1("Crèche Parent");
        logo.getStyle().set("font-size", "24px").set("margin", "20px");
        addToNavbar(logo);

        VerticalLayout drawerLayout = new VerticalLayout();
        drawerLayout.setPadding(true);
        drawerLayout.setSpacing(false);
        drawerLayout.setSizeFull(); // important to allow spacing with flex-grow

        // Top section
        VerticalLayout topSection = new VerticalLayout();
        topSection.setPadding(false);
        topSection.setSpacing(true);

        topSection.add(new Span("Navigation"));
        topSection.add(createLinkContainer("My Kids Management", MykidsView.class));

        // Spacer to push logout button to the bottom
        Div spacer = new Div();
        spacer.getStyle().set("flex-grow", "1");

        // Sign out button
        Button signOut = new Button("Sign Out", e -> UI.getCurrent().navigate("logout"));
        signOut.setWidthFull();

        drawerLayout.add(topSection, spacer, signOut);
        addToDrawer(drawerLayout);
    }

    private Div createLinkContainer(String text, Class<? extends com.vaadin.flow.component.Component> navigationTarget) {
        RouterLink link = new RouterLink(text, navigationTarget);
        link.getStyle()
                .set("display", "block")
                .set("width", "100%")
                .set("padding", "12px 16px")
                .set("border-radius", "6px")
                .set("color", "#333")
                .set("text-decoration", "none")
                .set("font-weight", "500")
                .set("box-sizing", "border-box");

        Div container = new Div(link);
        container.getStyle()
                .set("width", "100%")
                .set("margin-bottom", "4px");

        return container;
    }
}
