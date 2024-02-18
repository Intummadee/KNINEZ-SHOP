package com.example.inventory.views;

import com.example.inventory.core.AdminEntity;
import com.example.inventory.core.AdminService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoUtility;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

@Route("")
public class LoginView  extends VerticalLayout {
    private TextField usernameField;
    private PasswordField passwordField;
    private Button loginButton;
    private AdminService adminService;

    @Autowired
    public LoginView(AdminService adminService) {
        this.adminService = adminService;

        // Initialize components
        usernameField = new TextField("Username");
        passwordField = new PasswordField("Password");
        loginButton = new Button("Login");

        loginButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);


        VerticalLayout centeringLayout = new VerticalLayout();
        centeringLayout.setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        centeringLayout.getStyle().set("background-color", "whitesmoke");
        centeringLayout.setWidth("min-content");
        centeringLayout.getStyle().set("border-radius", "5px");
        centeringLayout.addClassName(LumoUtility.Padding.LARGE);

        usernameField.setWidth("300px");
        passwordField.setWidth("300px");
        loginButton.setWidth("150px");

        // Add components to the centering layout
        centeringLayout.add(new H3("Inventory Management"),usernameField, passwordField, loginButton);

        // Center the layout on the page
        setSizeFull();
        setJustifyContentMode(JustifyContentMode.CENTER);
        setAlignItems(Alignment.CENTER);

        // Add the centering layout to the main layout
        add(centeringLayout);
        addLoginFunctionality();
    }
    private void addLoginFunctionality() {
        loginButton.addClickListener(event -> {
            String enteredUsername = usernameField.getValue();
            String enteredPassword = passwordField.getValue();

            // Check if either the username or password is empty
            if (enteredUsername.isEmpty() || enteredPassword.isEmpty()) {
                // Display an error message if fields are not filled in
                Notification notification = Notification.show("กรุณากรอกข้อมูลให้ครบ", 3000,Notification.Position.TOP_CENTER);
                notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
                return;
            }

            // Check if the entered credentials are valid using the AdminService
            if (adminService.isValidLogin(enteredUsername, enteredPassword)) {
                // Navigate to InventoryView if the login is successful
                getUI().ifPresent(ui -> ui.navigate("inventory"));
            } else {
                // Display an error message if the login fails
                Notification notification = Notification.show("ชื่อผู้ใช้หรือรหัสผ่านไม่ถูกต้อง", 3000,Notification.Position.TOP_CENTER);
                notification.addThemeVariants(NotificationVariant.LUMO_ERROR);

            }

            // Clear the fields after processing
            usernameField.clear();
            passwordField.clear();
        });

    }

}
