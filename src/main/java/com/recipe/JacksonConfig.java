package com.recipe;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.BeanPropertyWriter;
import com.fasterxml.jackson.databind.ser.BeanSerializerModifier;
import com.recipe.entities.Recipe;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;


@Configuration
public class JacksonConfig {

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.setSerializerModifier(new CustomBeanSerializerModifier());
        objectMapper.registerModule(module);
        return objectMapper;
    }

    private static class CustomBeanSerializerModifier extends BeanSerializerModifier {
        @Override
        public List<BeanPropertyWriter> changeProperties(
                SerializationConfig config,
                BeanDescription beanDesc,
                List<BeanPropertyWriter> beanProperties) {

            for (BeanPropertyWriter beanProperty : beanProperties) {
                JsonView annotation = beanProperty.getAnnotation(JsonView.class);
                if (annotation != null && Arrays.asList(annotation.value()).contains(Recipe.NonImage.class)) {
                    if (beanProperty.getName().equals("image")) {
                        try {
                            beanProperty.serializeAsField(null, null, null);
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            }

            return beanProperties;
        }
    }
}