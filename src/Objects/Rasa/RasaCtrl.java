package Objects.Rasa;

import javafx.fxml.FXML;
import javafx.scene.text.Text;

public class RasaCtrl {
    @FXML
    private Text id;
    @FXML
    private Text pol;
    @FXML
    private Text ang;

    public void setPol(String val){
        pol.setText(val);
    }

    public void setId(String val){
        id.setText(val);
    }

    public void setAng(String val){ang.setText(val);}
}
