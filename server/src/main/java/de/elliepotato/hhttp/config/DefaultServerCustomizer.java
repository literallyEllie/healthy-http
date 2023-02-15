package de.elliepotato.hhttp.config;

import de.elliepotato.hhttp.bootstrap.ServerCustomizer;
import io.avaje.jsonb.JsonType;
import io.avaje.jsonb.Jsonb;
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

    private final Jsonb jsonb;

    @Inject
    public DefaultServerCustomizer(Jsonb jsonb) {
        this.jsonb = jsonb;
    }

    @Override
    public void accept(Javalin javalin) {
        javalin.updateConfig(config -> {

            config.jsonMapper(new JsonMapper() {
                @NotNull
                @Override
                public <T> T fromJsonString(@NotNull String json, @NotNull Type targetType) {
                    JsonType<T> type = jsonb.type(targetType);
                    return type.fromJson(json);
                }

                @NotNull
                @Override
                public String toJsonString(@NotNull Object obj, @NotNull Type type) {
                    return jsonb.type(type).toJson(obj);
                }
            });

//            PluginConfig pluginConfig = config.plugins;
//            pluginConfig.enableDevLogging();
//            pluginConfig.register(new RouteOverviewPlugin("/"));
        });
    }

}
