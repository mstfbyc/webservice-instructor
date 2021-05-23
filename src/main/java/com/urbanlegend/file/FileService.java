package com.urbanlegend.file;

import com.urbanlegend.configuration.AppConfiguration;
import com.urbanlegend.user.User;
import org.apache.tika.Tika;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class FileService {
    AppConfiguration appConfiguration;
    FileAttachmentRepository fileAttachmentRepository;
    private Tika tika;

    public FileService(AppConfiguration appConfiguration,FileAttachmentRepository fileAttachmentRepository) {
        this.appConfiguration = appConfiguration;
        this.fileAttachmentRepository = fileAttachmentRepository;
        this.tika = new Tika();
    }

    public String writeBase64EncodedStringToFile(String image) throws IOException {

        String fileName =generateRandomName();
        File target = new File(appConfiguration.getUploadPath()+"/"+fileName);
        OutputStream outputStream = new FileOutputStream(target);
        byte[] base64encoded = Base64.getDecoder().decode(image);

        outputStream.write(base64encoded);
        outputStream.close();
        return fileName;
    }
    public String generateRandomName(){
        return UUID.randomUUID().toString().replaceAll("-","");
    }

    public void deleteProfileImage(String oldImageName) {
        if(oldImageName == null) {
            return;
        }
        deleteFile(Paths.get(appConfiguration.getProfileStoragePath(), oldImageName));
    }

    public void deleteAttachmentFile(String oldImageName) {
        if(oldImageName == null) {
            return;
        }
        deleteFile(Paths.get(appConfiguration.getAttachmentStoragePath(), oldImageName));
    }

    private void deleteFile(Path path) {
        try {
            Files.deleteIfExists(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String detectType(String value) {
        byte[] base64encoded = Base64.getDecoder().decode(value);
        return tika.detect(base64encoded);
    }
    public String detectType(byte[] arr) {
        return tika.detect(arr);
    }

    public FileAttachment saveHoaxAttachment(MultipartFile multipartFile) {
        String fileName = generateRandomName();
        File target = new File(appConfiguration.getAttachmentStoragePath() + "/" + fileName);
        String fileType = null;
        try {
            byte[] arr = multipartFile.getBytes();
            OutputStream outputStream = new FileOutputStream(target);
            outputStream.write(arr);
            outputStream.close();
            fileType = detectType(arr);
        } catch (IOException e) {
            e.printStackTrace();
        }
        FileAttachment attachment = new FileAttachment();
        attachment.setName(fileName);
        attachment.setDate(new Date());
        attachment.setFileType(fileType);
        return fileAttachmentRepository.save(attachment);
    }

    @Scheduled(fixedRate = 24 * 60 * 60 * 1000)
    public void cleanupStorage() {
        Date twentyFourHoursAgo = new Date(System.currentTimeMillis() - (24 * 60 * 60 * 1000));
        List<FileAttachment> filesToBeDeleted = fileAttachmentRepository.findByDateBeforeAndHoaxIsNull(twentyFourHoursAgo);
        for(FileAttachment file : filesToBeDeleted) {
            deleteAttachmentFile(file.getName());
            fileAttachmentRepository.deleteById(file.getId());
        }

    }

    public void deleteAllStoredFilesForUser(User user) {
        deleteProfileImage(user.getImage());
        List<FileAttachment> filesToBeRemoved = fileAttachmentRepository.findByHoaxUser(user);
        for(FileAttachment file: filesToBeRemoved) {
            deleteAttachmentFile(file.getName());
        }
    }
}
