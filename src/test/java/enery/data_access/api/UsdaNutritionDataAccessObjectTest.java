package enery.data_access.api;

import enery.entity.meal.FoodItem;
import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UsdaNutritionDataAccessObjectTest {

    private static final String API_KEY = "test-usda-key";

    @Test
    void constructorRejectsNullClient() {
        final IllegalArgumentException exception =
                assertThrows(
                        IllegalArgumentException.class,
                        () -> new UsdaNutritionDataAccessObject(
                                null,
                                API_KEY
                        )
                );

        assertEquals(
                "HTTP client is required.",
                exception.getMessage()
        );
    }

    @Test
    void constructorRejectsNullApiKey() {
        final OkHttpClient client =
                mock(OkHttpClient.class);

        final IllegalArgumentException exception =
                assertThrows(
                        IllegalArgumentException.class,
                        () -> new UsdaNutritionDataAccessObject(
                                client,
                                null
                        )
                );

        assertEquals(
                "USDA API key is required.",
                exception.getMessage()
        );
    }

    @Test
    void constructorRejectsBlankApiKey() {
        final OkHttpClient client =
                mock(OkHttpClient.class);

        assertThrows(
                IllegalArgumentException.class,
                () -> new UsdaNutritionDataAccessObject(
                        client,
                        "   "
                )
        );
    }

    @Test
    void searchRejectsNullQuery() {
        final OkHttpClient client =
                mock(OkHttpClient.class);

        final UsdaNutritionDataAccessObject dao =
                new UsdaNutritionDataAccessObject(
                        client,
                        API_KEY
                );

        final IllegalArgumentException exception =
                assertThrows(
                        IllegalArgumentException.class,
                        () -> dao.search(null)
                );

        assertEquals(
                "A food query is required.",
                exception.getMessage()
        );
    }

    @Test
    void searchRejectsBlankQuery() {
        final OkHttpClient client =
                mock(OkHttpClient.class);

        final UsdaNutritionDataAccessObject dao =
                new UsdaNutritionDataAccessObject(
                        client,
                        API_KEY
                );

        assertThrows(
                IllegalArgumentException.class,
                () -> dao.search("   ")
        );
    }

    @Test
    void successfulSearchParsesFoodAndNutrients()
            throws IOException {

        final OkHttpClient client =
                mock(OkHttpClient.class);

        final Call call =
                mock(Call.class);

        final String json = """
                {
                  "foods": [
                    {
                      "description": "BANANA, RAW",
                      "foodNutrients": [
                        {
                          "nutrientName": "Energy",
                          "unitName": "KCAL",
                          "value": 89.0
                        },
                        {
                          "nutrientName": "Protein",
                          "unitName": "G",
                          "value": 1.1
                        },
                        {
                          "nutrientName": "Carbohydrate, by difference",
                          "unitName": "G",
                          "value": 22.8
                        },
                        {
                          "nutrientName": "Total lipid (fat)",
                          "unitName": "G",
                          "value": 0.3
                        },
                        {
                          "nutrientName": "Fiber, total dietary",
                          "unitName": "G",
                          "value": 2.6
                        },
                        {
                          "nutrientName": "Sugars, total including NLEA",
                          "unitName": "G",
                          "value": 12.2
                        },
                        {
                          "nutrientName": "Sodium, Na",
                          "unitName": "MG",
                          "value": 1.0
                        }
                      ]
                    }
                  ]
                }
                """;

        when(client.newCall(any(Request.class)))
                .thenReturn(call);

        when(call.execute())
                .thenReturn(
                        createResponse(
                                200,
                                "OK",
                                json
                        )
                );

        final UsdaNutritionDataAccessObject dao =
                new UsdaNutritionDataAccessObject(
                        client,
                        API_KEY
                );

        final List<FoodItem> results =
                dao.search("banana");

        assertEquals(1, results.size());
        assertNotNull(results.get(0));

        verify(client).newCall(any(Request.class));
        verify(call).execute();
    }

    @Test
    void foodWithoutNutrientsStillParses()
            throws IOException {

        final OkHttpClient client =
                mock(OkHttpClient.class);

        final Call call =
                mock(Call.class);

        final String json = """
                {
                  "foods": [
                    {
                      "description": "UNKNOWN FOOD"
                    }
                  ]
                }
                """;

        when(client.newCall(any(Request.class)))
                .thenReturn(call);

        when(call.execute())
                .thenReturn(
                        createResponse(
                                200,
                                "OK",
                                json
                        )
                );

        final UsdaNutritionDataAccessObject dao =
                new UsdaNutritionDataAccessObject(
                        client,
                        API_KEY
                );

        final List<FoodItem> results =
                dao.search("unknown");

        assertEquals(1, results.size());
        assertNotNull(results.get(0));
    }

    @Test
    void missingFoodsArrayReturnsEmptyList()
            throws IOException {

        final OkHttpClient client =
                mock(OkHttpClient.class);

        final Call call =
                mock(Call.class);

        when(client.newCall(any(Request.class)))
                .thenReturn(call);

        when(call.execute())
                .thenReturn(
                        createResponse(
                                200,
                                "OK",
                                "{}"
                        )
                );

        final UsdaNutritionDataAccessObject dao =
                new UsdaNutritionDataAccessObject(
                        client,
                        API_KEY
                );

        final List<FoodItem> results =
                dao.search("nothing");

        assertTrue(results.isEmpty());
    }

    @Test
    void emptyFoodsArrayReturnsEmptyList()
            throws IOException {

        final OkHttpClient client =
                mock(OkHttpClient.class);

        final Call call =
                mock(Call.class);

        when(client.newCall(any(Request.class)))
                .thenReturn(call);

        when(call.execute())
                .thenReturn(
                        createResponse(
                                200,
                                "OK",
                                """
                                {
                                  "foods": []
                                }
                                """
                        )
                );

        final UsdaNutritionDataAccessObject dao =
                new UsdaNutritionDataAccessObject(
                        client,
                        API_KEY
                );

        final List<FoodItem> results =
                dao.search("nothing");

        assertTrue(results.isEmpty());
    }

    @Test
    void irrelevantNutrientsAreIgnored()
            throws IOException {

        final OkHttpClient client =
                mock(OkHttpClient.class);

        final Call call =
                mock(Call.class);

        final String json = """
                {
                  "foods": [
                    {
                      "description": "TEST FOOD",
                      "foodNutrients": [
                        {
                          "nutrientName": "Vitamin C",
                          "unitName": "MG",
                          "value": 20.0
                        },
                        {
                          "nutrientName": "Energy",
                          "unitName": "KJ",
                          "value": 500.0
                        }
                      ]
                    }
                  ]
                }
                """;

        when(client.newCall(any(Request.class)))
                .thenReturn(call);

        when(call.execute())
                .thenReturn(
                        createResponse(
                                200,
                                "OK",
                                json
                        )
                );

        final UsdaNutritionDataAccessObject dao =
                new UsdaNutritionDataAccessObject(
                        client,
                        API_KEY
                );

        final List<FoodItem> results =
                dao.search("test");

        assertEquals(1, results.size());
    }

    @Test
    void unsuccessfulResponseThrowsNutritionApiException()
            throws IOException {

        final OkHttpClient client =
                mock(OkHttpClient.class);

        final Call call =
                mock(Call.class);

        when(client.newCall(any(Request.class)))
                .thenReturn(call);

        when(call.execute())
                .thenReturn(
                        createResponse(
                                500,
                                "Server Error",
                                "{}"
                        )
                );

        final UsdaNutritionDataAccessObject dao =
                new UsdaNutritionDataAccessObject(
                        client,
                        API_KEY
                );

        final NutritionApiException exception =
                assertThrows(
                        NutritionApiException.class,
                        () -> dao.search("banana")
                );

        assertTrue(
                exception.getMessage().contains("500")
        );
    }

    @Test
    void unauthorizedResponseThrowsNutritionApiException()
            throws IOException {

        final OkHttpClient client =
                mock(OkHttpClient.class);

        final Call call =
                mock(Call.class);

        when(client.newCall(any(Request.class)))
                .thenReturn(call);

        when(call.execute())
                .thenReturn(
                        createResponse(
                                401,
                                "Unauthorized",
                                "{}"
                        )
                );

        final UsdaNutritionDataAccessObject dao =
                new UsdaNutritionDataAccessObject(
                        client,
                        API_KEY
                );

        final NutritionApiException exception =
                assertThrows(
                        NutritionApiException.class,
                        () -> dao.search("apple")
                );

        assertTrue(
                exception.getMessage().contains("401")
        );
    }

    @Test
    void invalidJsonThrowsNutritionApiException()
            throws IOException {

        final OkHttpClient client =
                mock(OkHttpClient.class);

        final Call call =
                mock(Call.class);

        when(client.newCall(any(Request.class)))
                .thenReturn(call);

        when(call.execute())
                .thenReturn(
                        createResponse(
                                200,
                                "OK",
                                "not valid json"
                        )
                );

        final UsdaNutritionDataAccessObject dao =
                new UsdaNutritionDataAccessObject(
                        client,
                        API_KEY
                );

        final NutritionApiException exception =
                assertThrows(
                        NutritionApiException.class,
                        () -> dao.search("banana")
                );

        assertEquals(
                "USDA returned invalid JSON.",
                exception.getMessage()
        );
    }

    @Test
    void ioExceptionBecomesNutritionApiException()
            throws IOException {

        final OkHttpClient client =
                mock(OkHttpClient.class);

        final Call call =
                mock(Call.class);

        when(client.newCall(any(Request.class)))
                .thenReturn(call);

        when(call.execute())
                .thenThrow(
                        new IOException(
                                "network unavailable"
                        )
                );

        final UsdaNutritionDataAccessObject dao =
                new UsdaNutritionDataAccessObject(
                        client,
                        API_KEY
                );

        final NutritionApiException exception =
                assertThrows(
                        NutritionApiException.class,
                        () -> dao.search("banana")
                );

        assertEquals(
                "Unable to connect to USDA FoodData Central.",
                exception.getMessage()
        );

        assertInstanceOf(
                IOException.class,
                exception.getCause()
        );
    }

    private static Response createResponse(
            int code,
            String message,
            String body) {

        final Request request =
                new Request.Builder()
                        .url("https://example.com")
                        .build();

        return new Response.Builder()
                .request(request)
                .protocol(Protocol.HTTP_1_1)
                .code(code)
                .message(message)
                .body(
                        ResponseBody.create(
                                body,
                                MediaType.get(
                                        "application/json"
                                )
                        )
                )
                .build();
    }
}
