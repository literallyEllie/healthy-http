package de.elliepotato.hhttp.config;

import com.google.gson.Gson;
import de.elliepotato.hhttp.bootstrap.ServerCustomizer;
import io.javalin.Javalin;
import io.javalin.config.PluginConfig;
import io.javalin.json.JsonMapper;
import io.javalin.plugin.bundled.RouteOverviewPlugin;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Type;

@Singleton
public class DefaultServerCustomizer implements ServerCustomizer {

    private final Gson gson;

    @Inject
    public DefaultServerCustomizer(Gson gson) {
        this.gson = gson;
    }

    @Override
    public void accept(Javalin javalin) {
        javalin.updateConfig(config -> {

            config.jsonMapper(new JsonMapper() {
                @NotNull
                @Override
                public <T> T fromJsonString(@NotNull String json, @NotNull Type targetType) {
                    return gson.fromJson(json, targetType);
                }

                @NotNull
                @Override
                public String toJsonString(@NotNull Object obj, @NotNull Type type) {
                    return gson.toJson(obj, type);
                }
            });

//            PluginConfig pluginConfig = config.plugins;
//            pluginConfig.enableDevLogging();
//            pluginConfig.register(new RouteOverviewPlugin("/"));
        });
    }

}
