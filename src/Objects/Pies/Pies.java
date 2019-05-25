package Objects.Pies;

public class Pies{
    private String imie;
    private String rasa;
    private boolean is_lagodny;
    private boolean is_wege;
    private int id;

    public Pies(int id, String imie, String rasa, boolean is_lagodny, boolean is_wege){
        setId(id);
        setImie(imie);
        setRasa(rasa);
        setIs_lagodny(is_lagodny);
        setIs_wege(is_wege);
    }

    public int getId(){
        return id;
    }

    private void setId(int id){
        this.id=id;
    }

    public String getImie(){
        return imie;
    }

    private void setImie(String imie){
        this.imie=imie;
    }

    public String getRasa(){
        return rasa;
    }

    private void setRasa(String rasa){
        this.rasa=rasa;
    }

    public boolean getIs_Lagodny(){
        return is_lagodny;
    }

    private void setIs_lagodny(boolean is_lagodny){
        this.is_lagodny=is_lagodny;
    }

    public boolean getIs_wege() {
        return is_wege;
    }

    private void setIs_wege(boolean is_wege) {
        this.is_wege=is_wege;
    }
}
