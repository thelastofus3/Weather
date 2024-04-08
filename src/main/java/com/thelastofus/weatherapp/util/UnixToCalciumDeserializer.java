package com.thelastofus.weatherapp.util;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;

public class UnixToCalciumDeserializer extends JsonDeserializer<Double> {
    @Override
    public Double deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {
        return Double.valueOf(String.format("%.2f",(p.getDoubleValue() - 32) * 5.0 / 9.0).replace(',','.')) ;
    }
}
