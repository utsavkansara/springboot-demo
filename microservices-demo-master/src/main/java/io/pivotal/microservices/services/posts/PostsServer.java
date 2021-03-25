package io.pivotal.microservices.services.posts;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Import;

import io.pivotal.microservices.accounts.AccountsConfiguration;
import io.pivotal.microservices.posts.PostRepository;
import io.pivotal.microservices.posts.PostsConfiguration;
import io.pivotal.microservices.services.registration.RegistrationServer;


/**
 * Run as a micro-service, registering with the Discovery Server (Eureka).
 * <p>
 * Note that the configuration for this application is imported from
 * {@link AccountsConfiguration}. This is a deliberate separation of concerns.
 * <p>
 * This class declares no beans and current package contains no components for
 * ComponentScan to find.
 * 
 * @author Paul Chapman
 */
@SpringBootApplication
@EnableDiscoveryClient
@Import(PostsConfiguration.class)
public class PostsServer {

    @Autowired
    protected PostRepository postsRepository;

    protected Logger logger = Logger.getLogger(PostsServer.class.getName());

    /**
     * Run the application using Spring Boot and an embedded servlet engine.
     * 
     * @param args Program arguments - ignored.
     */
    public static void main(String[] args) {
        // Default to registration server on localhost
        if (System.getProperty(RegistrationServer.REGISTRATION_SERVER_HOSTNAME) == null)
            System.setProperty(RegistrationServer.REGISTRATION_SERVER_HOSTNAME, "localhost");

        // Tell server to look for posts-server.properties or
        // posts-server.yml
        System.setProperty("spring.config.name", "posts-server");

        SpringApplication.run(PostsServer.class, args);
    }
}
