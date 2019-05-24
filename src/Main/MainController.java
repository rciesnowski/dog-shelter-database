package Main;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;

public class MainController{
    @FXML
    private StackPane content;
    @FXML
    private AnchorPane loginpane;
    @FXML
    private TextField usr;
    @FXML
    private PasswordField pwd;
    @FXML
    private Text info;
    @FXML
    private Button logout;

    @FXML
    public void Close(ActionEvent event){
        Node src=(Node) event.getSource();
        Stage current=(Stage) src.getScene().getWindow();
        current.close();
    }

    @FXML
    void Login() throws SQLException, ClassNotFoundException, IOException{
        try{
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Connection con=DriverManager.getConnection("jdbc:sqlserver://ERPE;database=bazaDanych", "adnub", "admin");
            PreparedStatement stm=con.prepareStatement("insert into Logowanie(id_uzytkownik) select id from Uzytkownik where login = ?");
            stm.setString(1, usr.getText());
            stm.executeUpdate();
            con.close();
        }catch(SQLException|ClassNotFoundException ignored){
        }
        if(Validate(usr.getText(),pwd.getText())=='A'){
            logout.setVisible(true);
            AnchorPane adminpanel = FXMLLoader.load(getClass().getResource("/AdminPanel/AdminPanel.fxml"));
            content.getChildren().clear();
            content.getChildren().add(adminpanel);
        }
        if(Validate(usr.getText(),pwd.getText())=='U'){
            logout.setVisible(true);
            AnchorPane userpanel = FXMLLoader.load(getClass().getResource("/UserPanel/UserPanel.fxml"));
            content.getChildren().clear();
            content.getChildren().add(userpanel);
        }
    }

    private char Validate(String usr, String pwd) throws ClassNotFoundException, SQLException{
        info.setText("");
        if(usr.length()>=3 && usr.length()<=6 && pwd.length()>=3 && pwd.length()<=6){
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Connection con=DriverManager.getConnection("jdbc:sqlserver://ERPE;database=bazaDanych", "adnub", "admin");
            PreparedStatement stm=con.prepareStatement("select upowaznienia from Uzytkownik where login = ? and haslo = ?");
            stm.setString(1,usr);
            stm.setString(2,pwd);
            ResultSet result = stm.executeQuery();
            if(result.next()){
                return result.getString("upowaznienia").charAt(0);
            }
            else{
                info.setText("Brak uzytkownika.");
            }
            con.close();
        }
        else{
            info.setText("Nieprawidlowe dane.");
        }
        return 'F';
    }

    @FXML
    void Logout(){
        logout.setVisible(false);
        usr.setText("");
        pwd.setText("");
        content.getChildren().clear();
        content.getChildren().add(loginpane);
    }
}
