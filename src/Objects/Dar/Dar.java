package Objects.Dar;

public class Dar{
    private String id;
    private String data;
    private String login;
    private float kwota;

    public Dar(String id, String data, String login, float kwota){
        setId(id);
        setLogin(login);
        setData(data);
        setKwota(kwota);
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

    public float getKwota() {
        return kwota;
    }

    private void setKwota(float kwota) {
        this.kwota=kwota;
    }
}
