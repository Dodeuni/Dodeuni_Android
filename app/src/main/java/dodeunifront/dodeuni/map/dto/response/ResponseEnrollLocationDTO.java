package dodeunifront.dodeuni.map.dto.response;

import java.util.List;

import dodeunifront.dodeuni.map.dto.ReviewPreviewDTO;

public class ResponseEnrollLocationDTO {
    private int id;

    private String name;

    private String category;

    private String address;

    private String contact;

    private String x;

    private String y;

    private String createdDateTime;

    private String modifiedDateTime;

    private int uid;

    private String nickname;

    private List<ReviewPreviewDTO> reviews;

    public ResponseEnrollLocationDTO(){

    }

    public ResponseEnrollLocationDTO(int id, String name, String category, String address, String contact, String createdDateTime, String modifiedDateTime, String x, String y, int uid, String nickname, List<ReviewPreviewDTO> reviews){
    //public ResponseEnrollLocationDTO(ResponseEnrollLocationDTO response){
        this.id = id;
        this.name = name;
        //this.category = category;
        this.address = address;
        this.contact = contact;
        this.createdDateTime = createdDateTime;
        this.modifiedDateTime = modifiedDateTime;
        this.x = x;
        this.y = y;
        this.uid = uid;
        this.nickname = nickname;
        this.reviews = reviews;
    }

    public int getId(){
        return id;
    }
    public void setId(int id){
        this.id = id;
    }

    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name = name;
    }

    public String getCategory(){
        return category;
    }
    public void setCetegory(String category){ this.category = category;}

    public String getAddress(){
        return address;
    }
    public void setAddress(String address){
        this.address = address;
    }

    public String getContact(){return contact;}
    public void getContact(String contact){this.contact = contact;}

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

    public String getCreatedDateTime() { return createdDateTime;}
    public void setCreatedDateTime(String createdDateTime) {this.createdDateTime = createdDateTime;}

    public String getModifiedDateTime() { return modifiedDateTime;}
    public void setModifiedDateTime(String modifiedDateTime) {this.modifiedDateTime = modifiedDateTime;}

    public int getUid() { return uid;}
    public void setUid(int uid) {this.uid = uid;}

    public String getNickname() { return nickname;}
    public void setNickname(String nickname) {this.nickname = nickname;}

    public List<ReviewPreviewDTO> getReviews() { return reviews;}
    public void setReviews(List<ReviewPreviewDTO> nickname) {this.reviews = reviews;}
}
