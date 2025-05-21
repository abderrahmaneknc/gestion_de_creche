package io.sleigh.fleet.views.admin;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridSelectionModel;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.shared.Registration;

import java.util.ArrayList;
import java.util.List;

@Route(value = "user-management", layout = MainLayoutAdmin.class)
public class UserManagementView extends VerticalLayout {

    private final Grid<User> userGrid;
    private final List<Long> selectedUsers = new ArrayList<>();
    private List<User> users;

    // Save the listener registration for modifying user dialog
    private Registration saveModifyListenerRegistration;

    public UserManagementView() {
        setSizeFull();
        setPadding(true);
        setSpacing(true);
        getStyle().set("padding", "20px");

        // Title
        H2 title = new H2("User Management");
        title.getStyle()
                .set("margin-bottom", "10px")
                .set("color", "#2c3e50");

        // Add user dialog (unchanged)
        Dialog addUserDialog = new Dialog();
        addUserDialog.setCloseOnOutsideClick(false);

        TextField addFullNameField = new TextField("Full Name");
        EmailField addEmailField = new EmailField("Email");
        PasswordField addPasswordField = new PasswordField("Password");
        TextField addRoleField = new TextField("Role");

        FormLayout addFormLayout = new FormLayout(addFullNameField, addEmailField, addPasswordField, addRoleField);

        Button confirmAddButton = new Button("Confirm Add");
        confirmAddButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        confirmAddButton.getStyle()
                .set("background-color", "#1976d2")
                .set("color", "white");

        Button cancelAddButton = new Button("Cancel", event -> addUserDialog.close());
        cancelAddButton.getStyle()
                .set("background-color", "#e0e0e0")
                .set("color", "black");

        HorizontalLayout addDialogButtons = new HorizontalLayout(confirmAddButton, cancelAddButton);
        addDialogButtons.setJustifyContentMode(FlexComponent.JustifyContentMode.END);

        VerticalLayout addDialogLayout = new VerticalLayout(addFormLayout, addDialogButtons);
        addDialogLayout.setPadding(false);
        addDialogLayout.setSpacing(true);
        addUserDialog.add(addDialogLayout);

        // Modify user dialog (new)
        Dialog modifyUserDialog = new Dialog();
        modifyUserDialog.setCloseOnOutsideClick(false);

        TextField modFullNameField = new TextField("Full Name");
        EmailField modEmailField = new EmailField("Email");
        PasswordField modPasswordField = new PasswordField("Password");
        TextField modRoleField = new TextField("Role");

        // Disable FullName and Email fields for modification
        modFullNameField.setReadOnly(true);
        modEmailField.setReadOnly(true);

        FormLayout modFormLayout = new FormLayout(modFullNameField, modEmailField, modPasswordField, modRoleField);

        Button saveModifyButton = new Button("Save");
        saveModifyButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        saveModifyButton.getStyle()
                .set("background-color", "#1976d2")
                .set("color", "white");

        Button cancelModifyButton = new Button("Cancel", event -> modifyUserDialog.close());
        cancelModifyButton.getStyle()
                .set("background-color", "#e0e0e0")
                .set("color", "black");

        HorizontalLayout modifyDialogButtons = new HorizontalLayout(saveModifyButton, cancelModifyButton);
        modifyDialogButtons.setJustifyContentMode(FlexComponent.JustifyContentMode.END);

        VerticalLayout modifyDialogLayout = new VerticalLayout(modFormLayout, modifyDialogButtons);
        modifyDialogLayout.setPadding(false);
        modifyDialogLayout.setSpacing(true);
        modifyUserDialog.add(modifyDialogLayout);

        // Buttons
        Button addButton = new Button("Add User", event -> addUserDialog.open());
        addButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        addButton.getStyle()
                .set("background-color", "#1976d2")
                .set("color", "white")
                .set("margin-right", "10px")
                .set("height", "40px");

        Button deleteButton = new Button("Delete User");
        deleteButton.addThemeVariants(ButtonVariant.LUMO_ERROR);
        deleteButton.getStyle()
                .set("background-color", "#e53935")
                .set("color", "white")
                .set("height", "40px");

        HorizontalLayout buttonLayout = new HorizontalLayout(addButton, deleteButton);
        buttonLayout.setSpacing(true);
        buttonLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.END);
        buttonLayout.setAlignItems(FlexComponent.Alignment.CENTER);

