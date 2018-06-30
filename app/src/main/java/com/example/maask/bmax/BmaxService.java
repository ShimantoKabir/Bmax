package com.example.maask.bmax;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by Maask on 3/23/2018.
 */

public interface BmaxService {

    @Headers({
            "Authorization: Bearer a78734f0cad44eca9ebc7270f68ad23f",
            "Content-Type: application/json",
    })
    @POST("/v1/query?v=20150910")
    Call<Response> getResponse(@Body SendBody sendBody);

}
