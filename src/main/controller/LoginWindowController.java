package controller;

import config.MainApplicationConfiguration;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import request.user.LoginRequest;
import request.user.RegisterUserRequest;
import response.user.LoginResponse;
import response.user.RegisterUserResponse;
import service.AuthenticationService;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

@Component
final public class LoginWindowController implements Initializable
{
    private static final String RELATIVE_MAIN_CONTROLLER_PATH = "../ui/mainWindow.fxml";

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private Environment environment;

    @FXML
    private TextField login;

    @FXML
    private PasswordField password;

    @FXML
    private Button loginButton;

    @FXML
    private Button registerButton;

    @FXML
    private Label message;

    @FXML
    private Label userLoggedOut;

    private boolean isLogout = false;

    private String userName;

    @Override
    public void initialize(URL location, ResourceBundle resources) {}

    @FXML
    public void onRegisterButtonClicked()
    {
        this.message.setText("");

        String userName = this.login.getText();
        String password = this.password.getText();

        RegisterUserRequest registerUserRequest = new RegisterUserRequest(userName, password);

        RegisterUserResponse registerUserResponse = authenticationService.registerUser(registerUserRequest);

        if (!registerUserResponse.isSuccessful())
        {
            this.message.setText(registerUserResponse.getErrorMessage());
            return;
        }

        this.message.setText(String.format(environment.getProperty("user.registered_successfully"),
                registerUserResponse.getUserId()));
    }

    @FXML
    public void onLoginButtonClicked() throws IOException
    {
        this.message.setText("");

        String userName = this.login.getText();
        String password = this.password.getText();

        LoginRequest loginRequest = new LoginRequest(userName, password);

        LoginResponse loginResponse = authenticationService.login(loginRequest);

        if (!loginResponse.isSuccessful())
        {
            this.message.setText(loginResponse.getErrorMessage());
            return;
        }

        openMainApplicationWindow(userName);
    }

    private void openMainApplicationWindow(String userName) throws IOException
    {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(RELATIVE_MAIN_CONTROLLER_PATH));
        fxmlLoader.setControllerFactory(MainApplicationConfiguration.applicationContext::getBean);
        Parent root = fxmlLoader.load();
        Stage stage = new Stage();
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initStyle(StageStyle.DECORATED);
        stage.setTitle("My music collection");
        MainWindowController controller = fxmlLoader.getController();
        controller.setUserName(userName);
        stage.setScene(new Scene(root));
        stage.show();

        Stage loginStage = (Stage) registerButton.getScene().getWindow();
        loginStage.close();
    }
}
