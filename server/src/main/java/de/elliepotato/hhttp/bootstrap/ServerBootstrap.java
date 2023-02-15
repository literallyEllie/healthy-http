package de.elliepotato.hhttp.bootstrap;

import io.avaje.http.api.WebRoutes;
import io.avaje.inject.BeanScope;
import io.avaje.inject.InjectModule;
import io.javalin.Javalin;

/**
 * Starts the http server.
 */
//@InjectModule(requires = WebRoutes.class)
public final class ServerBootstrap {

    public static void run() {
        String host = System.getProperty("HEALTH_SERVER_HOST", "127.0.0.1");
        int port = Integer.parseInt(System.getProperty("HEALTHY_SERVER_PORT", "8080"));

        BeanScope beanScope = BeanScope.builder().build();

        Javalin javalin = beanScope.get(Javalin.class);

        // Allow Javalin intercept
        beanScope.getOptional(ServerCustomizer.class)
                .ifPresent(serverCustomizer -> serverCustomizer.accept(javalin));

        javalin.start(host, port);
    }

}
