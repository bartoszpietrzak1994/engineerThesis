package controller;

import javafx.fxml.Initializable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import service.AuthenticationService;

import java.net.URL;
import java.util.ResourceBundle;

@Component
final public class LoginWindowController implements Initializable
{
    @Autowired
    private AuthenticationService authenticationService;

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {

    }
}
