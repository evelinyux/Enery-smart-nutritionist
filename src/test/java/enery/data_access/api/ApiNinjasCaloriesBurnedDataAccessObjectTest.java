package enery.data_access.api;

import enery.entity.exercise.ExerciseSearchResult;
import okhttp3.Call;
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

class ApiNinjasCaloriesBurnedDataAccessObjectTest {

    private static final String API_KEY = "test-api-key";

    @Test
    void constructorRejectsNullClient() {
        final IllegalArgumentException exception =
                assertThrows(
                        IllegalArgumentException.class,
                        () -> new ApiNinjasCaloriesBurnedDataAccessObject(
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
        final OkHttpClient client = mock(OkHttpClient.class);

        final IllegalArgumentException exception =
                assertThrows(
                        IllegalArgumentException.class,
                        () -> new ApiNinjasCaloriesBurnedDataAccessObject(
                                client,
                                null
                        )
                );

        assertEquals(
                "API Ninjas key is required.",
                exception.getMessage()
        );
    }

    @Test
    void constructorRejectsBlankApiKey() {
        final OkHttpClient client = mock(OkHttpClient.class);

        assertThrows(
                IllegalArgumentException.class,
                () -> new ApiNinjasCaloriesBurnedDataAccessObject(
                        client,
                        "   "
                )
        );
    }

    @Test
    void successfulSearchReturnsExerciseResults() throws IOException {
        final OkHttpClient client = mock(OkHttpClient.class);
        final Call call = mock(Call.class);

        final String json = """
                [
                  {
                    "name": "Cycling",
                    "calories_per_hour": 500.0,
                    "duration_minutes": 30,
                    "total_calories": 250.0
                  },
                  {
                    "name": "Running",
                    "calories_per_hour": 700.0,
                    "duration_minutes": 20,
                    "total_calories": 233.3
                  }
                ]
                """;

        final Request originalRequest =
                new Request.Builder()
                        .url("https://example.com")
                        .build();

        final Response response =
                new Response.Builder()
                        .request(originalRequest)
                        .protocol(Protocol.HTTP_1_1)
                        .code(200)
                        .message("OK")
                        .body(ResponseBody.create(
                                json,
                                okhttp3.MediaType.get(
                                        "application/json"
                                )
                        ))
                        .build();

        when(client.newCall(any(Request.class)))
                .thenReturn(call);

        when(call.execute())
                .thenReturn(response);

        final ApiNinjasCaloriesBurnedDataAccessObject dao =
                new ApiNinjasCaloriesBurnedDataAccessObject(
                        client,
                        API_KEY
                );

        final List<ExerciseSearchResult> results =
                dao.search(
                        "cycling",
                        150.0,
                        30
                );

        assertEquals(2, results.size());
        assertNotNull(results.get(0));
        assertNotNull(results.get(1));

        verify(client).newCall(any(Request.class));
        verify(call).execute();
    }

    @Test
    void emptyJsonArrayReturnsEmptyList() throws IOException {
        final OkHttpClient client = mock(OkHttpClient.class);
        final Call call = mock(Call.class);

        final Request request =
                new Request.Builder()
                        .url("https://example.com")
                        .build();

        final Response response =
                new Response.Builder()
                        .request(request)
                        .protocol(Protocol.HTTP_1_1)
                        .code(200)
                        .message("OK")
                        .body(ResponseBody.create(
                                "[]",
                                okhttp3.MediaType.get(
                                        "application/json"
                                )
                        ))
                        .build();

        when(client.newCall(any(Request.class)))
                .thenReturn(call);

        when(call.execute())
                .thenReturn(response);

        final ApiNinjasCaloriesBurnedDataAccessObject dao =
                new ApiNinjasCaloriesBurnedDataAccessObject(
                        client,
                        API_KEY
                );

        final List<ExerciseSearchResult> results =
                dao.search(
                        "walking",
                        160.0,
                        20
                );

        assertTrue(results.isEmpty());
    }

    @Test
    void unsuccessfulResponseThrowsExerciseApiException()
            throws IOException {

        final OkHttpClient client = mock(OkHttpClient.class);
        final Call call = mock(Call.class);

        final Request request =
                new Request.Builder()
                        .url("https://example.com")
                        .build();

        final Response response =
                new Response.Builder()
                        .request(request)
                        .protocol(Protocol.HTTP_1_1)
                        .code(500)
                        .message("Server Error")
                        .body(ResponseBody.create(
                                "",
                                okhttp3.MediaType.get(
                                        "application/json"
                                )
                        ))
                        .build();

        when(client.newCall(any(Request.class)))
                .thenReturn(call);

        when(call.execute())
                .thenReturn(response);

        final ApiNinjasCaloriesBurnedDataAccessObject dao =
                new ApiNinjasCaloriesBurnedDataAccessObject(
                        client,
                        API_KEY
                );

        final ExerciseApiException exception =
                assertThrows(
                        ExerciseApiException.class,
                        () -> dao.search(
                                "running",
                                170.0,
                                30
                        )
                );

        assertTrue(
                exception.getMessage().contains("500")
        );
    }

    @Test
    void ioExceptionBecomesExerciseApiException()
            throws IOException {

        final OkHttpClient client = mock(OkHttpClient.class);
        final Call call = mock(Call.class);

        when(client.newCall(any(Request.class)))
                .thenReturn(call);

        when(call.execute())
                .thenThrow(new IOException(
                        "Connection failed"
                ));

        final ApiNinjasCaloriesBurnedDataAccessObject dao =
                new ApiNinjasCaloriesBurnedDataAccessObject(
                        client,
                        API_KEY
                );

        final ExerciseApiException exception =
                assertThrows(
                        ExerciseApiException.class,
                        () -> dao.search(
                                "cycling",
                                150.0,
                                30
                        )
                );

        assertEquals(
                "Unable to connect to the exercise API.",
                exception.getMessage()
        );

        assertInstanceOf(
                IOException.class,
                exception.getCause()
        );
    }
}
