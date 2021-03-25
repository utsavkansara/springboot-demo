package io.pivotal.microservices.posts;

import java.util.logging.Logger;

import javax.sql.DataSource;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;

/**
 * The posts service Spring configuration.
 * 
 * @author Aayushi
 */
@Configuration
@ComponentScan
@EntityScan("io.pivotal.microservices.posts")
@EnableJpaRepositories("io.pivotal.microservices.posts")
@PropertySource("classpath:post-db-config.properties")
public class PostsConfiguration {

	protected Logger logger;

	public PostsConfiguration() {
		logger = Logger.getLogger(getClass().getName());
	}

	/**
	 * Creates an in-memory "rewards" database populated with test data for fast
	 * testing
	 */
	@Bean
	public DataSource dataSource() {
		logger.info("dataSource() invoked");

		// Create an in-memory H2 relational database
		DataSource dataSource = (new EmbeddedDatabaseBuilder()).addScript("classpath:testdb/postschema.sql").build();

		logger.info("dataSource = " + dataSource);

		// Sanity check
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		int postCount = jdbcTemplate.queryForObject("SELECT count(*) from T_POST", Integer.class);
		logger.info("System has " + postCount + " posts");

		return dataSource;
	}
}
