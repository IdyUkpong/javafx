import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {

    private final ObservableList<User> users = FXCollections.observableArrayList();

    @Override
    public void start(Stage primaryStage) {
        users.addAll(UserDAO.findAll());


        // --- Form fields ---
        TextField nameField = new TextField();
        nameField.setPromptText("Name");

        TextField ageField = new TextField();
        ageField.setPromptText("Age");

        TextField countryField = new TextField();
        countryField.setPromptText("Country");

        Button addButton = new Button("Add");

        // --- TableView setup ---
        TableView<User> table = new TableView<>(users);

        TableColumn<User, String> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(data -> data.getValue().nameProperty());

        TableColumn<User, Integer> ageCol = new TableColumn<>("Age");
        ageCol.setCellValueFactory(data -> data.getValue().ageProperty().asObject());

        TableColumn<User, String> countryCol = new TableColumn<>("Country");
        countryCol.setCellValueFactory(data -> data.getValue().countryProperty());

        table.getColumns().addAll(nameCol, ageCol, countryCol);

        // Add user to list
        addButton.setOnAction(e -> {
            try {
                String name = nameField.getText();
                int age = Integer.parseInt(ageField.getText());
                String country = countryField.getText();
                User user = new User(name, age,country);

                users.add(user);
                UserDAO.insert(user);

                // Clear fields
                nameField.clear();
                ageField.clear();
                countryField.clear();

            } catch (Exception ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Please enter valid details.");
                alert.show();
            }
        });

        // Layout
        GridPane form = new GridPane();
        form.setHgap(10);
        form.setVgap(10);

        form.add(new Label("Name:"), 0, 0);
        form.add(nameField, 1, 0);

        form.add(new Label("Age:"), 0, 1);
        form.add(ageField, 1, 1);

        form.add(new Label("Country:"), 0, 2);
        form.add(countryField, 1, 2);

        form.add(addButton, 1, 3);

        VBox root = new VBox(15, form, table);

        Scene scene = new Scene(root, 400, 400);

        primaryStage.setTitle("User Details");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
