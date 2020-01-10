package cn.com.gary.database.common.config.db;


import cn.com.gary.util.date.DateUtil;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * LocalDateTime格式化配置
 *
 * @author luxinglin
 * @since 2019-09-20
 */
@Configuration
public class JavaTimeConvertConfig {

    @Value("${local.date-time.format:yyyy-MM-dd HH:mm:ss}")
    private String dateTimeFormat;

    @Value("${local.date.format:yyyy-MM-dd}")
    private String dateFormat;

    @Value("${local.time.format:HH:mm:ss}")
    private String timeFormat;

    @Bean
    public ObjectMapper serializingObjectMapper() {
        JavaTimeModule module = new JavaTimeModule();

        //local-date-time
        module.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer());
        module.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer());

        //local-date
        module.addDeserializer(LocalDate.class, new LocalDateDeserializer());
        module.addSerializer(LocalDate.class, new LocalDateSerializer());

        //local-time
        module.addDeserializer(LocalTime.class, new LocalTimeDeserializer());
        module.addSerializer(LocalTime.class, new LocalTimeSerializer());

        //date
        module.addDeserializer(Date.class, new DateDeserializer());
        module.addSerializer(Date.class, new DateSerializer());

        ObjectMapper objectMapper = Jackson2ObjectMapperBuilder.json()
                .modules(module)
                .featuresToDisable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                .build();
        return objectMapper;

    }

    public class DateSerializer extends JsonSerializer<Date> {
        @Override
        public void serialize(Date value, JsonGenerator gen, SerializerProvider serializers)
                throws IOException {
            gen.writeString(DateUtil.dateToString(value, dateTimeFormat));
        }
    }

    public class DateDeserializer extends JsonDeserializer<Date> {
        @Override
        public Date deserialize(JsonParser p, DeserializationContext deserializationContext)
                throws IOException {
            return DateUtil.stringToDate(p.getValueAsString(), dateTimeFormat);
        }
    }


    public class LocalDateTimeSerializer extends JsonSerializer<LocalDateTime> {
        @Override
        public void serialize(LocalDateTime value, JsonGenerator gen, SerializerProvider serializers)
                throws IOException {
            gen.writeString(value.format(DateTimeFormatter.ofPattern(dateTimeFormat)));
        }
    }

    public class LocalDateTimeDeserializer extends JsonDeserializer<LocalDateTime> {
        @Override
        public LocalDateTime deserialize(JsonParser p, DeserializationContext deserializationContext)
                throws IOException {
            return LocalDateTime.parse(p.getValueAsString(), DateTimeFormatter.ofPattern(dateTimeFormat));
        }
    }

    public class LocalDateDeserializer extends JsonDeserializer<LocalDate> {
        @Override
        public LocalDate deserialize(JsonParser p, DeserializationContext deserializationContext)
                throws IOException {
            return LocalDate.parse(p.getValueAsString(), DateTimeFormatter.ofPattern(dateFormat));
        }
    }


    public class LocalDateSerializer extends JsonSerializer<LocalDate> {
        @Override
        public void serialize(LocalDate value, JsonGenerator gen, SerializerProvider serializers)
                throws IOException {
            gen.writeString(value.format(DateTimeFormatter.ofPattern(dateFormat)));
        }
    }

    public class LocalTimeSerializer extends JsonSerializer<LocalTime> {
        @Override
        public void serialize(LocalTime value, JsonGenerator gen, SerializerProvider serializers)
                throws IOException {
            gen.writeString(value.format(DateTimeFormatter.ofPattern(timeFormat)));
        }
    }

    public class LocalTimeDeserializer extends JsonDeserializer<LocalTime> {
        @Override
        public LocalTime deserialize(JsonParser p, DeserializationContext deserializationContext)
                throws IOException {
            return LocalTime.parse(p.getValueAsString(), DateTimeFormatter.ofPattern(timeFormat));
        }
    }
}