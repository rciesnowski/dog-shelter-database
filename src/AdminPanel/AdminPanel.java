package AdminPanel;

import Objects.Logowanie.Logowanie;
import Objects.Logowanie.LogowanieController;
import Objects.User.User;
import Objects.User.UserCellController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;

import java.io.IOException;
import java.sql.*;

public class AdminPanel{

    @FXML
    private ListView<Logowanie> loglist;
    @FXML
    private ListView<User> userlist;
    @FXML
    public HBox box;
    @FXML
    public Button addbutton;
    private ObservableList<User> users;
    private ObservableList<Logowanie> logowania;

    public AdminPanel(){
        users=FXCollections.observableArrayList();
        logowania=FXCollections.observableArrayList();
    }

    private void ReloadList() throws ClassNotFoundException, SQLException{
        users.clear();
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        Connection con=DriverManager.getConnection("jdbc:sqlserver://ERPE;database=bazaDanych", "adnub", "admin");
        Statement stm=con.createStatement();
        ResultSet result=stm.executeQuery("select * from Uzytkownik");
        while(result.next()){
            boolean perm;
            perm=result.getString(4).equals("A");
            User newuser=new User(result.getInt(1), result.getString(2), result.getString(3), perm);
            users.add(newuser);
        }
        con.close();
        userlist.setItems(users);
    }
    private void ReloadLogList() throws ClassNotFoundException, SQLException{
        logowania.clear();
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        Connection con=DriverManager.getConnection("jdbc:sqlserver://ERPE;database=bazaDanych", "adnub", "admin");
        Statement stm=con.createStatement();
        ResultSet result=stm.executeQuery("select l.id, u.login, l.data from Logowanie as l inner join Uzytkownik as u on l.id_uzytkownik = u.id");
        while(result.next()){
            Logowanie newlog=new Logowanie(result.getString(1), result.getString(2), result.getString(3));
            logowania.add(newlog);
        }
        con.close();
        loglist.setItems(logowania);
    }

    private void initChanger(char mode, int id){
        box.getChildren().clear();
        TextField log=new TextField();
        log.setStyle("-fx-border-color: green");
        log.setPrefWidth(75);
        log.setPromptText("Login");
        TextField pw=new TextField();
        pw.setStyle("-fx-border-color: green");
        pw.setPromptText("Haslo");
        pw.setPrefWidth(75);
        HBox adm=new HBox();
        Text a=new Text("A");
        CheckBox admin=new CheckBox();
        adm.getChildren().addAll(a, admin);
        adm.setAlignment(Pos.CENTER);
        if(mode=='e'){
            Button zmien=new Button("Zmien");
            zmien.setTextFill(Paint.valueOf("white"));
            zmien.setStyle("-fx-background-color: green");
            zmien.setPrefWidth(75);
            zmien.setCursor(Cursor.HAND);
            box.getChildren().addAll(log, pw, adm, zmien);
            zmien.setOnAction(event -> {
                log.setStyle("-fx-border-color: green");
                pw.setStyle("-fx-border-color: green");
                if(log.getText().length()<3 || log.getText().length()>6){
                    log.setStyle("-fx-border-color: red");
                }
                if(pw.getText().length()<3 || pw.getText().length()>6){
                    pw.setStyle("-fx-border-color: red");
                }
                if(pw.getText().length()>=3 && pw.getText().length()<=6 && log.getText().length()>=3 && log.getText().length()<=6){
                    try{
                        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                        Connection con=DriverManager.getConnection("jdbc:sqlserver://ERPE;database=bazaDanych", "adnub", "admin");
                        PreparedStatement stm=con.prepareStatement("update Uzytkownik set login=?,haslo=?,upowaznienia=? where id=?");
                        stm.setInt(4, id);
                        stm.setString(1, log.getText());
                        stm.setString(2, pw.getText());
                        if(admin.isSelected()){
                            stm.setString(3, "A");
                        }else{
                            stm.setString(3, "U");
                        }
                        stm.executeUpdate();
                        con.close();
                        ReloadList();
                        box.getChildren().clear();
                        box.getChildren().add(addbutton);
                    }catch(SQLException|ClassNotFoundException ignored){
                    }
                }
            });
        }else if(mode=='a'){
            Button add=new Button("Dodaj");
            add.setTextFill(Paint.valueOf("white"));
            add.setStyle("-fx-background-color: green");
            add.setPrefWidth(75);
            add.setCursor(Cursor.HAND);
            box.getChildren().addAll(log, pw, adm, add);
            add.setOnAction(event -> {
                log.setStyle("-fx-border-color: green");
                pw.setStyle("-fx-border-color: green");
                if(log.getText().length()<3 || log.getText().length()>6){
                    log.setStyle("-fx-border-color: red");
                }
                if(pw.getText().length()<3 || pw.getText().length()>6){
                    pw.setStyle("-fx-border-color: red");
                }
                if(pw.getText().length()>=3 && pw.getText().length()<=6 && log.getText().length()>=3 && log.getText().length()<=6){
                    try{
                        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                        Connection con=DriverManager.getConnection("jdbc:sqlserver://ERPE;database=bazaDanych", "adnub", "admin");
                        PreparedStatement stm=con.prepareStatement("insert into Uzytkownik(login, haslo, upowaznienia) values (?,?,?)");
                        stm.setString(1, log.getText());
                        stm.setString(2, pw.getText());
                        if(admin.isSelected()){
                            stm.setString(3, "A");
                        }else{
                            stm.setString(3, "U");
                        }
                        stm.executeUpdate();
                        con.close();
                        ReloadList();
                        box.getChildren().clear();
                        box.getChildren().add(addbutton);
                    }catch(SQLException|ClassNotFoundException ignored){
                    }
                }
            });
        }
    }

