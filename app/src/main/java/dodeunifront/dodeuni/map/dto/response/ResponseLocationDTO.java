package dodeunifront.dodeuni.map.dto.response;

public class ResponseLocationDTO {
    private String place_name;

    private String category_group_name;

    private String road_address_name;

    private String phone;

    private String x;

    private String y;
    public ResponseLocationDTO(){

    }

    public ResponseLocationDTO(String place_name, String category_group_name, String road_address_name, String phone, String x, String y){
        this.place_name = place_name;
        this.category_group_name = category_group_name;
        this.road_address_name = road_address_name;
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
    public void setCetegory(String category_group_name){ this.category_group_name = category_group_name;}

    public String getAddress(){
        return road_address_name;
    }
    public void setAddress(String road_address_name){
        this.road_address_name = road_address_name;
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
