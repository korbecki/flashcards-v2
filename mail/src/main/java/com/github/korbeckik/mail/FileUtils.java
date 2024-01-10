package com.github.korbeckik.mail;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
@Slf4j
public class FileUtils {
    public static String loadFileFromResources(String path) throws URISyntaxException {
        List<String> lines = null;
        File file = getFileFromResource(path);
        try {
            lines = Files.readAllLines(file.toPath(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            log.error(Arrays.toString(e.getStackTrace()));
        }
        assert lines != null;
        return String.join("", lines);
    }
    private File getFileFromResource(String fileName) throws URISyntaxException {

        ClassLoader classLoader = FileUtils.class.getClassLoader();
        URL resource = classLoader.getResource(fileName);
        if (resource == null) {
            throw new IllegalArgumentException("file not found! " + fileName);
        } else {
            return new File(resource.toURI());
        }

    }
}
