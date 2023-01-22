package com.alghoritms;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.Logger;

public class AlghoritmsTest {
	
	private static final Logger LOGGER = (Logger) LoggerFactory.getLogger(AlghoritmsTest.class);
	
	private Algorithms algorithms = new Algorithms();

	@Test
    public void findValue() {
		String message;
		JSONObject json = new JSONObject();
		json.put("property4", "Banana");
		JSONObject item = new JSONObject();
		item.put("property2", "Apple");
		item.put("property3", "Orange");
		json.put("property1", item);
		message = json.toString();
		LOGGER.info(message);
		String path = "property4";
		String value = algorithms.findValue(json, path);
		
        assertNotNull( value );
    }
	
	@Test
    public void findValue2() {
		String message;
		JSONObject json = new JSONObject();
		json.put("property4", "Banana");
		JSONObject item = new JSONObject();
		item.put("property2", "Apple");
		item.put("property3", "Orange");
		json.put("property1", item);
		message = json.toString();
		LOGGER.info(message);
		String path = "property1.property3";
		String value = algorithms.findValue(json, path);
        assertNotNull( value );
    }
	
	@Test
    public void findValueNull() {
		String path = "property1.property3";
		String value = algorithms.findValue(null, path);
        assertNull( value );
    }
	
	@Test
    public void findSmallestMissingNoTest1() {
		int arr[] = { 3, 7, 3, 1, 4 };
        int k = 2;
        int missing = algorithms.findSmallestMissingNo(arr, arr.length, k);
        LOGGER.info("The smallest missing number is [{}]", missing);
        assertNotNull( missing );
        assertEquals(missing, 5);
    }
	
	@Test
    public void findSmallestMissingNoTest2() {
		int arr[] = { 1, 2, 3 };
        int k = 0;
        int missing = algorithms.findSmallestMissingNo(arr, arr.length, k);
        LOGGER.info("The smallest missing number is [{}]", missing);
        assertNotNull( missing );
        assertEquals(missing, 4);
    }
	
	@Test
    public void findSmallestMissingNoTest3() {
		int arr[] = { 5, 1, 5, 2, 4, 8, 5 };
        int k = 3;
        int missing = algorithms.findSmallestMissingNo(arr, arr.length, k);
        LOGGER.info("The smallest missing number is [{}]", missing);
        assertNotNull( missing );
        assertEquals(missing, 6);
    }
	
	@Test
    void findDuplicateTest() {
		int arr[] = { 3, 7, 3, 1, 4 };
        int k = 2;
        int result = algorithms.findDuplicate(arr, arr.length, k);
        LOGGER.info("result: {}", result);
        assertNotNull( result );
    }
}
