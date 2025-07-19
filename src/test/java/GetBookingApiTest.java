import org.example.apis.GetBookingApi;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class GetBookingApiTest {

    private GetBookingApi getBookingApi;

//    @BeforeClass
//    public void initApi(){
//        this.getBookingApi = new GetBookingApi();
//    }
    @Parameters("testParam")
    @Test(description = "Basic HTTP Status check for get booking ids API")
    public void validateStatusCodeForGetBookingIdsApi(@Optional String testParam){
        System.out.println("Test param: " + testParam);
        var getBookingIdSResponse = new GetBookingApi().getAllBookingsIds()
                .then().assertThat().statusCode(200);
    }
    @Test(description = "Basic HTTP Status check for get booking by id API")
    public void validateStatusCodeForGetBookingByIdApi(){
        var getBookingIdSResponse = new GetBookingApi().getBookingById(1)
                .then().assertThat().statusCode(200);
    }
}
