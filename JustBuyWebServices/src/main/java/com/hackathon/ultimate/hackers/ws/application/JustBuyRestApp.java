package com.hackathon.ultimate.hackers.ws.application;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

import com.hackathon.ultimate.hackers.ws.configuration.JustBuyConfiguration;
import com.hackathon.ultimate.hackers.ws.resources.JustBuyResource;

public class JustBuyRestApp extends Application<JustBuyConfiguration> {
	public static void main(String[] args) throws Exception {
		new JustBuyRestApp().run(args);
	}

	@Override
	public String getName() {
		return "hello-world";
	}

	@Override
	public void initialize(Bootstrap<JustBuyConfiguration> bootstrap) {
		// initialize

	}

	@Override
	public void run(JustBuyConfiguration configuration,
			Environment environment) {
		environment.jersey().register(new JustBuyResource());
	}
}

