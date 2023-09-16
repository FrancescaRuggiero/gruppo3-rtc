package utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dto.ConsumptionWarning;
import org.apache.kafka.common.serialization.Serializer;

import java.util.Map;

public class ConsumptionWarningSerializer implements Serializer<ConsumptionWarning> {

    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
    }

    @Override
    public byte[] serialize(String topic, ConsumptionWarning data) {
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