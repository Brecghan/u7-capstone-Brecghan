package com.nashss.se.trainingmatrix.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class NameConverterTest {

    private NameConverter converter = new NameConverter();
    @Test
    void nameConvert_StringWithSpaces_replacesWithUnderscore() {
        //GIVEN
        String testString = "Name with Spaces Present";
        String convertedString = "Name_with_Spaces_Present";

        //WHEN
        String results = converter.nameConvert(testString);

        //THEN
        assertEquals(results, convertedString);
    }

    @Test
    void nameConvert_StringWithoutSpaces_returnsStringUnchanged() {
        //GIVEN
        String testString = "Name_without_Spaces_Present";

        //WHEN
        String results = converter.nameConvert(testString);

        //THEN
        assertEquals(results, testString);
    }
}