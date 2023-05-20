package dodeunifront.dodeuni.map.dto.response;

import java.util.List;

import dodeunifront.dodeuni.map.dto.AlertDTO;

public class ResponseAlertListDTO {
    private List<AlertDTO> alertList;
    private int length;

    public ResponseAlertListDTO(List<AlertDTO> documents) {
        this.alertList = alertList;
    }

    public List<AlertDTO> getAlertList(){
        return alertList;
    }
    public void setAlertList(List<AlertDTO> alertList){
        this.alertList = alertList;
        length = alertList.size();
    }

    public int getLength(){
        return length;
    }
}