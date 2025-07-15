import org.example.apis.CreateBookingApi;
import org.example.util.ApiRequestHelper;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.*;

public class CreateBookingApiTest {

    private CreateBookingApi createBookingApi;

    @BeforeClass
    public void initApi() {
        this.createBookingApi = new CreateBookingApi();
    }

    @Test(description = "Create a new booking and validate HTTP status code")
    public void createNewBookingAndValidateStatusCodeTest() {
        var createBookingPayload = ApiRequestHelper.getCreateBookingApiRequestBody(
                "Zach", "Newman", 799, false,
                "None", "2020-05-01", "2020-05-05");
        var createBookingApiResponse = createBookingApi.createNewBooking(createBookingPayload)
                .then().assertThat().statusCode(200)
                .and().body("bookingId", is(not(equalTo(0))));
    }

}
