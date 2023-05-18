package dodeunifront.dodeuni.map.dto.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RequestEnrollReviewDTO {
    @SerializedName("title")
    @Expose
    private String title;

    @SerializedName("content")
    @Expose
    private String content;

    @SerializedName("pid")
    @Expose
    private int pid;

    @SerializedName("uid")
    @Expose
    private int uid;

    public RequestEnrollReviewDTO(){

    }

    public RequestEnrollReviewDTO(String title, String content, int pid, int uid){
        this.title = title;
        this.content = content;
        this.pid = pid;
        this.uid = uid;
    }

    public String getTitle(){
        return title;
    }
    public void setTitle(String title){
        this.title = title;
    }

    public String getContent(){
        return content;
    }
    public void setContent(String content){
        this.content = content;
    }

    public int getPid() { return pid;}
    public void setPid(int pid) {this.pid = pid;}

    public int getUid() { return uid;}
    public void setUid(int uid) {this.uid = uid;}
}
