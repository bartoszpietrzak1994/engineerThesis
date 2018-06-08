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
import response.GenericResponse;
import response.user.RegisterUserResponse;
import service.AuthenticationService;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

@Component
final public class LoginWindowController extends BaseController implements Initializable
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

        GenericResponse genericResponse = authenticationService.login(new LoginRequest(userName, password));

        if (!genericResponse.isSuccessful())
        {
            this.message.setText(genericResponse.getErrorMessage());
            return;
        }

        openMainApplicationWindow(userName);
    }

    private void openMainApplicationWindow(String userName) throws IOException
    {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(RELATIVE_MAIN_CONTROLLER_PATH));
        fxmlLoader.setControllerFactory(this.applicationContext::getBean);
        Parent root = fxmlLoader.load();
        MainWindowController mainWindowController = fxmlLoader.getController();
        mainWindowController.setUserName(userName);
        mainWindowController.loadUserAlbums();
        Stage stage = new Stage();
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initStyle(StageStyle.DECORATED);
        stage.setTitle("My music collection");
        stage.setScene(new Scene(root));
        stage.show();

        Stage loginStage = (Stage) registerButton.getScene().getWindow();
        loginStage.close();
    }
}
