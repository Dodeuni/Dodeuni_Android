package dodeunifront.dodeuni.Hue;

public class HueData {
    String ev_huemessage;
    String sendtime;
    int viewType;

    public HueData(String ev_huemessage, String sendtime, int viewType) {
        this.ev_huemessage = ev_huemessage;
        this.sendtime = sendtime;
        this.viewType = viewType;
    }

    public int getViewType() {
        return viewType;
    }

    public void setViewType(int viewType) {
        this.viewType = viewType;
    }

    public String getSendtime() {
        return sendtime;
    }

    public void setSendtime(String sendtime) {
        this.sendtime = sendtime;
    }

    public String getEv_huemessage() {
        return ev_huemessage;
    }

    public void setEv_huemessage(String ev_huemessage) {
        this.ev_huemessage = ev_huemessage;
    }
}