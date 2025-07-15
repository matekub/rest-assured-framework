import org.awaitility.Awaitility;
import org.example.apis.GetBookingApi;
import org.testng.annotations.Test;

import java.time.Duration;

public class AwaitilityTest {


    @Test
    public void waitUntilAsserted() {
        var getBookingApi = new GetBookingApi();

        Awaitility.await()
                .and().with().alias("My custom message")
                .and().with().timeout(Duration.ofSeconds(5))
                .then().untilAsserted(() -> {
                            getBookingApi.getBookingById(2)
                                    .then().assertThat().statusCode(400);
                        }
                );
    }

    @Test
    public void waitUntil() {
        var getBookingApi = new GetBookingApi();

        Awaitility.await()
                .and().with().alias("My custom message")
                .and().with().timeout(Duration.ofSeconds(5))
                .then().until(() -> {
                            var statusCode = getBookingApi.getBookingById(2).statusCode();
                            return statusCode == 400;
                        }
                );
    }

    @Test
    public void waitUntilAndIgnoreAllExceptions() {
        var getBookingApi = new GetBookingApi();

        Awaitility.await()
                .and().with().alias("My custom message")
                .and().with().timeout(Duration.ofSeconds(5))
                .and().ignoreExceptions()
                .then().until(() -> {
                            getBookingApi.getBookingById(2)
                                    .then().assertThat().statusCode(400);
                            return true;
                        }
                );
    }

    @Test
    public void waitUntilAndIgnoreSpecificException() {
        var getBookingApi = new GetBookingApi();

        Awaitility.await()
                .and().with().alias("My custom message")
                .and().with().timeout(Duration.ofSeconds(5))
                .and().ignoreExceptionsInstanceOf(AssertionError.class)
                .then().until(() -> {
                            getBookingApi.getBookingById(2)
                                    .then().assertThat().statusCode(400);
                            return true;
                        }
                );
    }
    @Test
    public void definePollingDelay() {
        var getBookingApi = new GetBookingApi();

        Awaitility.await()
                .and().with().alias("My custom message")
                .and().with().timeout(Duration.ofSeconds(5))
                .and().ignoreExceptionsInstanceOf(AssertionError.class)
                .and().pollDelay(Duration.ofSeconds(1))
                .then().until(() -> {
                            getBookingApi.getBookingById(2)
                                    .then().assertThat().statusCode(400);
                            return true;
                        }
                );
    }
}
