import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Window;

public class ReturnBookController implements Controller {
   private GridPane mainPane;


   public ReturnBookController() {
      

      mainPane = new GridPane();


      String labelStyle = "-fx-font-weight: bold";
      Label bookIDLabel = new Label("Book ID  ");

      TextField bookIDText = new TextField();

      bookIDLabel.setStyle(labelStyle);


      Button returnButton = new Button("  Return the book  ");


      mainPane.add(bookIDLabel, 0, 0);
      mainPane.add(bookIDText, 0, 1);

      mainPane.add(returnButton, 1, 1);
   }

   public String getTitle() {
      return "Return a book";
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
