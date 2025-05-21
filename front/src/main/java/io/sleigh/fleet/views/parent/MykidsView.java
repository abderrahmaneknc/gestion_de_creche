package io.sleigh.fleet.views.parent;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import io.sleigh.fleet.views.admin.ChildrenManagementView;

import java.time.LocalDate;
import java.util.List;

@Route(value = "mykids-management", layout = MainLayoutParent.class)
public class MykidsView extends VerticalLayout {

    private final Grid<ChildrenManagementView.Child> childGrid;
    private final HorizontalLayout headerLayout;
    private final VerticalLayout childDetailsLayout;
    private HorizontalLayout detailsHeaderLayout;

    private final Button backButton;
    private final Button modifyButton;
    private final HorizontalLayout buttonsLayout;

    public MykidsView() {
        setSizeFull();
        setPadding(true);
        setSpacing(true);
        getStyle().set("padding", "20px");

        // Title
        H2 title = new H2("Children Management");
        title.getStyle()
                .set("margin-bottom", "10px")
                .set("color", "#2c3e50");

        // inscription Button
        Button inscriptButton = new Button("Inscript New Child");
        inscriptButton.getStyle()
                .set("border", "none")
                .set("margin-left", "auto");

        headerLayout = new HorizontalLayout(title, inscriptButton);
        headerLayout.setWidthFull();
        headerLayout.setAlignItems(FlexComponent.Alignment.CENTER);

        // Grid setup
        childGrid = new Grid<>(ChildrenManagementView.Child.class, false);
        childGrid.setWidthFull();
        childGrid.addThemeVariants(GridVariant.LUMO_ROW_STRIPES);
        childGrid.getStyle()
                .set("border-radius", "12px")
                .set("box-shadow", "0 4px 10px rgba(0,0,0,0.08)");

        childGrid.addColumn(ChildrenManagementView.Child::getId)
                .setHeader("ID")
                .setAutoWidth(true)
                .setSortable(true);

        childGrid.addColumn(ChildrenManagementView.Child::getFirstName)
                .setHeader("First Name")
                .setAutoWidth(true)
                .setSortable(true);

        childGrid.addColumn(ChildrenManagementView.Child::getLastName)
                .setHeader("Last Name")
                .setAutoWidth(true)
                .setSortable(true);

        childGrid.addColumn(ChildrenManagementView.Child::getBirthday)
                .setHeader("Birthday")
                .setAutoWidth(true)
                .setSortable(true);

        childGrid.addColumn(ChildrenManagementView.Child::getGrade)
                .setHeader("Grade")
                .setAutoWidth(true)
                .setSortable(true);

        // Sample children data
        List<ChildrenManagementView.Child> children = List.of(
                new ChildrenManagementView.Child(1L, "Alice", "Smith", LocalDate.of(2019, 5, 12), "Pre-K"),
                new ChildrenManagementView.Child(2L, "Bob", "Johnson", LocalDate.of(2018, 3, 22), "Kindergarten"),
                new ChildrenManagementView.Child(3L, "Charlie", "Williams", LocalDate.of(2020, 7, 8), "Nursery"),
                new ChildrenManagementView.Child(4L, "Daisy", "Brown", LocalDate.of(2017, 11, 2), "Kindergarten")
        );
        childGrid.setItems(children);

        // Selection listener on grid row
        childGrid.setSelectionMode(Grid.SelectionMode.SINGLE);
        childGrid.addSelectionListener(event -> {
            event.getFirstSelectedItem().ifPresent(this::showChildDetails);
        });

        // Create details layout (hidden initially)
        childDetailsLayout = new VerticalLayout();
        childDetailsLayout.setWidthFull();
        childDetailsLayout.setPadding(true);
        childDetailsLayout.setSpacing(true);
        childDetailsLayout.setVisible(false);

        // Back button to return to grid
        backButton = new Button("Back to List", e -> showChildList());
        modifyButton = new Button("Modify",e -> {});
        buttonsLayout = new HorizontalLayout(backButton, modifyButton);
        buttonsLayout.setWidthFull();
        buttonsLayout.setJustifyContentMode(JustifyContentMode.START);
        buttonsLayout.setSpacing(true);
        childDetailsLayout.add(buttonsLayout);


        // Add all to main layout - initially show header + grid
        add(headerLayout, childGrid, childDetailsLayout);
    }

    private void showChildDetails(ChildrenManagementView.Child child) {
        headerLayout.setVisible(false);
        childGrid.setVisible(false);

        childDetailsLayout.removeAll();

        // Create title
        H2 detailsTitle = new H2("Child Details");
        detailsTitle.getStyle().set("margin", "0"); //
        detailsTitle.setWidthFull(); // optional clean up margin

        // Buttons layout (already created)
        buttonsLayout.setJustifyContentMode(JustifyContentMode.END);

        // Create horizontal layout with buttons on the left and title on the right
        detailsHeaderLayout = new HorizontalLayout( detailsTitle,buttonsLayout);
        detailsHeaderLayout.setWidthFull();
        detailsHeaderLayout.setAlignItems(FlexComponent.Alignment.CENTER);
        detailsHeaderLayout.expand(detailsTitle); // title takes all remaining space pushing it right

        // Add the combined header to the details layout
        childDetailsLayout.add(detailsHeaderLayout);

        // Add child info below
        childDetailsLayout.add(new Paragraph("ID: " + child.getId()));
        childDetailsLayout.add(new Paragraph("First Name: " + child.getFirstName()));
        childDetailsLayout.add(new Paragraph("Last Name: " + child.getLastName()));
        childDetailsLayout.add(new Paragraph("Birthday: " + child.getBirthday()));
        childDetailsLayout.add(new Paragraph("Grade: " + child.getGrade()));

        childDetailsLayout.setVisible(true);
    }

    private void showChildList() {
        // Show header and grid
        headerLayout.setVisible(true);
        childGrid.setVisible(true);

        // Hide details layout
        childDetailsLayout.setVisible(false);

        // Clear selection in grid
        childGrid.deselectAll();
    }
}
