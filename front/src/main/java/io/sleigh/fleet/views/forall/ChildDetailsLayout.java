package io.sleigh.fleet.views.forall;

import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import io.sleigh.fleet.views.admin.ChildrenManagementView;

public class ChildDetailsLayout extends VerticalLayout {

    private final H2 title = new H2("Child Details");
    private final Paragraph id = new Paragraph();
    private final Paragraph firstName = new Paragraph();
    private final Paragraph lastName = new Paragraph();
    private final Paragraph birthday = new Paragraph();
    private final Paragraph grade = new Paragraph();

    public ChildDetailsLayout() {
        setWidthFull();
        setPadding(true);
        setSpacing(true);
        setVisible(false);  // hide initially

        add(title, id, firstName, lastName, birthday, grade);
    }

    public void setChild(ChildrenManagementView.Child child) {
        if (child == null) {
            clear();
            return;
        }

        id.setText("ID: " + child.getId());
        firstName.setText("First Name: " + child.getFirstName());
        lastName.setText("Last Name: " + child.getLastName());
        birthday.setText("Birthday: " + child.getBirthday());
        grade.setText("Grade: " + child.getGrade());

        setVisible(true);
    }

    public void clear() {
        id.setText("");
        firstName.setText("");
        lastName.setText("");
        birthday.setText("");
        grade.setText("");
        setVisible(false);
    }
}
