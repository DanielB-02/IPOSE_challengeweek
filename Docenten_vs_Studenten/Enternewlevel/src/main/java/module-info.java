module com.example.enternewlevel {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.almasb.fxgl.all;

    opens com.example.enternewlevel to javafx.fxml;
    exports com.example.enternewlevel;
}