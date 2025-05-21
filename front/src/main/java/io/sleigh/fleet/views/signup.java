package io.sleigh.fleet.views;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.component.notification.Notification;

@Route("signup")
public class signup extends VerticalLayout {

    public signup() {
        // Make this layout fill the viewport and center its content
        setSizeFull();
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);

        // Create a wrapper container for the form with fixed width
        Div formWrapper = new Div();
        formWrapper.getStyle().set("width", "400px");
        formWrapper.getStyle().set("padding", "20px");
        formWrapper.getStyle().set("box-shadow", "0 0 10px rgba(0,0,0,0.1)");
        formWrapper.getStyle().set("border-radius", "8px");

        VerticalLayout formLayout = new VerticalLayout();
        formLayout.setSpacing(true);
        formLayout.setPadding(true);
        formLayout.setDefaultHorizontalComponentAlignment(Alignment.STRETCH);

        H2 title = new H2("Sign Up");

        TextField username = new TextField("Username");
        PasswordField password = new PasswordField("Password");
        PasswordField confirmPassword = new PasswordField("Confirm Password");

        Button signUpButton = new Button("Sign Up", e -> {
            // signup logic here
            Notification.show("Sign Up clicked!", 3000, Notification.Position.MIDDLE);
        });
        signUpButton.setWidthFull();

        Span signInText = new Span("Already have an account? Sign Ip");
        signInText.getStyle().set("color", "blue");
        signInText.getStyle().set("text-decoration", "underline");
        signInText.getStyle().set("cursor", "pointer");
        signInText.getElement().getStyle().set("display", "block");
        signInText.getElement().getStyle().set("text-align", "center");

        signInText.addClickListener(e -> UI.getCurrent().navigate(""));
        signInText.setWidthFull();

        formLayout.add(title, username, password, confirmPassword, signUpButton,signInText);
        formWrapper.add(formLayout);

        add(formWrapper);
    }
}
