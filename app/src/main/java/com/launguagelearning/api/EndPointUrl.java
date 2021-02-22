package com.launguagelearning.api;

import com.launguagelearning.model.EditProfile;
import com.launguagelearning.model.Lessons;
import com.launguagelearning.model.LevelsModel;
import com.launguagelearning.model.ModelTest;
import com.launguagelearning.model.ResponseData;
import com.launguagelearning.model.ResultModel;

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
    Call<ResponseData> registration(@Query("fname") String fname, @Query("lname") String lname, @Query("uname") String uname, @Query("pwd") String pwd);

    @GET("/LanguageLearning/user_login.php")
    Call<ResponseData> login(@Query("uname") String uname, @Query("pwd") String pwd);

    @GET("/LanguageLearning/send_password.php")
    Call<ResponseData> send_password(@Query("uname") String uname);

    @Multipart
    @POST("LanguageLearning/add_category_image.php")
    Call<ResponseData> userRegistration(
            @Part MultipartBody.Part file,
            @PartMap Map<String, String> partMap
    );
    @GET("/LanguageLearning/getAnimalsLessions.php")
    Call<List<Lessons>> getAnimalsLessions();

    @GET("/LanguageLearning/getColorsLessions.php")
    Call<List<Lessons>> getColorsLessions();

    @GET("/LanguageLearning/getShapesLessions.php")
    Call<List<Lessons>> getShapesLessions();

    @GET("/LanguageLearning/get_result_by_category_level.php")
    Call<List<ResultModel>> getResults(@Query("uname") String uname,@Query("ctype") String ctype,@Query("level") String level);

    @GET("/LanguageLearning/getTestQuestion.php")
    Call<List<ModelTest>> getQuestions(@Query("ctype") String ctype, @Query("level") String level);

    @GET("/LanguageLearning/getProfile.php")
    Call<List<EditProfile>> getProfile(@Query("uname") String uname);

    @GET("/LanguageLearning/update_profile.php")
    Call<ResponseData> update_profile(@Query("fname") String fname, @Query("lname") String lname, @Query("uname") String uname, @Query("pwd") String pwd);

    @GET("/LanguageLearning/add_results.php")
    Call<ResponseData> add_results(@Query("uname") String uname, @Query("ctype") String ctype, @Query("level") String level, @Query("correct") String correct, @Query("wrong") String wrong, @Query("total") String total);

    @GET("/LanguageLearning/getLevelsByUname.php")
    Call<List<LevelsModel>> getLevelsByUname(@Query("uname") String uname, @Query("ctype") String ctype);
}