import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import static com.codeborne.selenide.Selenide.*;

public class NegativeCardDeliveryTest {

    CardDeliveryTest service = new CardDeliveryTest();

    String date = service.planningDate(4, "dd.MM.yyyy");

    @BeforeEach
    public void setUp(){
        Selenide.open("http://localhost:9999");
    }

    @Test
    public void wrongCityName(){
        $("[placeholder='Город']").setValue("Moscow");
        $("[placeholder='Дата встречи']").press(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE)
                .setValue(date);
        $("[name='name']").setValue("Петров Петр");
        $("[name='phone']").setValue("+79991234578");
        $(".checkbox__box").click();
        $$("button").find(Condition.text("Забронировать")).click();
        String actual = $("[data-test-id='city'].input_invalid").getText().trim();
        Assertions.assertEquals("Доставка в выбранный город недоступна", actual);
    }

    @Test
    public void emptyCityNameField(){
        $("[placeholder='Город']").setValue("");
        $("[placeholder='Дата встречи']").press(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE)
                .setValue(date);
        $("[name='name']").setValue("Петров Петр");
        $("[name='phone']").setValue("+79991234578");
        $(".checkbox__box").click();
        $$("button").find(Condition.text("Забронировать")).click();
        String actual = $("[data-test-id='city'].input_invalid").getText().trim();
        Assertions.assertEquals("Поле обязательно для заполнения", actual);
    }

    @Test
    public void WrongData(){
        $("[data-test-id='city'] input").setValue("Йошкар-Ола");
        $("[data-test-id='date'] input").press(Keys.chord(Keys.SHIFT, Keys.HOME),Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue("03.03.2021");
        $("[data-test-id='name'] input").setValue("Иванов Иван");
        $("[data-test-id='phone'] input").setValue("+79991251212");
        $("[data-test-id='agreement']").click();
        $$("button").find(Condition.text("Забронировать")).click();
        String actual = $("[data-test-id='date'] .input_invalid").getText().trim();
        Assertions.assertEquals("Заказ на выбранную дату невозможен", actual);
    }

    @Test
    public void emptyDataField(){
        $("[data-test-id='city'] input").setValue("Йошкар-Ола");
        $("[data-test-id='date'] input").press(Keys.chord(Keys.SHIFT, Keys.HOME),Keys.BACK_SPACE);
        $("[data-test-id='name'] input").setValue("Иванов Иван");
        $("[data-test-id='phone'] input").setValue("+79991251212");
        $("[data-test-id='agreement']").click();
        $$("button").find(Condition.text("Забронировать")).click();
        String actual = $("[data-test-id='date'] .input_invalid").getText().trim();
        Assertions.assertEquals("Неверно введена дата", actual);
    }

    @Test
    public void wrongNameField(){
        $("[data-test-id='city'] input").setValue("Йошкар-Ола");
        $("[data-test-id='date'] input").press(Keys.chord(Keys.SHIFT, Keys.HOME),Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(date);
        $("[data-test-id='name'] input").setValue("Ivanov");
        $("[data-test-id='phone'] input").setValue("+79991251212");
        $("[data-test-id='agreement']").click();
        $$("button").find(Condition.text("Забронировать")).click();
        String actual = $("[data-test-id='name'].input_invalid .input__sub").getText().trim();
        Assertions.assertEquals("Имя и Фамилия указаные неверно. Допустимы только русские буквы," +
                " пробелы и дефисы.", actual);
    }

    @Test
    public void emptyNameField(){
        $("[data-test-id='city'] input").setValue("Йошкар-Ола");
        $("[data-test-id='date'] input").press(Keys.chord(Keys.SHIFT, Keys.HOME),Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(date);
        $("[data-test-id='name'] input").setValue("");
        $("[data-test-id='phone'] input").setValue("+79991251212");
        $("[data-test-id='agreement']").click();
        $$("button").find(Condition.text("Забронировать")).click();
        String actual = $("[data-test-id='name'].input_invalid .input__sub").getText().trim();
        Assertions.assertEquals("Поле обязательно для заполнения", actual);
    }

    @Test
    public void wrongPhoneField(){
        $x("//input[@placeholder='Город']").setValue("Йошкар-Ола");
        $x("//input[@placeholder='Дата встречи']").press(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE)
                .setValue(date);
        $x("//input[@name='name']").setValue("Иванов Иван");
        $x("//input[@name='phone']").setValue("123");
        $x("//label[@data-test-id]").click();
        $x("//span[text()='Забронировать']").click();
        String actual = $x("//span[@class='input__sub'] [contains(text(), 'неверно')]").getText().trim();
        Assertions.assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", actual);
    }

    @Test
    public void emptyPhoneField(){
        $x("//input[@placeholder='Город']").setValue("Йошкар-Ола");
        $x("//input[@placeholder='Дата встречи']").press(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE)
                .setValue(date);
        $x("//input[@name='name']").setValue("Иванов Иван");
        $x("//input[@name='phone']").setValue("");
        $x("//label[@data-test-id]").click();
        $x("//span[text()='Забронировать']").click();
        String actual = $x("//span[@class='input__sub'] [text()='Поле обязательно для заполнения']")
                .should(Condition.visible).getText().trim();
        Assertions.assertEquals("Поле обязательно для заполнения", actual);
    }

    @Test
    public void emptyAgreementCheckBox(){
        $x("//input[@placeholder='Город']").setValue("Йошкар-Ола");
        $x("//input[@placeholder='Дата встречи']").press(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE)
                .setValue(date);
        $x("//input[@name='name']").setValue("Иванов Иван");
        $x("//input[@name='phone']").setValue("+79991235689");
        $x("//span[text()='Забронировать']").click();
        String actual = $x("//span[contains(text(), 'соглашаюсь')]")
                .should(Condition.visible).getText().trim();
        Assertions.assertEquals("Я соглашаюсь с условиями обработки и использования моих персональных данных", actual);
    }
}
