package com.berdanakbulut.services.serializers;

import com.berdanakbulut.services.models.ClickEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.flink.api.common.serialization.DeserializationSchema;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.api.java.typeutils.TypeExtractor;

import java.io.IOException;

public class ClickEventDeserializationSchema implements DeserializationSchema<ClickEvent> {

    private ObjectMapper mapper = new ObjectMapper();

    @Override
    public ClickEvent deserialize(byte[] bytes) throws IOException {
        return mapper.readValue(bytes, ClickEvent.class);
    }

    @Override
    public boolean isEndOfStream(ClickEvent event) {
        return false;
    }

    @Override
    public TypeInformation<ClickEvent> getProducedType() {
        return TypeExtractor.getForClass(ClickEvent.class);
    }
}
