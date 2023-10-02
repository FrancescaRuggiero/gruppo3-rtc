package unisannio.assd.gruppo3.RTC.utils;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.kafka.common.serialization.Serializer;
import unisannio.assd.gruppo3.RTC.model.Command;

import java.util.Map;

public class CommandSerializer implements Serializer<Command> {

    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
    }

    @Override
    public byte[] serialize(String topic, Command data) {
        try {
            return objectMapper.writeValueAsBytes(data);
        } catch (JsonProcessingException e) {
            System.err.println("Unable to serialize object");
            return null;
        }
    }

    @Override
    public void close() {
    }
}
