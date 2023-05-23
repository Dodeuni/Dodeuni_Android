package dodeunifront.dodeuni.map.dto;

public class AlertDTO {
    private Long id;
    private String main;
    private String sub;
    private String createdDateTime;
    private String alarm;

    public AlertDTO(Long id, String main, String sub, String createdDateTime, String alarm){
        this.id = id;
        this.main = main;
        this.sub = sub;
        this.createdDateTime = createdDateTime;
        this.alarm = alarm;
    }

    public Long getId(){
        return id;
    }
    public void setId(Long id){
        this.id = id;
    }

    public String getMain(){
        return main;
    }
    public void setMain(String main){ this.main = main;}

    public String getSub(){
        return sub;
    }
    public void setSub(String sub){
        this.sub = sub;
    }

    public String getCreatedDateTime(){return createdDateTime;}
    public void setCreatedDateTime(String createdDateTime){this.createdDateTime = createdDateTime;}

    public String getAlarm(){
        return alarm;
    }
    public void setAlarm(String alarm){
        this.alarm = alarm;
    }
}
