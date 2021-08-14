package com.soccer.player.morbibackend.controller;

import com.soccer.player.morbibackend.model.ImageModel;
import com.soccer.player.morbibackend.model.Video;
import com.soccer.player.morbibackend.payload.request.Response;
import com.soccer.player.morbibackend.repository.ImageRepository;
import com.soccer.player.morbibackend.repository.VideoRepo;
import com.soccer.player.morbibackend.security.services.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import javax.transaction.Transactional;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/video")
public class VideoController{

    @Autowired
    VideoRepo videoRepo;

    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    ImageRepository imageRepository;





    @Transactional
    @PostMapping("/uploadFile/{userid}")
    public Response uploadFile(@PathVariable("userid") Long userid, @RequestParam("uservideo") MultipartFile file){
        String fileName = fileStorageService.storeFile(file);
        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/downloadFile/")
                .path(fileName)
                .toUriString();
        Video v = new Video();
        v.setFilename(fileName);
        v.setContentType(file.getContentType());
        v.setUserid(userid);
        videoRepo.save(v);
        return new Response(fileName, fileDownloadUri, file.getContentType(), file.getSize());
    }






    @GetMapping("/getAllVideoFile-ImageRetrive is Done ]/{userid}")
    public ImageModel getImage(@PathVariable("userid") Long userid) throws IOException{
        String fileName    = "hasan.jpg";
        Resource resource  = fileStorageService.loadFileAsResource(fileName);
        String contentType = "image/jpeg";
        URL url = new URL(resource.getURL().toString());
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        try (InputStream inputStream = url.openStream()){
            int n = 0;
            byte [] buffer = new byte[1024];
            while (-1 != (n = inputStream.read(buffer))){
                output.write(buffer, 0, n);
            }
        }
        ImageModel img = new ImageModel(resource.getFilename(), contentType, output.toByteArray());
        return img;
    }





    @GetMapping("/getAllVideoFile/{userid}")
    public ResponseEntity<List<Video>> getVideo(@PathVariable("userid") Long userid) throws IOException{
        System.out.println("userid: " + userid);
        List<Video> videoList = new ArrayList<>();
        List<Video> vList = videoRepo.findAllVNameById(userid);

        System.out.println(" Size of List() :  " + vList.size() );
        for (int i = 0; i < vList.size(); i++){
            Video v = new Video();
            v.setId(vList.get(i).getId());
            v.setFilename(vList.get(i).getFilename());
            v.setContentType(vList.get(i).getContentType()); // data:video/mp4;base64,
            v.setVideo("data:"+vList.get(i).getContentType()+";base64,"+Base64.getEncoder().encodeToString(fileStorageService.loadFileAsResource(vList.get(i).getFilename()).getInputStream().readAllBytes()));
            v.setUserid(userid);
            videoList.add(v);
        }
        return ResponseEntity.ok().body(videoList);
    }



    public ResponseEntity<?> getUserByName(@PathVariable("userid") Long userid){
        System.out.println("### hit Get all video... method userid ### "+userid);
        return ResponseEntity.ok(new Video());
    }



}
