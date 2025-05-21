package io.sleigh.fleet.views.admin;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridSelectionModel;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Route(value = "children-management", layout = MainLayoutAdmin.class)
public class ChildrenManagementView extends VerticalLayout {

    private final Grid<Child> childGrid;
    private final List<Long> selectedChildren = new ArrayList<>();

    public ChildrenManagementView() {
        setSizeFull();
        setPadding(true);
        setSpacing(true);
        getStyle().set("padding", "20px");

        // Title
        H2 title = new H2("Children Management");
        title.getStyle()
                .set("margin-bottom", "10px")
                .set("color", "#2c3e50");

        // Buttons
        Button addButton = new Button("Add Child");
        addButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        addButton.getStyle()
                .set("background-color", "#1976d2")
                .set("color", "white")
                .set("margin-right", "10px")
                .set("height", "40px");

        Button deleteButton = new Button("Delete Child");
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

        // Grid
        childGrid = new Grid<>(Child.class, false);
        childGrid.setWidthFull();
        childGrid.addThemeVariants(GridVariant.LUMO_ROW_STRIPES);
        childGrid.getStyle()
                .set("border-radius", "12px")
                .set("box-shadow", "0 4px 10px rgba(0,0,0,0.08)");

        childGrid.addColumn(Child::getId)
                .setHeader("ID")
                .setAutoWidth(true)
                .setSortable(true);

        childGrid.addColumn(Child::getFirstName)
                .setHeader("First Name")
                .setAutoWidth(true)
                .setSortable(true);

        childGrid.addColumn(Child::getLastName)
                .setHeader("Last Name")
                .setAutoWidth(true)
                .setSortable(true);

        childGrid.addColumn(Child::getBirthday)
                .setHeader("Birthday")
                .setAutoWidth(true)
                .setSortable(true);

        childGrid.addColumn(Child::getGrade)
                .setHeader("Grade")
                .setAutoWidth(true)
                .setSortable(true);

        // Enable row selection
        GridSelectionModel<Child> selectionModel = childGrid.setSelectionMode(Grid.SelectionMode.MULTI);
        selectionModel.addSelectionListener(event -> {
            selectedChildren.clear();
            event.getAllSelectedItems().forEach(child -> selectedChildren.add(child.getId()));
            System.out.println("Selected children IDs: " + selectedChildren);
        });

        // Static sample data
        List<Child> children = List.of(
                new Child(1L, "Alice", "Smith", LocalDate.of(2019, 5, 12), "Pre-K"),
                new Child(2L, "Bob", "Johnson", LocalDate.of(2018, 3, 22), "Kindergarten"),
                new Child(3L, "Charlie", "Williams", LocalDate.of(2020, 7, 8), "Nursery"),
                new Child(4L, "Daisy", "Brown", LocalDate.of(2017, 11, 2), "Kindergarten")
        );
        childGrid.setItems(children);

        // Selected row style
        childGrid.getElement().executeJs("""
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

        // Add Child Dialog
        Dialog addChildDialog = createAddChildDialog();

        addButton.addClickListener(e -> addChildDialog.open());

        add(headerLayout, childGrid);
    }

    private Dialog createAddChildDialog() {
        Dialog dialog = new Dialog();
        dialog.setWidth("400px");

        // Input fields
        TextField firstNameField = new TextField("First Name");
        TextField lastNameField = new TextField("Last Name");
        DatePicker birthdayPicker = new DatePicker("Birthday");
        TextField gradeField = new TextField("Grade");

        FormLayout formLayout = new FormLayout();
        formLayout.add(firstNameField, lastNameField, birthdayPicker, gradeField);
        formLayout.setMaxWidth("400px");

        // Buttons
        Button confirmButton = new Button("Confirm Add");
        confirmButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        confirmButton.getStyle()
                .set("background-color", "#1976d2")
                .set("color", "white")
                .set("width", "100%");

        Button cancelButton = new Button("Cancel");
        cancelButton.addThemeVariants(ButtonVariant.LUMO_CONTRAST);
        cancelButton.getStyle()
                .set("background-color", "#9e9e9e")
                .set("color", "white")
                .set("width", "100%");

        VerticalLayout buttonsLayout = new VerticalLayout(confirmButton, cancelButton);
        buttonsLayout.setSpacing(true);
        buttonsLayout.setPadding(false);
        buttonsLayout.setWidthFull();

        VerticalLayout dialogLayout = new VerticalLayout(formLayout, buttonsLayout);
        dialogLayout.setPadding(false);
        dialogLayout.setSpacing(true);
        dialogLayout.setAlignItems(FlexComponent.Alignment.STRETCH);

        dialog.add(dialogLayout);

        // Confirm button logic (simple example: add new child to grid)
        confirmButton.addClickListener(event -> {
            String firstName = firstNameField.getValue().trim();
            String lastName = lastNameField.getValue().trim();
            LocalDate birthday = birthdayPicker.getValue();
            String grade = gradeField.getValue().trim();

            if (firstName.isEmpty() || lastName.isEmpty() || birthday == null || grade.isEmpty()) {
                Notification.show("Please fill in all fields", 3000, Notification.Position.MIDDLE);
                return;
            }

            // Add new child - here you should actually save to your backend
            long newId = childGrid.getDataProvider().fetch(new com.vaadin.flow.data.provider.Query<>()).count() + 1;
            Child newChild = new Child(newId, firstName, lastName, birthday, grade);
            childGrid.getListDataView().addItem(newChild);

            dialog.close();
            Notification.show("Child added successfully", 3000, Notification.Position.TOP_CENTER);

            // Clear fields for next use
            firstNameField.clear();
            lastNameField.clear();
            birthdayPicker.clear();
            gradeField.clear();
        });

        cancelButton.addClickListener(e -> {
            dialog.close();
            firstNameField.clear();
            lastNameField.clear();
            birthdayPicker.clear();
            gradeField.clear();
        });

        return dialog;
    }

    public static class Child {
        private final Long id;
        private final String firstName;
        private final String lastName;
        private final LocalDate birthday;
        private final String grade;

        public Child(Long id, String firstName, String lastName, LocalDate birthday, String grade) {
            this.id = id;
            this.firstName = firstName;
            this.lastName = lastName;
            this.birthday = birthday;
            this.grade = grade;
        }

        public Long getId() {
            return id;
        }

        public String getFirstName() {
            return firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public LocalDate getBirthday() {
            return birthday;
        }

        public String getGrade() {
            return grade;
        }
    }
}
