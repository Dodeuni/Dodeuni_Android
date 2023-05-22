package dodeunifront.dodeuni.map.dto;

public class KakaoXYDTO {
    private String address_name;

    private String x;

    private String y;

    public KakaoXYDTO(String address_name, String x, String y){
        this.address_name = address_name;
        this.x = x;
        this.y = y;
    }

    public String getAddress(){
        return address_name;
    }
    public void setAddress(String address_name){
        this.address_name = address_name;
    }

    public String getX(){
        return x;
    }
    public void setX(String x){
        this.x = x;
    }

    public String getY(){
        return y;
    }
    public void setY(String y){
        this.y = y;
    }
}
