package com.nashss.se.trainingmatrix.converters;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.Set;
public class SetConverter implements DynamoDBTypeConverter<String, Set<String>> {
    private static final Gson GSON = new Gson();

    @Override
    public String convert(Set setToBeConverted) {
        return GSON.toJson(setToBeConverted);
    }

    @Override
    public Set<String> unconvert(String dynamoDbRepresentation) {
        // need to provide the type parameter of the list to convert correctly
        return GSON.fromJson(dynamoDbRepresentation, new TypeToken<Set<String>>() { } .getType());
    }
}
