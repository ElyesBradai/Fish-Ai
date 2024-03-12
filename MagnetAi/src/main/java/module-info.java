module com.example.magnetai {
    requires javafx.controls;
    requires javafx.fxml;



    opens com.example.magnetai to javafx.fxml;
    exports com.example.magnetai;
}