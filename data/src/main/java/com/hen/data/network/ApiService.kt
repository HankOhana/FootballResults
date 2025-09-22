package com.hen.data.network

import retrofit2.http.GET

interface ApiService {
    @GET("fixtures")
    suspend fun getFixtures(): FixturesResponse
}