        HorizontalLayout headerLayout = new HorizontalLayout(title, buttonLayout);
        headerLayout.setWidthFull();
        headerLayout.setAlignItems(FlexComponent.Alignment.CENTER);
        headerLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);

        // Initialize grid here to avoid variable might not be initialized error
        userGrid = new Grid<>(User.class, false);
        userGrid.setWidthFull();
        userGrid.addThemeVariants(GridVariant.LUMO_ROW_STRIPES);
        userGrid.getStyle()
                .set("border-radius", "12px")
                .set("box-shadow", "0 4px 10px rgba(0,0,0,0.08)");

        userGrid.addColumn(User::getId)
                .setHeader("ID")
                .setAutoWidth(true)
                .setSortable(true);

        userGrid.addColumn(User::getFullName)
                .setHeader("Full Name")
                .setAutoWidth(true)
                .setSortable(true);

        userGrid.addColumn(User::getEmail)
                .setHeader("Email")
                .setAutoWidth(true)
                .setSortable(true);

        userGrid.addColumn(User::getPassword)
                .setHeader("Password")
                .setAutoWidth(true)
                .setSortable(false);

        userGrid.addColumn(User::getRole)
                .setHeader("Role")
                .setAutoWidth(true)
                .setSortable(true);

        // Modify button column with white background and open modify dialog
        userGrid.addComponentColumn(user -> {
            Button modifyButton = new Button("Modify");
            modifyButton.getStyle()
                    .set("background-color", "#ffffff")  // White background
                    .set("color", "#000000");

            modifyButton.addClickListener(e -> {
                // Fill dialog fields with user data
                modFullNameField.setValue(user.getFullName());
                modEmailField.setValue(user.getEmail());
                modPasswordField.setValue(user.getPassword());
                modRoleField.setValue(user.getRole());

                modifyUserDialog.open();

                // Remove previous listener to avoid multiple triggers
                if (saveModifyListenerRegistration != null) {
                    saveModifyListenerRegistration.remove();
                }

                // Add new click listener for save button
                saveModifyListenerRegistration = saveModifyButton.addClickListener(ev -> {
                    // Update only password and role fields
                    User updatedUser = new User(user.getId(),
                            user.getFullName(),      // fullName stays the same
                            user.getEmail(),         // email stays the same
                            modPasswordField.getValue(),
                            modRoleField.getValue());

                    int index = users.indexOf(user);
                    if (index >= 0) {
                        users.set(index, updatedUser);
                        userGrid.setItems(users);
                    }

                    modifyUserDialog.close();
                });
            });
            return modifyButton;
        }).setHeader("Actions");

        // Enable row selection
        GridSelectionModel<User> selectionModel = userGrid.setSelectionMode(Grid.SelectionMode.MULTI);
        selectionModel.addSelectionListener(event -> {
            selectedUsers.clear();
            event.getAllSelectedItems().forEach(user -> selectedUsers.add(user.getId()));
            System.out.println("Selected user IDs: " + selectedUsers);
        });

        // Static sample data (stored as field for easier update)
        users = new ArrayList<>(List.of(
                new User(1L, "Emma Watson", "emma@example.com", "password123", "Admin"),
                new User(2L, "John Doe", "john@example.com", "secret456", "Teacher"),
                new User(3L, "Alice Johnson", "alice@example.com", "pass789", "Parent")
        ));
        userGrid.setItems(users);

        // Selected row style
        userGrid.getElement().executeJs("""
            const style = document.createElement('style');
            style.textContent = `
                vaadin-grid::part(row):hover {
                    background-color: #e3f2fd;
                }
                vaadin-grid::part(selected-row) {
                    background-color: #bbdefb !important;
                }
            `;
            document.head.appendChild(style);
        """);

        add(addUserDialog, headerLayout, userGrid);
    }

    public static class User {
        private final Long id;
        private final String fullName;
        private final String email;
        private final String password;
        private final String role;

        public User(Long id, String fullName, String email, String password, String role) {
            this.id = id;
            this.fullName = fullName;
            this.email = email;
            this.password = password;
            this.role = role;
        }

        public Long getId() {
            return id;
        }

        public String getFullName() {
            return fullName;
        }

        public String getEmail() {
            return email;
        }

        public String getPassword() {
            return password;
        }

        public String getRole() {
            return role;
        }
    }
}
