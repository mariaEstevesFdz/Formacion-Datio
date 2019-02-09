package org.maria.mx;

import com.fasterxml.jackson.databind.JsonNode;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.web.reactive.server.WebTestClient;

public class IntegrationTest {
	private ConfigurableApplicationContext context;
	private WebTestClient testClient;

	@BeforeAll
	public void setUp() throws Exception {
		this.context = App.app.run("test");
		this.testClient = WebTestClient.bindToServer().baseUrl("http://localhost:8181")
				.build();
	}

	@Test
	public void testHello() throws Exception {
		this.testClient.get().uri("/") //
				.exchange() //
				.expectStatus().isOk() //
				.expectBody(String.class).isEqualTo("Hello World!");
	}

	@Test
	public void testMessage() throws Exception {
		this.testClient.post().uri("/messages") //
				.syncBody(new Message("Hello")).exchange() //
				.expectStatus().isOk() //
				.expectBody(String.class).isEqualTo("{\"text\":\"Hello\"}");

		this.testClient.get().uri("/messages") //
				.exchange() //
				.expectStatus().isOk() //
				.expectBody(String.class).isEqualTo("[{\"text\":\"Hello\"}]");
	}

	@Test
	public void testInvalidMessage() throws Exception {
		this.testClient.post().uri("/messages") //
				.syncBody(new Message("HelloHello")).exchange() //
				.expectStatus().isBadRequest() //
				.expectBody(JsonNode.class)
				.consumeWith(r -> Assertions
						.assertThat(
								r.getResponseBody().get(0).get("defaultMessage").asText())
						.isEqualTo(
								"The size of \"text\" must be less than or equal to 8. The given size is 10"));
	}

	@AfterAll
	public void tearDown() {
		this.context.close();
	}
}
