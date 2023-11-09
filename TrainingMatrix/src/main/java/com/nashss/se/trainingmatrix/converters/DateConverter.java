package com.nashss.se.trainingmatrix.converters;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.time.ZonedDateTime;

public class DateConverter implements DynamoDBTypeConverter<String, ZonedDateTime> {
    private static final Gson GSON = new Gson();


    @Override
    public String convert(ZonedDateTime dateToBeConverted) {
        String dateString = dateToBeConverted.toString();
        return GSON.toJson(dateString);
    }

    @Override
    public ZonedDateTime unconvert(String dynamoDbRepresentation) {
        // need to provide the type parameter of the list to convert correctly
        String dateString = GSON.fromJson(dynamoDbRepresentation, new TypeToken<String>() { } .getType());
        return ZonedDateTime.parse(dateString);
    }
}
