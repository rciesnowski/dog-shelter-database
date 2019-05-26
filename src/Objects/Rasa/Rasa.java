package Objects.Rasa;

public class Rasa{
    private String id;
    private String nazwa_pol;
    private String nazwa_ang;

    public Rasa(String id, String nazwa_pol, String nazwa_ang){
        setId(id);
        setPol(nazwa_pol);
        setAng(nazwa_ang);
    }

    public String getId(){
        return id;
    }

    private void setId(String id){
        this.id=id;
    }

    public String getPol(){
        return nazwa_pol;
    }

    private void setPol(String pol){
        this.nazwa_pol=pol;
    }

    public String getAng(){
        return nazwa_ang;
    }

    private void setAng(String ang){
        this.nazwa_ang=ang;
    }
}
