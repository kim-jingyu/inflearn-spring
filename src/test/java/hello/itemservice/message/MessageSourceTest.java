package hello.itemservice.message;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;

import java.util.Locale;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
public class MessageSourceTest {

    @Autowired
    MessageSource ms;

    @Test
    void test1() {
        String result = ms.getMessage("button.save", null, null);
        assertThat(result).isEqualTo("저장");
    }

    @Test
    void test2() {
        assertThatThrownBy(() -> ms.getMessage("no_code", null, null))
                .isInstanceOf(NoSuchMessageException.class);
    }

    @Test
    void test3() {
        String noCode = ms.getMessage("no_code", null, "메시지가 없습니다.", null);
        assertThat(noCode).isEqualTo("메시지가 없습니다.");
    }

    @Test
    void test4() {
        String test = ms.getMessage("test.arg", new Object[]{1}, null);
        assertThat(test).isEqualTo("테스트 1");
    }

    @Test
    void test5() {
        String message = ms.getMessage("button.save", null, Locale.ENGLISH);
        assertThat(message).isEqualTo("Save");
    }
}
