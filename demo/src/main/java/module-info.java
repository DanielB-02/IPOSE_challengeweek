module com.example.demo {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.almasb.fxgl.all;
    opens assets.levels;
    opens assets.textures;

    opens com.example.demo to javafx.fxml;
    exports com.example.demo;
}