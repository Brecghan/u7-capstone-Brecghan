package com.nashss.se.trainingmatrix.converters;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.Date;

public class DateConverter implements DynamoDBTypeConverter<String, Date> {
    private static final Gson GSON = new Gson();


    @Override
    public String convert(Date dateToBeConverted) {
        return GSON.toJson(dateToBeConverted);
    }

    @Override
    public Date unconvert(String dynamoDbRepresentation) {
        // need to provide the type parameter of the list to convert correctly
        return GSON.fromJson(dynamoDbRepresentation, new TypeToken<Date>() { } .getType());
    }
}
