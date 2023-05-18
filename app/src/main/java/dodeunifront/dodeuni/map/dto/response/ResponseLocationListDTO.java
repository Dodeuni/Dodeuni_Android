package dodeunifront.dodeuni.map.dto.response;

import java.util.List;

public class ResponseLocationListDTO {
    private List<ResponseLocationDTO> documents;

    public ResponseLocationListDTO(List<ResponseLocationDTO> documents) {
        this.documents = documents;
    }

    public List<ResponseLocationDTO> getDocuments(){
        return documents;
    }
    public void setDocuments(List<ResponseLocationDTO> documents){
        this.documents = documents;
    }

    public int getLength(){
        return documents.size();
    }
}