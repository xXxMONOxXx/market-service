package edu.azati.marketservice.repository;

import edu.azati.marketservice.exception.FileSystemRepositoryException;
import edu.azati.marketservice.util.Constants;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;

@Log4j2
@Repository
public class FileSystemRepository {

    private final String resourceDir = FileSystemRepository.class.getResource(Constants.IMAGE_RESOURCE_PATH).getPath();

    public String save(byte[] content, String oldFilename) {
        Path newFile = getPath(String.format("%s%s", new Date().getTime(), oldFilename));
        try {
            Files.write(newFile, content);
        } catch (IOException e) {
            log.error("Error during file creation");
            throw new FileSystemRepositoryException("Could not write content to file");
        }
        return newFile.getFileName().toString();
    }

    public File findFile(String filename) {
        try {
            return new FileSystemResource(getPath(filename)).getFile();
        } catch (Exception e) {
            throw new FileSystemRepositoryException(String.format("File with name '%s' was not", filename));
        }
    }

    public void deleteFile(String filename) {
        if (!findFile(filename).delete()) {
            log.info(String.format("Could not delete file with name: %s", filename));
            throw new FileSystemRepositoryException("File in location: '%s', was not deleted");
        }
    }

    private Path getPath(String filename) {
        if (filename == null) {
            return Paths.get(String.format("%s", resourceDir));
        } else {
            return Paths.get(String.format("%s%s", resourceDir, filename));
        }
    }
}
