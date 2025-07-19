import com.github.javafaker.Faker;
import org.testng.annotations.DataProvider;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.stream.IntStream;

import static org.example.util.TestDataHelper.getFutureDate;

public class BaseTest {

    protected final ThreadLocal<Faker> faker = ThreadLocal.withInitial(Faker::new);

    @DataProvider(name = "bookingData", parallel = true)
    public Object[][] bookingData() {
        var dateFormatter = DateTimeFormatter.ISO_DATE;
        return IntStream.range(0, 5)
                .mapToObj(i -> {
                    // tworzenie nowej instancji faker dla każdego zestawu danych

                    var checkInDate = getFutureDate(dateFormatter);
                    var checkOutDate = LocalDate.parse(checkInDate, dateFormatter)
                            .plusDays(faker.get().number().numberBetween(1, 14))
                            .format(dateFormatter);
                    return new Object[]{
                            faker.get().name().firstName(),
                            faker.get().name().lastName(),
                            faker.get().bool().bool(),
                            faker.get().food().dish(),
                            faker.get().number().randomNumber(3, true),
                            checkInDate,
                            checkOutDate
                    };
                })
                .toArray(Object[][]::new);
    }

}
