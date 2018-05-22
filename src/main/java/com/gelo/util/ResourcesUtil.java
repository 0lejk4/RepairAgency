package com.gelo.util;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * The type Resources util.
 * This class extracts File of resource with given filename.
 */
public class ResourcesUtil {

    /**
     * Gets resource file.
     *
     * @param fileName the file name
     * @return the resource file
     */
    public static File getResourceFile(String fileName) {
        URL url = ResourcesUtil.class.getClassLoader().getResource(fileName);

        File file;
        try {
            file = new File(url.toURI());
        } catch (URISyntaxException e) {
            throw new IllegalArgumentException("Bad path" ,e);
        }

        if (file.exists()) {
            return file;
        } else {
            throw new IllegalArgumentException("Cannot load resource");
        }
    }
}
