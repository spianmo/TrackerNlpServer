package com.tracker.nlp.config;

import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.springframework.context.annotation.Configuration;

/**
 * @author Administrator
 */
@Configuration
public class JsonModuleConfig extends SimpleModule {
    public JsonModuleConfig() {
        this.addSerializer(Long.class, ToStringSerializer.instance);
        this.addSerializer(Long.TYPE, ToStringSerializer.instance);
    }
}
