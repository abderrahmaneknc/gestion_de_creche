package io.sleigh.fleet.views.guest;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.MemoryBuffer;
import com.vaadin.flow.component.notification.Notification;
import java.util.Arrays;


@Route("register-child-parent-ins")
public class childParentRegistration extends VerticalLayout {

    private  VerticalLayout parentForm;
    private  VerticalLayout childForm;
    private Button nextButton ;
    private  Button returnButton ;
    private  Button backButton ;
    private  Button confirmButton ;

    public childParentRegistration() {
        setSizeFull();
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);
        // Container with style
        Div formWrapper = new Div();
        formWrapper.getStyle().set("width", "600px");
        formWrapper.getStyle().set("padding", "20px");
        formWrapper.getStyle().set("box-shadow", "0 0 10px rgba(0,0,0,0.1)");
        formWrapper.getStyle().set("border-radius", "8px");
        VerticalLayout formLayout = new VerticalLayout();
        formLayout.setSpacing(true);
        formLayout.setPadding(true);
        formLayout.setDefaultHorizontalComponentAlignment(Alignment.STRETCH);

        // Title & description
        H2 title = new H2("Child Registration");
        Paragraph description = new Paragraph("Please fill in the required information about yourself and your child to register it and have the parent role if your are going to be validated by the admin");
        description.getStyle().set("width" , "600px");
        description.getStyle().set("text-align","center");

        // ----------------------------------------------------------------------------------------------

        // PARENT SECTION
        parentForm = new VerticalLayout();
//        parentForm.setWidth("400px");
        parentForm.setSpacing(true);

        TextField parentID = new TextField("Parent National ID");
        parentID.setWidthFull();
        TextField parentName = new TextField("Full Name");
        parentName.setWidthFull();
        TextField parentEmail = new TextField("Email");
        parentEmail.setWidthFull();
        TextField parentPhone = new TextField("Phone Number");
        parentPhone.setWidthFull();
        parentForm.add(parentID, parentName, parentEmail, parentPhone);

        // ----------------------------------------------------------------------------------------------

        // CHILD SECTION
        childForm = new VerticalLayout();
//        childForm.setWidth("400px");
        childForm.setSpacing(true);
        childForm.setVisible(false); // hidden by default

        TextField childName = new TextField("Child Full Name");
        childName.setWidthFull();
        TextField childAge = new TextField("Child Age");
        childAge.setWidthFull();
        TextField childGrade = new TextField("Grade Applying For");
        childGrade.setWidthFull();

        Paragraph fileDescription =new Paragraph("You need to upload a file with all serious document for your child (ex: birthday document, special needs...)");
        MemoryBuffer pdfBuffer = new MemoryBuffer();
        Upload pdfUpload = new Upload(pdfBuffer);
        pdfUpload.setAcceptedFileTypes("application/pdf");  // Only accept PDFs
        pdfUpload.setMaxFiles(1);
        pdfUpload.setDropAllowed(true);
        Button uploadButton = new Button("Upload");
        uploadButton.setWidthFull();
        uploadButton.getStyle().set("white-space", "normal");
        uploadButton.getStyle().set("text-align", "center");
        pdfUpload.setUploadButton(uploadButton); // Custom upload button text
       pdfUpload.setWidthFull();

        pdfUpload.addSucceededListener(event -> {
            Notification.show("Uploaded: " + event.getFileName());
            // You can process the uploaded PDF with pdfBuffer.getInputStream()
        });
        childForm.add(childName, childAge, childGrade,fileDescription,pdfUpload);


        // NAVIGATION BUTTONS
        nextButton = new Button("Next", e -> {
            parentForm.setVisible(false);
            childForm.setVisible(true);
            nextButton.setVisible(false);
            confirmButton.setVisible(true);
            backButton.setVisible(true);
        });

        backButton = new Button("Back", e -> {
            childForm.setVisible(false);
            parentForm.setVisible(true);
            nextButton.setVisible(true);
            confirmButton.setVisible(false);
            backButton.setVisible(false);

        });

        backButton.setVisible(false); // initially hidden

        confirmButton = new Button("Confirm", e -> {
            // Handle confirmation logic here
            Notification notification = new Notification(
                    "Your demand has been sent please wait for the validation of the admin, a notification will be sent to you when that happens",
                    3000, Notification.Position.MIDDLE);

            notification.getElement().getStyle().set("width", "200px");   // wider so message fits nicely
            notification.getElement().getStyle().set("white-space", "normal"); // allow wrapping
            notification.open();

        });
        confirmButton.setVisible(false); // initially hidden

        Button returnButton = new Button("Return", e -> UI.getCurrent().navigate("guest")
        );

        // BUTTON LAYOUT

        Div buttonsLayout = new Div(backButton, confirmButton, nextButton);

        buttonsLayout.setWidthFull();
        buttonsLayout.getStyle().set("margin-top", "20px");
        buttonsLayout.getStyle().set("display", "flex");
        buttonsLayout.getStyle().set("gap", "10px");
        buttonsLayout.getStyle().set("justify-content", "flex-end");
        buttonsLayout.getStyle().set("gap", "400px");





        ////

        Div buttonLayout2 = new Div(returnButton);
        buttonLayout2.getStyle().set("width","600px");
        buttonLayout2.getStyle().set("margin-top", "20px");
        buttonLayout2.getStyle().set("display", "flex");
        buttonLayout2.getStyle().set("justify-content", "flex-start");
        buttonLayout2.getStyle().set("gap", "10px");



        // Add everything to layout
        formLayout.add( parentForm, childForm, buttonsLayout);

        formWrapper.add(formLayout);
        add(title, description,formWrapper,buttonLayout2);


    }
}
