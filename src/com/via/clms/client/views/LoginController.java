import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Window;

public class LoginController implements Controller {
   private GridPane mainPane;
   private String userName;
   private String password;

   public LoginController() {

      mainPane = new GridPane();

      //Labels
      String labelStyle = "-fx-font-weight: bold";
      
      Label loginLabel = new Label("Login");
      loginLabel.setStyle(labelStyle);
      
      Label userName = new Label("User Name");
      userName.setStyle(labelStyle);
      
      Label password = new Label("Password");
      password.setStyle(labelStyle);

      //TextFields
      TextField userNameField = new TextField();
      userNameField.setPrefColumnCount(20);
      
      PasswordField passwordField = new PasswordField();
      passwordField.setPrefColumnCount(20);

      //Buttons
      Button loginButton = new Button("Login");
      

      mainPane.add(loginLabel, 0, 0);
      mainPane.add(userName, 0, 2);
      mainPane.add(userNameField, 0, 3);
      mainPane.add(password, 0, 4);
      mainPane.add(passwordField, 0, 5);
      mainPane.add(loginButton, 0, 6);
   }

   public String getTitle() {
      return "Login";
   }

   public Parent getComponent() {
      return mainPane;
   }

   public void onWindowOpen(Window window) {

   }

   public void onWindowClose(Window window) {

   }

   public void onWindowRefresh(Window window) {

   }

  
   
}

