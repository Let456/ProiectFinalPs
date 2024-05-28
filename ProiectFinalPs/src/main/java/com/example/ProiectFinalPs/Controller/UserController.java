package com.example.ProiectFinalPs.Controller;

import com.example.ProiectFinalPs.DTO.NameRequest;
import com.example.ProiectFinalPs.DTO.UserTypeRequest;
import com.example.ProiectFinalPs.Model.Parfum;
import com.example.ProiectFinalPs.Model.ParfumMagazin;
import com.example.ProiectFinalPs.Model.SticlaParfum;
import com.example.ProiectFinalPs.Model.Utilizator;
import com.example.ProiectFinalPs.Service.Implementation.UserServiceImplementation;
import com.example.ProiectFinalPs.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@CrossOrigin
@RequestMapping("/User")
public class UserController {

    private final UserServiceImplementation userServiceImplementation;

    @PostMapping("/Login")
    public ResponseEntity<Object> login(@RequestBody Utilizator credentials) {

        Utilizator utilizator = userServiceImplementation.login(credentials);

        if (utilizator == null) {
            return ResponseEntity.badRequest().body("Invalid Credentials");
        }
        else {
            return ResponseEntity.ok(utilizator);
        }
    }

    @PostMapping("/GetMagazinId")
    public ResponseEntity<Integer> magazinId(@RequestBody Integer id)
    {
        Utilizator utilizator = userServiceImplementation.findFirstById(id);
        return ResponseEntity.ok(utilizator.getMagazin().getMagazinId());
    }

    @PostMapping("/SaveAsCsv")
    public ResponseEntity<String> saveAsCsv(@RequestBody Integer id)
    {
        Utilizator user = userServiceImplementation.findFirstById(id);
        String response =  userServiceImplementation.saveAsCsv(user);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/SaveAsDoc")
    public ResponseEntity<String> saveAsDoc(@RequestBody Integer id)
    {
        Utilizator user = userServiceImplementation.findFirstById(id);
        String response = userServiceImplementation.saveAsDoc(user);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/SaveAsJson")
    public ResponseEntity<String> saveAsJson(@RequestBody Integer id)
    {
        Utilizator user = userServiceImplementation.findFirstById(id);
        String response = userServiceImplementation.saveAsJson(user);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/SaveAsXml")
    public ResponseEntity<String> saveAsXml(@RequestBody Integer id)
    {
        Utilizator user = userServiceImplementation.findFirstById(id);
        String response = userServiceImplementation.saveAsXml(user);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/FindAll")
    public ResponseEntity<List<Utilizator>> findAll()
    {
        List<Utilizator> utilizatorList = userServiceImplementation.findAll();

        return ResponseEntity.ok(utilizatorList);
    }

    @PostMapping("/Insert")
    public ResponseEntity<String> Insert(@RequestBody Utilizator utilizator)
    {

        return ResponseEntity.ok(userServiceImplementation.insert(utilizator));

    }

    @PostMapping("/Delete")
    public ResponseEntity<String> delete(@RequestBody Utilizator utilizator)
    {
        userServiceImplementation.delete(utilizator);

        return ResponseEntity.ok("deleted");
    }

    @PostMapping("/Search")
    public ResponseEntity<List<Utilizator>> search(@RequestBody UserTypeRequest userTypeRequest)
    {
        System.out.println(userTypeRequest.getUserType());
        List<Utilizator> userList = userServiceImplementation.search(userTypeRequest.getUserType());
        return ResponseEntity.ok(userList);
    }

}
