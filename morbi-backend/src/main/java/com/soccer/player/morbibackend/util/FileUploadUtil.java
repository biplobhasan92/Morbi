package com.soccer.player.morbibackend.util;

import java.io.*;
import java.nio.file.*;

import org.springframework.web.multipart.MultipartFile;

public class FileUploadUtil{

    public static void saveFile(String uploadDir, String fileName,
                                MultipartFile multipartFile) throws IOException {
        Path uploadPath = Paths.get(uploadDir);

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        try (InputStream inputStream = multipartFile.getInputStream()) {
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ioe) {
            throw new IOException("Could not save image file: " + fileName, ioe);
        }
    }



    /*

    public boolean addFile(Issue issue) throws IOException {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        Boolean isAdded = Boolean.FALSE;
        FileLocation fileLocation = new FileLocation();
        List<MultipartFile> files = Arrays.asList(issue.getFiles());
        for (MultipartFile file : files) {
            if (file.isEmpty()) {
                continue;
            }
            byte[] bytes = file.getBytes();
            Path path = Paths.get(filesLocation + timestamp.getTime() + "_" + file.getOriginalFilename());
            Files.write(path, bytes);
            fileLocation.setIssue(issue);
            IssueDetails issueDetails = new IssueDetails();
            issueDetails.setId(0); // issue details will be added after the issue being added. so now, it's 0
            fileLocation.setIssueDetails(issueDetails);
            fileLocation.setFileLocation(path.toString());
            if (fileRepository.add(fileLocation).getId() > 0) {
                log.info("file id: {} added for issue id: {}", fileLocation.getId(), issue.getId());
                isAdded = Boolean.TRUE;
            } else {
                log.info("file didn't added with issue id: {}", issue.getId());
            }
        }
        return isAdded;
    } */
}
