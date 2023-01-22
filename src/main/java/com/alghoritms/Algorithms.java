package com.alghoritms;

import java.util.Arrays;

import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.Logger;

public class Algorithms {

	private static final Logger LOGGER = (Logger)LoggerFactory.getLogger(Algorithms.class);
	
	public String findValue(JSONObject jsonObj, String path) throws JSONException{
		LOGGER.info("finding value for path [{}] ", path);
		String value = "";
		if (jsonObj == null) {
            return null;
        }
		String[] paths = path.split("\\.");
		if(paths.length == 1) {
			return jsonObj.getString(paths[0]);
		}
		value = findValue(jsonObj.getJSONObject(paths[0]), Arrays.copyOfRange(paths, 1, paths.length)[0]);
		LOGGER.info("Value for given path is: [{}] ", value);
	    return value;
	}
	
	public int findSmallestMissingNo(int []arr, int n, int k){
        int currval;
        int nextval;
        int result;

        int duplicates = findDuplicate(arr, n, k);
     
        for (int i = 0; i < n; i++) {
            if (arr[i] <= 0 || arr[i] > n)
                continue;
            currval = arr[i];
            while (arr[currval - 1] != currval) {
                nextval = arr[currval - 1];
                arr[currval - 1] = currval;
                currval = nextval;
                if (currval <= 0 || currval > n)
                    break;
            }
        }
        for (int i = 0; i < n; i++) {
            if (arr[i] != i + 1) {
            	if(duplicates > i + 1) {
            		arr[i] = i + 1;
            		result = findSmallestMissingNo(arr, arr.length, k);
            	} else {
            		result = i + 1;
            	}
                return result;
            }
        }
        result = n + 1;
        return result;
    }
	
	public int findDuplicate(int arr[], int n, int k) {
        Arrays.sort(arr);
        int result = 0;
        int i = 0;
        while (i < n) {
            int j, count = 1;
            for (j = i + 1; j < n && arr[j] == arr[i]; j++) {
                count++;
            }
            if (count == k) {
            	result = arr[i];
                return result;
            }
            i = j;
        }
        return -1;
    }
     
}
