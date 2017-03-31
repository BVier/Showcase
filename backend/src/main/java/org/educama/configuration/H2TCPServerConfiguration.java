
package org.educama.configuration;

import org.h2.tools.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.lang.invoke.MethodHandles;
import java.sql.SQLException;

/**
 * Opens port to database for acceptance-tests.
 */

@Component
@Profile("acceptance_test")
public class H2TCPServerConfiguration {

    private final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private Server tcpServer;
    private Server webServer;

    @PostConstruct
    protected void init() {
        try {
            tcpServer = Server.createTcpServer(new String[] {"-tcpPort", "12345"}).start();
            logger.info("H2 TCP server started. Current state: " + tcpServer.getStatus() + ", url: " + tcpServer.getURL());
            webServer = Server.createWebServer().start();
            logger.info("H2 WEB server started. Current state: " + webServer.getStatus() + ", url: " + webServer.getURL());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @PreDestroy
    protected void shutdown() {
        tcpServer.shutdown();
        logger.info("H2 TCP server stopped. Current state: " + tcpServer.getStatus());
        webServer.shutdown();
        logger.info("H2 WEB server stopped. Current state: " + webServer.getStatus());
    }

}
