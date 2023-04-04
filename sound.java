import edu.govschool.modals.*;
import javafx.scene.media.*;
import java.io.File;

File audioFile = new File("C:\\Path\\To\\Audio\\File.mp3");
        Media audio = new Media(audioFile.toURI().toString());
        MediaPlayer audioPlayer = new MediaPlayer(audio);
        audioPlayer.setAutoPlay(true);