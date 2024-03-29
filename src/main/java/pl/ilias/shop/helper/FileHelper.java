package pl.ilias.shop.helper;

import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

@Component
public class FileHelper {
    public void save(InputStream inputStream, Path path) throws IOException {
        Files.copy(inputStream, path);
    }
}
