package dodeunifront.dodeuni.map.dto;

public class KakaoLocationDTO {
    private String place_name;

    private String category_group_name;

    private String address_name;

    private String phone;

    private String x;

    private String y;

    public KakaoLocationDTO(String place_name, String category_group_name, String address_name, String phone, String x, String y){
        this.place_name = place_name;
        this.category_group_name = category_group_name;
        this.address_name = address_name;
        this.phone = phone;
        this.x = x;
        this.y = y;
    }

    public String getPlaceName(){
        return place_name;
    }
    public void setPlaceName(String place_name){
        this.place_name = place_name;
    }

    public String getCategory(){
        return category_group_name;
    }
    public void setCategory(String category_group_name){ this.category_group_name = category_group_name;}

    public String getAddress(){
        return address_name;
    }
    public void setAddress(String address_name){
        this.address_name = address_name;
    }

    public String getPhone(){return phone;}
    public void setPhone(String phone){this.phone = phone;}

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
