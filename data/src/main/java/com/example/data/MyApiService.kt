package com.example.data

import retrofit2.http.GET
import retrofit2.http.Query


interface MyApiService {
    @GET("apiv2/search/text/")
    suspend fun search(
        @Query("query") query: String,
        @Query("fields") fields: String = "id,name,username,previews,images"
    ): ApiResponse
}
