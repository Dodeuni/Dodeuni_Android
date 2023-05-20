package dodeunifront.dodeuni.map.dto.response;

import java.util.List;

import dodeunifront.dodeuni.map.dto.KakaoLocationDTO;

public class ResponseKakaoLocationListDTO {
    private List<KakaoLocationDTO> documents;

    public ResponseKakaoLocationListDTO(List<KakaoLocationDTO> documents) {
        this.documents = documents;
    }

    public List<KakaoLocationDTO> getDocuments(){
        return documents;
    }
    public void setDocuments(List<KakaoLocationDTO> documents){
        this.documents = documents;
    }

    public int getLength(){
        return documents.size();
    }
}