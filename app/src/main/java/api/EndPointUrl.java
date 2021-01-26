package api;

import com.launguagelearning.model.Lessions;
import com.launguagelearning.model.ResponseData;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Query;

public interface EndPointUrl {

    @GET("/LanguageLearning/user_registration.php")
    Call<ResponseData> registration(@Query("uname") String uname, @Query("pwd") String pwd);

    @GET("/LanguageLearning/user_login.php")
    Call<ResponseData> login(@Query("uname") String uname, @Query("pwd") String pwd);

    @Multipart
    @POST("LanguageLearning/add_category_image.php")
    Call<ResponseData> userRegistration(
            @Part MultipartBody.Part file,
            @PartMap Map<String, String> partMap
    );
    @GET("/LanguageLearning/getAnimalsLessions.php")
    Call<List<Lessions>> getAnimalsLessions();

    @GET("/LanguageLearning/getColorsLessions.php")
    Call<List<Lessions>> getColorsLessions();

    @GET("/LanguageLearning/getShapesLessions.php")
    Call<List<Lessions>> getShapesLessions();
}