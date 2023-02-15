package de.elliepotato.hhttp.factory;

import io.avaje.inject.Bean;
import io.avaje.inject.Factory;
import io.avaje.jsonb.Jsonb;

/**
 * Factory to provide a Jsonb instance.
 * </br>
 * This should not be necessary,
 * however the default injector wasn't working for me :(
 */
@Factory
public class JsonbFactory {

  @Bean
  Jsonb jsonb() {
    return Jsonb.builder().build();
  }

}