package com.budget.budgetapi.util;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

import org.springframework.util.StreamUtils;

public class ResourceUtils {
    
    public static String getContentFromResource(String resourceName) {
        InputStream inputStream = ResourceUtils.class.getResourceAsStream(resourceName);

        try {
            return StreamUtils.copyToString(inputStream, Charset.forName("UTF-8"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
