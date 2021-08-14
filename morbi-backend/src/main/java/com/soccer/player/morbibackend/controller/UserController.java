package com.soccer.player.morbibackend.controller;


import com.soccer.player.morbibackend.model.Inbox;
import com.soccer.player.morbibackend.model.User;
import com.soccer.player.morbibackend.repository.ImageRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.soccer.player.morbibackend.payload.request.UserUpdate;
import com.soccer.player.morbibackend.payload.response.MessageResponse;
import com.soccer.player.morbibackend.repository.InboxRepo;
import com.soccer.player.morbibackend.repository.UserRepository;
import com.soccer.player.morbibackend.security.services.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import javax.servlet.ServletContext;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.*;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/test")
public class UserController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    InboxRepo inboxRepo;

    @Autowired
    ImageRepository imageRepository;

    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    ServletContext context;

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @GetMapping("/all")
    public String allAccess() {
        return "This is a Public Content.";
    }



    @GetMapping("/user")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public String userAccess() {
        return "This is User Content.";
    }



    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public String adminAccess() {
        return "Admin Board.";
    }




    @GetMapping("/userbyname/{username}")
    public ResponseEntity<User> getUserByName(@PathVariable(value = "username") String username)
            throws RuntimeException{
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found for this username :: " + username));
        return ResponseEntity.ok().body(user);
    }




    @Transactional
    @PutMapping("/updateUser")
    public ResponseEntity<?> usedateUser(@Valid @RequestBody UserUpdate userupdate)
    {
        User u = userRepository.getUserByid(userupdate.getUserid());
        u.setFirstName(userupdate.getFirstName());
        u.setLastName(userupdate.getLastName());
        u.setPhone(userupdate.getPhone());
        userRepository.save(u);
        return ResponseEntity.ok(new MessageResponse("User Update successfully!"));
    }






    @GetMapping("/getallinbox/{userid}")
    public ResponseEntity<List<Inbox>> getAllText(@PathVariable("userid") Long userid)
            throws RuntimeException{
            List<Inbox> i = inboxRepo.findMessageByFrom(userid);
        return ResponseEntity.ok().body(i);
    }




    /*
        @GetMapping("/getUserComboData/{userid}")
        public ResponseEntity<List<user>> getUserCombo(@PathVariable("userid") Long userid)
                throws RuntimeException{
            User user = userRepository.findAllButThis(userid);
            return ResponseEntity.ok().body(user);
        }
    */


    @GetMapping("/getUserComboData/{userid}")
    public ResponseEntity<List<User>> getUserForCombo(@PathVariable("userid") Long userid)
            throws RuntimeException{
        List<User> user = userRepository.findAllButThis(userid);
        return ResponseEntity.ok().body(user);
    }





    public List<Inbox> getAllEmployees(){
        return inboxRepo.findAll();
    }



    // uncompress the image bytes before returning it to the angular application
    // compress the image bytes before storing it in the database
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
        try{
            outputStream.close();
        }catch (IOException e){
        }
        System.out.println("Compressed Image Byte Size - " + outputStream.toByteArray().length);
        return outputStream.toByteArray();
    }



    // uncompress the image bytes before returning it to the angular application
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
        } catch (IOException ioe) {
        } catch (DataFormatException e) {
        }
        return outputStream.toByteArray();
    }


/*

    @GetMapping(path = { "/get/{imageName}" })
    public ImageModel getImage(@PathVariable("imageName") String imageName) throws IOException
    {
        final Optional<ImageModel> retrievedImage = imageRepository.findByName(imageName);
        ImageModel img = new ImageModel(retrievedImage.get().getName(), retrievedImage.get().getType(),
        decompressBytes(retrievedImage.get().getPicByte()));
        return img;
    }
*/


}
