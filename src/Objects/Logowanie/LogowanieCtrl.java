package Objects.Logowanie;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

public class LogowanieCtrl {
    @FXML
    private Text id;
    @FXML
    private Text login;
    @FXML
    private Text data;

    public void setLogin(String val){
        login.setText(val);
    }

    public void setId(String val){
        id.setText(val);
    }

    public void setData(String val){data.setText(val);}
}
