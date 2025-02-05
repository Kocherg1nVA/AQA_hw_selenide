import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.Test;
import java.time.Duration;
import java.util.Calendar;
import java.util.GregorianCalendar;

import static com.codeborne.selenide.Selenide.*;

public class Task2CardDeliveryTest {

    CardDeliveryTest service = new CardDeliveryTest();

    Calendar calendar = new GregorianCalendar();
    int daysOfMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
//    int currentDay = calendar.get(Calendar.DATE);

//    public int meetingDayService(int days){
//        return currentDay + days;
//    }

    @Test
    public void shouldSendFormThisMonth() {

        String planDate = service.planningDate(7, "dd.MM.yyyy");
        String planDay = service.planningDate(7, "d");

//        int resultDay = meetingDayService(7);
        int resultDay = calendar.get(Calendar.DAY_OF_WEEK);

        Selenide.open("http://localhost:9999");

        $("[data-test-id='city'] input").setValue("ка");
        $$(".menu-item").find(Condition.text("Йошкар-Ола")).click();
        $(".icon-button .icon_name_calendar").click();
        if (resultDay > daysOfMonth) {
            $("[role='button'][data-step='1'].calendar__arrow_direction_right").click();
            $$(".calendar__day").find(Condition.text(planDay)).click();
        } else {
            $$(".calendar__day").find(Condition.text(planDay)).click();
        }
        $("[data-test-id='name'] input").setValue("Иванов Иван");
        $("[data-test-id='phone'] input").setValue("+79991251212");
        $("[data-test-id='agreement']").click();
        $$("button").find(Condition.text("Забронировать")).click();
        $("[data-test-id='notification'] .notification__title").shouldBe(Condition.text("Успешно!"),
                Duration.ofSeconds(15));
        $("[data-test-id='notification'] .notification__content").shouldBe(Condition.text("Встреча успешно " +
                "забронирована на " + planDate));
    }

//    @Test
//    public void shouldSendFormNextMonth() {
//
//        String planDate = service.planningDate(25, "dd.MM.yyyy");
//        String planDay = service.planningDate(25, "d");
//
//        int resultDay = meetingDayService(25);
//
//        Selenide.open("http://localhost:9999");
//
//        $("[data-test-id='city'] input").setValue("ка");
//        $$(".menu-item").find(Condition.text("Йошкар-Ола")).click();
//        $(".icon-button .icon_name_calendar").click();
//        if (resultDay > daysOfMonth) {
//            $("[role='button'][data-step='1'].calendar__arrow_direction_right").click();
//            $$(".calendar__day").find(Condition.text(planDay)).click();
//        } else {
//            $$(".calendar__day").find(Condition.text(planDay)).click();
//        }
//        $("[data-test-id='name'] input").setValue("Иванов Иван");
//        $("[data-test-id='phone'] input").setValue("+79991251212");
//        $("[data-test-id='agreement']").click();
//        $$("button").find(Condition.text("Забронировать")).click();
//        $("[data-test-id='notification'] .notification__title").shouldBe(Condition.text("Успешно!"),
//                Duration.ofSeconds(15));
//        $("[data-test-id='notification'] .notification__content").shouldBe(Condition.text("Встреча успешно " +
//                "забронирована на " + planDate));
//    }
}
