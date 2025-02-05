import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class CardDeliveryTest {

    public String planningDate(int date, String pattern){
        return LocalDate.now().plusDays(date).format(DateTimeFormatter.ofPattern(pattern));
    }

    @Test
    public void shouldSendForm(){
        Selenide.open("http://localhost:9999");

        String date = planningDate(4,"dd.MM.yyyy");

        $("[data-test-id='city'] input").setValue("Йошкар-Ола");
        $("[data-test-id='date'] input").press(Keys.chord(Keys.SHIFT, Keys.HOME),Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(date);
        $("[data-test-id='name'] input").setValue("Иванов Иван");
        $("[data-test-id='phone'] input").setValue("+79991251212");
        $("[data-test-id='agreement']").click();
        $$("button").find(Condition.text("Забронировать")).click();
        $("[data-test-id='notification'] .notification__title").shouldBe(Condition.text("Успешно!"),
                Duration.ofSeconds(15));
        $("[data-test-id='notification'] .notification__content").shouldBe(Condition.text("Встреча успешно " +
                "забронирована на " + date));
    }
}
