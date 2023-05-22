package dodeunifront.dodeuni.map.dto.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RequestRecommendLocationDTO {

    @SerializedName("x")
    @Expose
    private double x;
    @SerializedName("y")
    @Expose
    private double y;
    @SerializedName("keyword")
    @Expose
    private String keyword;

    public RequestRecommendLocationDTO(){}

    public RequestRecommendLocationDTO(String name, String category, String address, String contact, double x, double y, int uid){
        this.x = x;
        this.y = y;
        this.keyword = keyword;
    }

    public double getX(){
        return x;
    }
    public void setX(double x){
        this.x = x;
    }

    public double getY(){
        return y;
    }
    public void setY(double y){
        this.y = y;
    }

    public String getKeyword() { return keyword;}
    public void setKeyword(String keyword) {this.keyword = keyword;}

}
