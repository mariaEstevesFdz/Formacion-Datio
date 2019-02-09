package org.maria.mx;

import org.springframework.core.env.Profiles;
import org.springframework.fu.jafu.ApplicationDsl;

public class App {

	static ApplicationDsl app = ApplicationDsl.application(app -> app //
			.beans(beans -> beans //
					.bean(HelloHandler.class) //
					.bean(MessageHandler.class))
			.server(server -> server
					.port(app.env().acceptsProfiles(Profiles.of("test")) ? 8181 : 8080) //
					.router(router -> router //
							.add(app.ref(HelloHandler.class).routes()) //
							.add(app.ref(MessageHandler.class).routes())) //
					.codecs(codecs -> codecs.string().jackson())));

	public static void main(String[] args) {
		app.run(args);
	}
}