    private void Add(){
        initChanger('a', 0);
    }

    public void initialize() throws SQLException, ClassNotFoundException{
        ReloadLogList();
        ReloadList();
        addbutton.setOnAction(event -> Add());
        loglist.setCellFactory(lb -> new ListCell<Logowanie>() {
            private HBox graphic1;
            private LogowanieController controller1;

            {
                try{
                    FXMLLoader loader1=new FXMLLoader(getClass().getResource("/Objects/Logowanie/Logowanie.fxml"));
                    graphic1=loader1.load();
                    controller1=loader1.getController();
                }catch(IOException e){
                    e.printStackTrace();
                }
            }
            @Override
            protected void updateItem(Logowanie log, boolean empty){
                super.updateItem(log, empty);
                if(empty){
                    setGraphic(null);
                }else{
                    controller1.setId(log.getId());
                    controller1.setLogin(log.getLogin());
                    controller1.setData(log.getData());

                    setGraphic(graphic1);
                }
            }
        });
        userlist.setCellFactory(lb -> new ListCell<User>(){
            private HBox graphic;
            private UserCellController controller;

            {
                try{
                    FXMLLoader loader=new FXMLLoader(getClass().getResource("/Objects/User/UserCell.fxml"));
                    graphic=loader.load();
                    controller=loader.getController();
                }catch(IOException e){
                    e.printStackTrace();
                }
            }

            @Override
            protected void updateItem(User user, boolean empty){
                super.updateItem(user, empty);
                if(empty){
                    setGraphic(null);
                }else{
                    controller.setLogin(user.getLogin());
                    controller.setHaslo(user.getHaslo());
                    if(user.isAdmin()){
                        controller.setImage("/img/admin.png");
                    }else{
                        controller.setImage("/img/user.png");
                    }
                    controller.getDelbutton().setOnAction(event -> {
                        try{
                            Delete(user.getId());
                        }catch(SQLException|ClassNotFoundException ignored){
                        }
                    });
                    controller.getEditbutton().setOnAction(event -> Edit(user.getId()));
                    setGraphic(graphic);
                }
            }

            void Delete(int id) throws SQLException, ClassNotFoundException{
                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                Connection con=DriverManager.getConnection("jdbc:sqlserver://ERPE;database=bazaDanych", "adnub", "admin");
                PreparedStatement stm=con.prepareStatement("delete from Uzytkownik where id=?");
                stm.setInt(1, id);
                stm.executeUpdate();
                con.close();
                ReloadList();
            }

            void Edit(int id){
                initChanger('e', id);
            }
        });
    }
}
