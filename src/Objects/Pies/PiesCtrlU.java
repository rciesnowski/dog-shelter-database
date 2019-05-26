package Objects.Pies;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

public class PiesCtrlU {
    @FXML
    private Text imie;
    @FXML
    private Text rasa;
    @FXML
    private ImageView is_lagodny;
    @FXML
    private ImageView is_wege;

    public void setImie(String val){
        imie.setText(val);
    }

    public void setRasa(String val){
        rasa.setText(val);
    }

    public void setIs_lagodny(String val){
        is_lagodny.setImage(new Image(val));
    }

    public void setIs_wege(String val){
        is_wege.setImage(new Image(val));
    }
}
