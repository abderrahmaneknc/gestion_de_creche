package io.sleigh.fleet.views;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;

import io.sleigh.fleet.views.models.child;

@Route("child-details")
@PageTitle("Child Details")
public class ChildDetailsView extends VerticalLayout implements HasUrlParameter<Integer> {

    private Span nameSpan = new Span();
    private Span ageSpan = new Span();
    private Span classroomSpan = new Span();

    public ChildDetailsView() {
        add(new Div(new Span("Loading child details...")));
    }

    @Override
    public void setParameter(BeforeEvent event, Integer childId) {
        removeAll(); // clear loading message

        // Here, you would load child data from DB or service by childId.
        // For demo, let's simulate with static data:
        child child = loadChildById(childId);

        if (child == null) {
            add(new Span("Child not found!"));
            return;
        }

        nameSpan.setText("Name: " + child.getName());
        ageSpan.setText("Age: " + child.getAge());
        classroomSpan.setText("Classroom: " + child.getClassroom());

        add(nameSpan, ageSpan, classroomSpan);
    }

    // Simulate fetching a child by id (replace with your real data source)
    private child loadChildById(int id) {
        return switch (id) {
            case 1 -> new child(1, "Ahmed", 10, "5A");
            case 2 -> new child(2, "Sara", 8, "3B");
            case 3 -> new child(3, "Yasmine", 12, "6C");
            default -> null;
        };
    }
}
