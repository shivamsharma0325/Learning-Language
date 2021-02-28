package com.launguagelearning.api;



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

public interface ApiService {
    @Multipart
    @POST("LanguageLearning/add_category_image.php")
    Call<ResponseData> userRegistration(
            @Part MultipartBody.Part file,
            @PartMap Map<String, String> partMap

    );

    @Multipart
    @POST("LanguageLearning/edit_category_image.php")
    Call<ResponseData> editLessions(
            @Part MultipartBody.Part file,
            @PartMap Map<String, String> partMap

    );



    @Multipart
    @POST("LanguageLearning/add_model_test.php")
    Call<ResponseData> add_model_test(
            @Part MultipartBody.Part file,
            @PartMap Map<String, String> partMap
    );

    @Multipart
    @POST("LanguageLearning/edit_model_tests.php")
    Call<ResponseData> edit_model_tests(
            @Part MultipartBody.Part file,
            @PartMap Map<String, String> partMap
    );

}
