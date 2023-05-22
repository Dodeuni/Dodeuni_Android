package dodeunifront.dodeuni.map.dto.response;

import java.util.List;

import dodeunifront.dodeuni.map.dto.KakaoLocationDTO;
import dodeunifront.dodeuni.map.dto.KakaoXYDTO;

public class ResponseKakaoXYListDTO {
    private List<KakaoXYDTO> documents;

    public ResponseKakaoXYListDTO(List<KakaoXYDTO> documents) {
        this.documents = documents;
    }

    public List<KakaoXYDTO> getDocuments(){
        return documents;
    }
    public void setDocuments(List<KakaoXYDTO> documents){
        this.documents = documents;
    }

    public int getLength(){
        return documents.size();
    }
}