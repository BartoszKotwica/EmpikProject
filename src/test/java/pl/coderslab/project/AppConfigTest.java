package pl.coderslab.project;

import org.h2.tools.Server;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestTemplate;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class AppConfigTest {

    @Test
    void restTemplateBeanCreation() {
        AppConfig appConfig = new AppConfig();
        RestTemplate restTemplate = appConfig.restTemplate();
        assertNotNull(restTemplate);
    }

    @Test
    public void h2TCPServerBeanCreation() throws SQLException {
        AppConfig appConfig = new AppConfig();
        Server h2TCPServer = appConfig.h2TCPServer();
        assertNotNull(h2TCPServer);
    }

    @Test
    public void h2TCPServerStartStop() throws SQLException {
        AppConfig appConfig = new AppConfig();
        Server h2TCPServer = appConfig.h2TCPServer();
        h2TCPServer.start();
        assertTrue(h2TCPServer.isRunning(true));
        h2TCPServer.stop();
        assertFalse(h2TCPServer.isRunning(false));
    }
}