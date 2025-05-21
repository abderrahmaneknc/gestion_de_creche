package io.sleigh.fleet.views.forall;
import com.vaadin.flow.component.UI;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;

@Route("")
public class login extends VerticalLayout {

    public login() {
        // Fill the screen and center content vertically and horizontally
        setSizeFull();
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);

        // Wrapper div to constrain width and style the form
        Div formWrapper = new Div();
        formWrapper.getStyle().set("width", "400px");
        formWrapper.getStyle().set("padding", "20px");
        formWrapper.getStyle().set("box-shadow", "0 0 10px rgba(0,0,0,0.1)");
        formWrapper.getStyle().set("border-radius", "8px");

        VerticalLayout formLayout = new VerticalLayout();
        formLayout.setSpacing(true);
        formLayout.setPadding(true);
        formLayout.setDefaultHorizontalComponentAlignment(Alignment.STRETCH);

        H2 title = new H2("Sign In");

        TextField username = new TextField("Username");
        PasswordField password = new PasswordField("Password");

        Button loginButton = new Button("Login", e -> {
            // Add login logic here
            Notification.show("Sign in clicked!", 3000, Notification.Position.MIDDLE);
        });
        loginButton.setWidthFull();

        Span signUpText = new Span("Don't have an account? Sign Up");
        signUpText.getStyle().set("color", "blue");
        signUpText.getStyle().set("text-decoration", "underline");
        signUpText.getStyle().set("cursor", "pointer");
        signUpText.getElement().getStyle().set("display", "block");
        signUpText.getElement().getStyle().set("text-align", "center");

        signUpText.addClickListener(e -> UI.getCurrent().navigate("signup"));
        signUpText.setWidthFull();


        formLayout.add(title, username, password, loginButton, signUpText);
        formWrapper.add(formLayout);

        add(formWrapper);
    }
}
