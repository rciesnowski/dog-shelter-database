package AdminPanel;
import Objects.Logowanie.Logowanie;
import Objects.Logowanie.LogowanieController;
import Objects.Pies.Pies;
import Objects.Pies.PiesCellController;
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
    private ListView<Pies> pieslist;
    @FXML
    public HBox box;
    @FXML
    public  HBox box2;
    @FXML
    public Button addbutton;
    @FXML
    public Button addbutton1;

    private ObservableList<User> users;
    private ObservableList<Logowanie> logowania;
    private ObservableList<Pies> psy;

    public AdminPanel(){
        users=FXCollections.observableArrayList();
        logowania=FXCollections.observableArrayList();
        psy=FXCollections.observableArrayList();
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
        ResultSet result=stm.executeQuery("select l.id, l.data, u.login from Logowanie as l inner join Uzytkownik as u on l.id_uzytkownik = u.id order by 1 desc");
        while(result.next()){
            Logowanie newlog=new Logowanie(result.getString(1), result.getString(2), result.getString(3));
            logowania.add(newlog);
        }
        con.close();
        loglist.setItems(logowania);
    }
    private void ReloadPiesList() throws ClassNotFoundException, SQLException{
        psy.clear();
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        Connection con=DriverManager.getConnection("jdbc:sqlserver://ERPE;database=bazaDanych", "adnub", "admin");
        Statement stm=con.createStatement();
        ResultSet result=stm.executeQuery("select p.id, p.imie, r.nazwa_pl, p.is_lagodny, p.is_wege from Pies as p inner join Rasa as r on p.id_rasa = r.id");
        while(result.next()){
            boolean permL, permW;
            permL=(result.getInt(4)==1);
            permW=(result.getInt(5)==1);
            Pies newpies=new Pies(result.getInt(1), result.getString(2), result.getString(3), permL, permW);
            psy.add(newpies);
        }
        con.close();
        pieslist.setItems(psy);
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
        Text a=new Text("admin");
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
    private void initPiesChanger(char mode, int id){
        box2.getChildren().clear();
        TextField imie=new TextField();
        imie.setStyle("-fx-border-color: green");
        imie.setPrefWidth(75);
        imie.setPromptText("Imie");
        TextField rasa=new TextField();
        rasa.setStyle("-fx-border-color: green");
        rasa.setPromptText("Rasa");
        rasa.setPrefWidth(75);
        HBox wyb=new HBox();
        Text l=new Text("łagodny");
        Text w=new Text("wege");
        CheckBox lag=new CheckBox();
        CheckBox weg=new CheckBox();
        wyb.getChildren().addAll(l, lag, w, weg);
        if(mode=='e'){
            Button zmien2=new Button("Zmien");
            zmien2.setTextFill(Paint.valueOf("white"));
            zmien2.setStyle("-fx-background-color: green");
            zmien2.setPrefWidth(75);
            zmien2.setCursor(Cursor.HAND);
            box2.getChildren().addAll(imie, rasa, wyb, zmien2);
            zmien2.setOnAction(event -> {
                imie.setStyle("-fx-border-color: green");
                rasa.setStyle("-fx-border-color: green");
                if(imie.getText().length()<2 || imie.getText().length()>8){
                    imie.setStyle("-fx-border-color: red");
                }
                if(rasa.getText().length()<3){
                    rasa.setStyle("-fx-border-color: red");
                }
                if(rasa.getText().length()>=3 && imie.getText().length()>=2 && imie.getText().length()<=8){
                    try{
                        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                        Connection con=DriverManager.getConnection("jdbc:sqlserver://ERPE;database=bazaDanych", "adnub", "admin");
                        PreparedStatement stm=con.prepareStatement("update Pies set imie=?,is_wege=?,is_lagodny=? where id=?");
                        stm.setInt(4, id);
                        stm.setString(1, imie.getText());
                        if(weg.isSelected()){
                            stm.setInt(2, 1);
                        }else{
                            stm.setInt(2, 0);
                        }
                        if(lag.isSelected()){
                            stm.setInt(3, 1);
                        }else{
                            stm.setInt(3, 0);
                        }
                        stm.executeUpdate();
                        con.close();
                        ReloadPiesList();
                        box2.getChildren().clear();
                        box2.getChildren().add(addbutton1);
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
            box2.getChildren().addAll(imie, rasa, l, lag, w, weg, add);
            add.setOnAction(event -> {
                imie.setStyle("-fx-border-color: green");
                rasa.setStyle("-fx-border-color: green");
                if(imie.getText().length()<2 || imie.getText().length()>8){
                    imie.setStyle("-fx-border-color: red");
                }
                if(rasa.getText().length()<3){
                    rasa.setStyle("-fx-border-color: red");
                }
                if(rasa.getText().length()>=3 && imie.getText().length()>=2 && imie.getText().length()<=8){
                    try{
                        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                        Connection con=DriverManager.getConnection("jdbc:sqlserver://ERPE;database=bazaDanych", "adnub", "admin");
                        PreparedStatement stm=con.prepareStatement("insert into Pies(imie, is_lagodny, is_wege) values (?,?,?)");
                        stm.setString(1, imie.getText());
                        if(lag.isSelected()){
                            stm.setInt(2, 1);
                        }else{
                            stm.setInt(2, 0);
                        }
                        if(weg.isSelected()){
                            stm.setInt(3, 1);
                        }else{
                            stm.setInt(3, 0);
                        }
                        stm.executeUpdate();
                        con.close();
                        ReloadPiesList();
                        box2.getChildren().clear();
                        box2.getChildren().add(addbutton1);
                    }catch(SQLException|ClassNotFoundException ignored){
                    }
                }
            });
        }
    }

    private void Add(){
        initChanger('a', 0);
    }

    private void AddPies(){
        initPiesChanger('a', 0);
    }

    public void initialize() throws SQLException, ClassNotFoundException{
        ReloadLogList();
        ReloadList();
        ReloadPiesList();
        addbutton1.setOnAction(event -> AddPies());
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
        pieslist.setCellFactory(lb -> new ListCell<Pies>() {
            private HBox graphic2;
            private PiesCellController controller2;

            {
                try{
                    FXMLLoader loader2=new FXMLLoader(getClass().getResource("/Objects/Pies/PiesCell.fxml"));
                    graphic2=loader2.load();
                    controller2=loader2.getController();
                }catch(IOException e){
                    e.printStackTrace();
                }
            }
            @Override
            protected void updateItem(Pies pies, boolean empty){
                super.updateItem(pies, empty);
                if(empty){
                    setGraphic(null);
                }else{
                    controller2.setImie(pies.getImie());
                    controller2.setRasa(pies.getRasa());
                    if(pies.getIs_Lagodny()){
                        controller2.setIs_lagodny("/img/hear.gif");
                    }else{
                        controller2.setIs_lagodny("/img/dang.gif");
                    }
                    if(pies.getIs_wege()){
                        controller2.setIs_wege("/img/broc.gif");
                        System.out.println(pies.getIs_Lagodny());
                    }else{
                        controller2.setIs_wege("/img/meat.gif");
                    }
                    controller2.getDelbutton().setOnAction(event -> {
                        try{
                            Delete(pies.getId());
                        }catch(SQLException|ClassNotFoundException ignored){
                        }
                    });
                    controller2.getEditbutton().setOnAction(event -> Edit(pies.getId()));
                    setGraphic(graphic2);
                }
            }

            void Delete(int id) throws SQLException, ClassNotFoundException{
                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                Connection con=DriverManager.getConnection("jdbc:sqlserver://ERPE;database=bazaDanych", "adnub", "admin");
                PreparedStatement stm=con.prepareStatement("delete from Pies where id=?");
                stm.setInt(1, id);
                stm.executeUpdate();
                con.close();
                ReloadPiesList();
            }

            void Edit(int id){
                initPiesChanger('e', id);
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
                    if(user.isAdmin()) controller.setImage("/img/admin.png");
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
