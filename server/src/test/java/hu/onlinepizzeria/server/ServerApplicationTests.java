package hu.onlinepizzeria.server;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class ServerApplicationTests {

    @Test
    void contextLoads() {
        assertEquals(ServerApplication.Home(), "Hello World!");
    }

}
