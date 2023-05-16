package dodeunifront.dodeuni.community.DTO;

import java.time.LocalDateTime;

public class CommentResponseDTO {
    private Long id;
    private String content;
    private Long step;
    private Long pid;
    private String modifiedDateTime;
    private String createdDateTime;

    private Long cid;
    private Long uid;
    private String nickname;

    public CommentResponseDTO(Long id, String content, Long step, Long pid, String modifiedDateTime, String createdDateTime, Long cid, Long uid, String nickname) {
        this.id = id;
        this.content = content;
        this.step = step;
        this.pid = pid;
        this.modifiedDateTime = modifiedDateTime;
        this.createdDateTime = createdDateTime;
        this.cid = cid;
        this.uid = uid;
        this.nickname = nickname;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getStep() {
        return step;
    }

    public void setStep(Long step) {
        this.step = step;
    }

    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

    public String getModifiedDateTime() {
        return modifiedDateTime;
    }

    public void setModifiedDateTime(String modifiedDateTime) {
        this.modifiedDateTime = modifiedDateTime;
    }

    public String getCreatedDateTime() {
        return createdDateTime;
    }

    public void setCreatedDateTime(String createdDateTime) {
        this.createdDateTime = createdDateTime;
    }

    public Long getCid() {
        return cid;
    }

    public void setCid(Long cid) {
        this.cid = cid;
    }

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}


