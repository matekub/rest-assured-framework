import com.github.javafaker.Faker;
import org.example.apis.CreateBookingApi;
import org.example.apis.DeleteBookingApi;
import org.example.apis.GetBookingApi;
import org.example.apis.UpdateBookingApi;
import org.example.util.ApiRequestHelper;
import org.hamcrest.Matchers;
import org.testng.annotations.Test;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

public class CRUDTest extends BaseTest {


    @Test(description = "CRUD OPeration on Restful Booker API resource",
            dataProvider = "bookingData")
    public void crudTest(String firstName, String lastName, boolean depositPaid, String additionalNeeds,
                         Long totalPrice, String checkIn, String checkOut) {
        var updateBookingApi = new UpdateBookingApi();
        var createBookingApi = new CreateBookingApi();
        var deleteBookingApi = new DeleteBookingApi();
        var getBookingApi = new GetBookingApi();



        //Create new Booking
        var createBookingPayload = ApiRequestHelper.getCreateBookingApiRequestBody(firstName, lastName,
                Math.toIntExact(totalPrice), depositPaid, additionalNeeds, checkIn, checkOut);
        var createBookingApiResponse = createBookingApi.createNewBooking(createBookingPayload)
                .then().assertThat().statusCode(200)
                .and().body("bookingId", is(not(equalTo(0))));

        //var bookingId = createBookingApiResponse.extract().path("bookingId");
        var bookingId = createBookingApiResponse.extract().jsonPath().getInt("bookingid");

        //Retrieve created booking using booking ID
        validateRetrievedBookingDateFromGetApi(firstName, lastName, depositPaid, additionalNeeds,
                totalPrice, checkIn, checkOut, getBookingApi, bookingId);

        //update the booking using PUT Api
        var updatedLastName = faker.get().name().lastName();
        createBookingPayload.replace("lastname", updatedLastName);
        var updatedTotalPrice = Math.toIntExact(faker.get().number().randomNumber(3, true));
        createBookingPayload.replace("totalprice", updatedTotalPrice);
        var updateBookingApiResponse = updateBookingApi.updateBooking(createBookingPayload, bookingId,
                        "admin", "password123"
                )
                .then().assertThat().statusCode(200)
                .and().body("bookingId", is(not(equalTo(0))))
                        .and().body("lastname", is(equalTo(updatedLastName)))
                        .and().body("totalprice", is(equalTo(updatedTotalPrice)));


        deleteBookingApi.deleteBookingById(
                bookingId, "admin", "password123");
        getBookingApi.getBookingById(bookingId).then().assertThat().statusCode(404);
    }

    private static void validateRetrievedBookingDateFromGetApi(String firstName, String lastName, boolean depositPaid, String additionalNeeds, Long totalPrice, String checkIn, String checkOut, GetBookingApi getBookingApi, int bookingId) {
        var getBookingIdSResponse = getBookingApi.getBookingById(bookingId)
                .then().assertThat().statusCode(200)
                .and().body("firstname", Matchers.is(equalTo(firstName)))
                .and().body("lastname", Matchers.is(equalTo(lastName)))
                .and().body("totalprice", Matchers.is(equalTo(totalPrice.intValue())))
                .and().body("depositpaid", Matchers.is(equalTo(depositPaid)))
                .and().body("additionalneeds", Matchers.is(equalTo(additionalNeeds)))
                .and().root("bookingdates")
                .and().body("checkin", Matchers.is(equalTo(checkIn)))
                .and().body("checkout", Matchers.is(equalTo(checkOut)))
                .and().detachRootPath("bookingdates");
    }
}