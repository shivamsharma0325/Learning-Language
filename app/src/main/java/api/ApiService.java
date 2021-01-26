package api;



import com.launguagelearning.model.ResponseData;

import java.util.Map;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Query;

public interface ApiService {

    @GET("DriverApp/user_registration.php")
    Call<ResponseData> userRegistration(
            @Query("name") String name,
            @Query("email") String email,
            @Query("phonenumber") String phonenumber,
            @Query("pwd") String pwd);


    @GET("/DriverApp/user_login.php?")
    Call<ResponseData> userLogin(
            @Query("uname") String uname,
            @Query("pwd") String pwd,
            @Query("role") String role
    );


    @GET("/DriverApp/forgotpassword.php")
    Call<ResponseData> forgotpassword(@Query("emailid") String emailid);

    @GET("/DriverApp/update_pickup_status.php?")
    Call<ResponseData> updatePickupStatus(@Query("status") String status, @Query("id") String id);

    @Multipart
    @POST("LanguageLearning/add_category_image.php")
    Call<ResponseData> userRegistration(
            @Part MultipartBody.Part file,
            @PartMap Map<String, String> partMap

    );

    @Multipart
    @POST("/DriverApp/user_update_profile.php?")
    Call<ResponseData> user_update_profile(
            @Part MultipartBody.Part file,
            @PartMap Map<String, String> partMap

    );



}
