package dodeunifront.dodeuni.community.DTO;

public class CommentSaveRequestDTO {
    private String content;
    private Long step;
    private Long pid;
    private Long cid;
    private Long uid;

    public CommentSaveRequestDTO(String content, Long step, Long pid, Long cid, Long uid) {
        this.content = content;
        this.step = step;
        this.pid = pid;
        this.cid = cid;
        this.uid = uid;
    }

    public CommentSaveRequestDTO(String content, Long cid, Long uid) {
        this.content = content;
        this.cid = cid;
        this.uid = uid;
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
}
