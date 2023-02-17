package hello.typeconverter.formatter;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.util.Locale;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class MyNumberFormatterTest {

    MyNumberFormatter formatter = new MyNumberFormatter();

    /**
     * 문자 -> 숫자
     * @throws ParseException
     */
    @Test
    void parse() throws ParseException {
        Number result = formatter.parse("10,000", Locale.KOREA);
        assertThat(result).isEqualTo(10000L);
    }

    @Test
    void print() {
        String result = formatter.print(99999, Locale.KOREA);
        assertThat(result).isEqualTo("99,999");
    }
}