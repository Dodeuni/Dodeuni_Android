package dodeunifront.dodeuni.map.dto.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RequestEnrollLocationDTO {
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("category")
    @Expose
    private String category;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("contact")
    @Expose
    private String contact;
    @SerializedName("x")
    @Expose
    private String x;
    @SerializedName("y")
    @Expose
    private String y;
    @SerializedName("uid")
    @Expose
    private Long uid;

    public RequestEnrollLocationDTO(){}

    public RequestEnrollLocationDTO(String name, String category, String address, String contact, String x, String y, Long uid){
        this.name = name;
        this.category = category;
        this.address = address;
        this.contact = contact;
        this.x = x;
        this.y = y;
        this.uid = uid;
    }

    public String getPlaceName(){
        return name;
    }
    public void setPlaceName(String name){
        this.name = name;
    }

    public String getCategory(){
        return category;
    }
    public void setCategory(String category){ this.category = category;}

    public String getAddress(){
        return address;
    }
    public void setAddress(String address){
        this.address = address;
    }

    public String getPhone(){return contact;}
    public void setPhone(String phone){this.contact = phone;}

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

    public Long getUid() { return uid;}
    public void setUid(Long uid) {this.uid = uid;}

}
