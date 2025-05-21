package io.sleigh.fleet.views.admin;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridSelectionModel;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
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

        add(headerLayout, childGrid);
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
