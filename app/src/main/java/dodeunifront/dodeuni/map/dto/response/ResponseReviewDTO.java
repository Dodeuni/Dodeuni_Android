package dodeunifront.dodeuni.map.dto.response;

public class ResponseReviewDTO {
    private int id;

    private String title;

    private String content;

    private String createdDateTime;

    private String modifiedDateTime;

    private int pid;

    private int uid;

    private String nickname;

    public ResponseReviewDTO(){

    }

    public ResponseReviewDTO(int id, String title, String content, String createdDateTime, String modifiedDateTime, int pid, int uid, String nickname){
        this.id = id;
        this.title = title;
        this.content = content;
        this.createdDateTime = createdDateTime;
        this.modifiedDateTime = modifiedDateTime;
        this.pid = pid;
        this.uid = uid;
        this.nickname = nickname;
    }

    public int getId(){
        return id;
    }
    public void setId(int id){
        this.id = id;
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

    public String getCreatedDateTime() { return createdDateTime;}
    public void setCreatedDateTime(String createdDateTime) {this.createdDateTime = createdDateTime;}

    public String getModifiedDateTime() { return modifiedDateTime;}
    public void setModifiedDateTime(String modifiedDateTime) {this.modifiedDateTime = modifiedDateTime;}

    public int getPid() { return pid;}
    public void setPid(int pid) {this.pid = pid;}

    public int getUid() { return uid;}
    public void setUid(int uid) {this.uid = uid;}

    public String getNickname() { return nickname;}
    public void setNickname(String nickname) {this.nickname = nickname;}
}
