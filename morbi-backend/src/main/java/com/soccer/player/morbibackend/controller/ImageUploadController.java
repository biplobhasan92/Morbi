package com.soccer.player.morbibackend.controller;

import com.soccer.player.morbibackend.model.ImageModel;
import com.soccer.player.morbibackend.model.User;
import com.soccer.player.morbibackend.payload.request.UserUpdate;
import com.soccer.player.morbibackend.payload.response.MessageResponse;
import com.soccer.player.morbibackend.repository.ImageRepository;
import com.soccer.player.morbibackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Optional;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/image")
public class ImageUploadController{


    @Autowired
    ImageRepository imageRepository;






    @Transactional
    @PostMapping("/uploadFile/{userid}")
    public ResponseEntity.BodyBuilder uplaodImage(@PathVariable("userid") Long userid, @RequestParam("userimage") MultipartFile file) throws IOException {
        Optional<ImageModel> getImg = imageRepository.getPhotoByUserid(userid);
        System.out.println("Images Controller is working fine upload images ");
        ImageModel img = new ImageModel(file.getOriginalFilename(), file.getContentType(),
                compressBytes(file.getBytes()), userid);
        if(getImg.isEmpty()){
            imageRepository.save(img);
        }else{
            imageRepository.updateUserPhoto(compressBytes(file.getBytes()), file.getOriginalFilename(), file.getContentType(), getImg.get().getId());
        }
        return ResponseEntity.status(HttpStatus.OK);
    }





    @GetMapping(path = { "/userImage/{userid}" })
    public ImageModel getImage(@PathVariable("userid") Long userid) throws IOException {
        final Optional<ImageModel> retrievedImage = imageRepository.getPhotoByUserid(userid);
        ImageModel img = new ImageModel(retrievedImage.get().getName(), retrievedImage.get().getType(),
                decompressBytes(retrievedImage.get().getPicByte()));
        return img;
    }






    public static byte[] compressBytes(byte[] data) {
        Deflater deflater = new Deflater();
        deflater.setInput(data);
        deflater.finish();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] buffer = new byte[1024];
        while (!deflater.finished()) {
            int count = deflater.deflate(buffer);
            outputStream.write(buffer, 0, count);
        }
        try {
            outputStream.close();
        } catch (IOException e) {
        }
        return outputStream.toByteArray();
    }





    public static byte[] decompressBytes(byte[] data) {
        Inflater inflater = new Inflater();
        inflater.setInput(data);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] buffer = new byte[1024];
        try {
            while (!inflater.finished()) {
                int count = inflater.inflate(buffer);
                outputStream.write(buffer, 0, count);
            }
            outputStream.close();
        } catch (IOException ioe){

        } catch (DataFormatException e){

        }
        return outputStream.toByteArray();
    }


}
