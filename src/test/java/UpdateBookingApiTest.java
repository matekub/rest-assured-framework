import com.github.javafaker.Faker;
import io.restassured.specification.Argument;
import org.example.apis.CreateBookingApi;
import org.example.apis.DeleteBookingApi;
import org.example.apis.UpdateBookingApi;
import org.example.util.ApiRequestHelper;
import org.example.util.TestDataHelper;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.example.util.TestDataHelper.getFutureDate;
import static org.hamcrest.Matchers.*;

public class UpdateBookingApiTest extends BaseTest{



//    @DataProvider(name = "bookingData2")
//    public Object[][] bookingData2() {
//        var faker = TestDataHelper.getFaker();
//        var name = faker.name();
//        var dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//        return IntStream.range(0, 10)
//                .mapToObj(i ->
//
//                        new Object[]{
//
//                                name.firstName(),
//                                name.lastName(),
//                                faker.bool().bool(),
//                                faker.food().dish(),
//                                faker.number().randomNumber(3, true),
//                                getFutureDate(dateFormatter),
//                                getFutureDate(dateFormatter)
//                        })
//                .toArray(Object[][]::new);
//    }
//
//    @DataProvider(name = "bookingDataWithLoop")
//    public Object[][] bookingDataWithLoop() {
//        var faker = TestDataHelper.getFaker();
//        var name = faker.name();
//        var dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//        List<Object[]> list = new ArrayList<>();
//        for (int i = 0; i < 10; i++) {
//            Object[] objects = new Object[]{
//
//                    name.firstName(),
//                    name.lastName(),
//                    faker.bool().bool(),
//                    faker.food().dish(),
//                    faker.number().randomNumber(3, true),
//                    getFutureDate(dateFormatter),
//                    getFutureDate(dateFormatter)
//            };
//            list.add(objects);
//        }
//        return list.toArray(new Object[0][]);
//    }

    @Test(description = "Update a new booking and validate HTTP status code", dataProvider = "bookingData")
    public void updateNewBookingAndValidateStatusCodeTest(String firstName, String lastName, boolean depositPaid,
                                                          String additionalNeeds, Long totalPrice,
                                                          String checkIn, String checkOut) {
        var updateBookingApi = new UpdateBookingApi();
        var createBookingApi = new CreateBookingApi();
        var deleteBookingApi = new DeleteBookingApi();

        var createBookingPayload = ApiRequestHelper.getCreateBookingApiRequestBody(firstName, lastName,
                Math.toIntExact(totalPrice), depositPaid, additionalNeeds, checkIn, checkOut);
        var createBookingApiResponse = createBookingApi.createNewBooking(createBookingPayload)
                .then().assertThat().statusCode(200)
                .and().body("bookingId", is(not(equalTo(0))));

        //var bookingId = createBookingApiResponse.extract().path("bookingId");
        var bookingId = createBookingApiResponse.extract().jsonPath().getInt("bookingid");

        createBookingPayload.replace("lastname", faker.get().name().lastName());
        createBookingPayload.replace("totalprice", faker.get().number().randomNumber(3, true));
        var updateBookingApiResponse = updateBookingApi.updateBooking(createBookingPayload, bookingId,
                        "admin", "password123"
                )
                .then().assertThat().statusCode(200)
                .and().body("bookingId", is(not(equalTo(0))));
        deleteBookingApi.deleteBookingById(
                bookingId, "admin", "password123");
    }

    public static void main(String[] args) {
        var faker = Faker.instance();

        var firstName = faker.name().firstName();
        var lastName = faker.name().lastName();
        var depositPaid = faker.bool().bool();
        var additionalNeeds = faker.food().dish();
        var dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        var futureDate = getFutureDate(DateTimeFormatter.ISO_DATE);
        System.out.println(firstName + " " + lastName + " " + depositPaid + " " + additionalNeeds + " " + futureDate);
    }
}
