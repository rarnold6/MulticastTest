module com.example.multicasttest {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.multicasttest to javafx.fxml;
    exports com.example.multicasttest;
}