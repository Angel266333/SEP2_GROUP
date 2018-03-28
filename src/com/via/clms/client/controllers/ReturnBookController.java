import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Window;

public class ReturnBookController implements Controller {
   private GridPane mainPane;
   private long bookID;
   private String bookName;

   private String name;

   public ReturnBookController(long bookID) {
      this.bookID = bookID;

      mainPane = new GridPane();
      getData();


      String labelStyle = "-fx-font-weight: bold";
      Label nameLabel = new Label("Name  ");
      nameLabel.setStyle(labelStyle);
      Label nameTextLabel = new Label(name);
      Label bookIDLabel = new Label("Book ID  ");
      Label bookTitleLabel = new Label("Book Title  ");
      bookTitleLabel.setStyle(labelStyle);
      Label titleTextLabel = new Label(bookName);


      bookIDLabel.setStyle(labelStyle);
      Label bookIDTextLabel = new Label("" + bookID);


      Button returnButton = new Button("  Return the book  ");

      
      mainPane.add(nameLabel, 0, 0);
      mainPane.add(nameTextLabel, 2, 0);
      mainPane.add(bookIDLabel, 0, 1);
      mainPane.add(bookIDTextLabel, 2, 1);
      mainPane.add(bookTitleLabel, 0, 2);
      mainPane.add(titleTextLabel, 2, 2);
      mainPane.add(returnButton, 1, 3);
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

   private void getData() {
      //Test data REMOVE====
      name = "John Hansen";
      bookName = "The Da Vinci Code";
      bookID = 45678;

   }
}