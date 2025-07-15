package org.example.config;

import org.aeonbits.owner.Config;


@Config.Sources("classpath:config.properties")
public interface PropertyConfig extends Config {

    @Key("baseUrl")
    String baseUrl();

    @Key("connection.timeout")
    int connectionTimeout();

    @Key("socket.timeout")
    int socketTimeout();
}
