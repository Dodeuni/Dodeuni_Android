package dodeunifront.dodeuni.map.dto;

import java.util.List;

public class ReviewPreviewListDTO {
    private List<ReviewPreviewDTO> previews;

    public ReviewPreviewListDTO(List<ReviewPreviewDTO> previews){
        this.previews = previews;
    }

    public List<ReviewPreviewDTO> getPreviews(){
        return previews;
    }
    public void setPreviews(List<ReviewPreviewDTO> previews){ this.previews = previews; }

    public int getLength(){
        return previews.size();
    }
}
