import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.Calendar;
import java.util.GregorianCalendar;

import static com.codeborne.selenide.Selenide.*;

public class Task2CardDeliveryTest {

    CardDeliveryTest dateService = new CardDeliveryTest();

    Calendar calendar = new GregorianCalendar();
    int daysOfMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    int daysOfWeek = calendar.getActualMaximum(Calendar.DAY_OF_WEEK);

    @Test
    public void shouldSendFormNextWeek() {

        String planDate = dateService.planningDate(daysOfWeek, "dd.MM.yyyy");
        String planDay = dateService.planningDate(daysOfWeek, "d");

        int meetingDay = Integer.parseInt(planDay);

        Selenide.open("http://localhost:9999");

        $("[data-test-id='city'] input").setValue("ка");
        $$(".menu-item").find(Condition.text("Йошкар-Ола")).click();
        $(".icon-button .icon_name_calendar").click();
        if (meetingDay > daysOfMonth) {
            $("[role='button'][data-step='1'].calendar__arrow_direction_right").click();
        }
        $$(".calendar__day").find(Condition.text(planDay)).click();
        $("[data-test-id='name'] input").setValue("Иванов Иван");
        $("[data-test-id='phone'] input").setValue("+79991251212");
        $("[data-test-id='agreement']").click();
        $$("button").find(Condition.text("Забронировать")).click();
        $("[data-test-id='notification'] .notification__title").shouldBe(Condition.text("Успешно!"),
                Duration.ofSeconds(15));
        $("[data-test-id='notification'] .notification__content").shouldBe(Condition.text("Встреча успешно " +
                "забронирована на " + planDate));
    }
}
