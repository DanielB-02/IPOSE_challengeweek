module org.hsleiden.challengeweek.docenten_vs_studenten {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.almasb.fxgl.all;
    opens assets.textures;
    opens assets.levels;

    opens org.hsleiden.challengeweek.docenten_vs_studenten to javafx.fxml;
    exports org.hsleiden.challengeweek.docenten_vs_studenten;
    exports org.hsleiden.challengeweek.docenten_vs_studenten.demo;
}