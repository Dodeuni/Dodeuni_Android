package dodeunifront.dodeuni.map.api;

import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

import dodeunifront.dodeuni.map.dto.AlertDTO;
import dodeunifront.dodeuni.map.dto.response.ResponseAlertListDTO;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface AlertAPI {
    String URL = "http://172.30.1.6:8080/";

    @GET("api/alarm")
    Call<ResponseAlertListDTO> getAlert(
            @Query("userId") long userId
    );

}
