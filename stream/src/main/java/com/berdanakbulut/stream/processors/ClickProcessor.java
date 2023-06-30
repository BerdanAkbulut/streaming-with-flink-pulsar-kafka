package com.berdanakbulut.stream.processors;

import com.berdanakbulut.services.models.ClickEvent;
import org.apache.flink.streaming.api.functions.ProcessFunction;
import org.apache.flink.util.Collector;

public class ClickProcessor extends ProcessFunction<ClickEvent, ClickEvent> {
    @Override
    public void processElement(ClickEvent event, ProcessFunction<ClickEvent, ClickEvent>.Context context, Collector<ClickEvent> collector) throws Exception {
        System.out.println("PROCESSOR WORKS");
        System.out.println("processor event: " + event);
        collector.collect(event);
    }
}
