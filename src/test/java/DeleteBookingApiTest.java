import org.example.apis.CreateBookingApi;
import org.example.apis.DeleteBookingApi;
import org.example.util.ApiRequestHelper;
import org.testng.annotations.Test;

import static org.hamcrest.Matchers.*;

public class DeleteBookingApiTest {


    private DeleteBookingApi deleteBookingApi;
    private CreateBookingApi createBookingApi;

    public DeleteBookingApiTest() {
        deleteBookingApi = new DeleteBookingApi();
        createBookingApi = new CreateBookingApi();
    }

    @Test(description = "Delete an existing booking")
    void deleteBookingByIdTest() {

        var createBookingPayload = ApiRequestHelper.getCreateBookingApiRequestBody(
                "Zach", "Newman", 799, false,
                "None", "2020-05-01", "2020-05-05");
        var createBookingApiResponse = createBookingApi.createNewBooking(createBookingPayload)
                .then().assertThat().statusCode(200)
                .and().body("bookingId", is(not(equalTo(0))));

        //var Id = createBookingApiResponse.extract().path("bookingid");
        var bookingId = createBookingApiResponse.extract().jsonPath().getInt("bookingid");

        System.out.println(System.getenv("RESTBOOKER_USERNAME"));
        System.out.println(System.getenv("RESTBOOKER_PASSWORD"));
        var deleteBookingApiResponse = deleteBookingApi.deleteBookingById(
                        bookingId,
   //                     "admin", "password123")
                System.getenv("RESTBOOKER_USERNAME"),
                System.getenv("RESTBOOKER_PASSWORD"))
                .then().assertThat().statusCode(201);
    }

    @Test(description = "Delete a non-existing booking")
    void deleteNonExistingBookingByIdTest() {
        var deleteBookingApiResponse = deleteBookingApi.deleteBookingById(
                        -1, "admin", "password123")
                .then().assertThat().statusCode(405);
    }

    @Test(description = "Delete a booking with invalid credentials")
    void deleteBookingByIdWithInvalidCredentialsTest() {

        var createBookingPayload = ApiRequestHelper.getCreateBookingApiRequestBody(
                "Zach", "Newman", 799, false,
                "None", "2020-05-01", "2020-05-05");
        var createBookingApiResponse = createBookingApi.createNewBooking(createBookingPayload)
                .then().assertThat().statusCode(200)
                .and().body("bookingId", is(not(equalTo(0))));

        //var bookingId = createBookingApiResponse.extract().path("bookingid");
        var bookingId = createBookingApiResponse.extract().jsonPath().getInt("bookingid");
        var deleteBookingApiResponse = deleteBookingApi.deleteBookingById(
                        bookingId, "admin", "password")
                .then().assertThat().statusCode(403);
    }
}
