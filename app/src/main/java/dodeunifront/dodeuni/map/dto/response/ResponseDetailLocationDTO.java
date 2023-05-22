package dodeunifront.dodeuni.map.dto.response;

import java.util.List;

import dodeunifront.dodeuni.map.dto.ReviewPreviewDTO;

public class ResponseDetailLocationDTO {
    private int id;

    private String name;

    private String category;

    private String address;

    private String contact;

    private int uid;

    private List<ResponseReviewDTO> reviews;

    public ResponseDetailLocationDTO(int id, String name, String category, String address, String contact, int uid, List<ResponseReviewDTO> reviews){
        this.id = id;
        this.name = name;
        this.category = category;
        this.address = address;
        this.contact = contact;
        this.uid = uid;
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

    public int getUid() { return uid;}
    public void setUid(int uid) {this.uid = uid;}

    public List<ResponseReviewDTO> getReviews() { return reviews;}
    public void setReviews(List<ResponseReviewDTO> reviews) {this.reviews = reviews;}
    public int getReviewsLength(){
        return reviews.size();
    }
}
