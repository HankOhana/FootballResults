package com.hen.data.network

import kotlinx.coroutines.test.runTest
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory

class ApiServiceTest {

    private lateinit var server: MockWebServer
    private lateinit var api: ApiService

    @Before
    fun setUp() {
        server = MockWebServer()
        server.start()

        val json = Json {
            ignoreUnknownKeys = true
        }
        val contentType = "application/json".toMediaType()

        api = Retrofit.Builder()
            .baseUrl(server.url("/"))
            .addConverterFactory(json.asConverterFactory(contentType))
            .build()
            .create(ApiService::class.java)
    }

    @After
    fun tearDown() {
        server.shutdown()
    }

    @Test
    fun `getFixtures hits fixtures endpoint and parses json`() = runTest {
        val body = """
            {
              "data": [
                { "id": 1, "name": "Match A", "result_info": "2-1", "starting_at": "2025-09-21T10:00:00Z" },
                { "id": 2, "name": "Match B", "result_info": "0-0", "starting_at": "2025-09-21T12:00:00Z" }
              ]
            }
        """.trimIndent()

        server.enqueue(
            MockResponse()
                .setResponseCode(200)
                .addHeader("Content-Type", "application/json")
                .setBody(body)
        )

        val response: FixturesResponse = api.getFixtures()

        val request = server.takeRequest()
        assertEquals("/fixtures", request.path)

        assertEquals(2, response.data.size)
        assertEquals("Match A", response.data[0].name)
        assertEquals("2-1", response.data[0].result_info)
        assertEquals("2025-09-21T12:00:00Z", response.data[1].starting_at)
    }

    @Test(expected = HttpException::class)
    fun `getFixtures throws HttpException on non-2xx`() = runTest {
        server.enqueue(MockResponse().setResponseCode(500))
        api.getFixtures()
    }
}