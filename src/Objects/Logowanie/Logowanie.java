package Objects.Logowanie;

public class Logowanie{
    private String id;
    private String login;
    private String data;

    public Logowanie(String id, String login, String data){
        setId(id);
        setLogin(login);
        setData(data);
    }

    public String getId(){
        return id;
    }

    private void setId(String id){
        this.id=id;
    }

    public String getLogin(){
        return login;
    }

    private void setLogin(String login){
        this.login=login;
    }

    public String getData(){
        return data;
    }

    private void setData(String data){
        this.data=data;
    }
}
