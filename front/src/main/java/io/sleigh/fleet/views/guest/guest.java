package io.sleigh.fleet.views.guest;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

@Route("guest")
public class guest extends VerticalLayout {

    public guest() {
        // Fill screen and center content
        setSizeFull();
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);

        // Container with style
        Div formWrapper = new Div();
        formWrapper.getStyle().set("width", "400px");
        formWrapper.getStyle().set("padding", "20px");
        formWrapper.getStyle().set("box-shadow", "0 0 10px rgba(0,0,0,0.1)");
        formWrapper.getStyle().set("border-radius", "8px");

        VerticalLayout formLayout = new VerticalLayout();
        formLayout.setSpacing(true);
        formLayout.setPadding(true);
        formLayout.setDefaultHorizontalComponentAlignment(Alignment.STRETCH);

        // Title
        H2 title = new H2("Welcome Guest");

        // Descriptive text
        Paragraph description = new Paragraph(
                "Welcome! As a guest, you can register your child or request to become part of our school staff. Your need to wait until the admin accept you demand (if the admin accespt your child registration you will have the role of parent same as the other demand).  " +
                        "Please choose an option below to proceed."
        );



        // Buttons
        Button registerChildButton = new Button("Register My Child", e -> {
            Notification.show("Child registration submitted.");
        });

        Button requestTeacherButton = new Button("Request to Become a Teacher", e -> {
            Notification.show("Teacher request submitted.");
        });

        Button requestChefButton = new Button("Request to Become a Chef", e -> {
            Notification.show("Chef request submitted.");
        });

        // Make buttons full width
        registerChildButton.setWidthFull();
        requestTeacherButton.setWidthFull();
        requestChefButton.setWidthFull();

        // Add everything to layout
        formLayout.add(title, description,
                registerChildButton, requestTeacherButton, requestChefButton);

        formWrapper.add(formLayout);
        add(formWrapper);
    }
}
