package io.github.nprcz.controller;

import io.github.nprcz.model.User;
import io.github.nprcz.model.UserRepository;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
 private final UserRepository userRepository;
 public static final Logger logger = LoggerFactory.getLogger(User.class);

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping(value ="" ,params ={"!sort","!page","!size"} )
    ResponseEntity<List<User>> readAllUser(){
        return ResponseEntity.ok(userRepository.findAll());

 }
    @GetMapping
    ResponseEntity<List<User>> readAllUser(Pageable page){
        return ResponseEntity.ok(userRepository.findAll(page).getContent());

    }
    @PutMapping("/{id}")
    ResponseEntity<User> updateUser(@RequestBody User userToUpdate,@PathVariable int id){
      if (!userRepository.existsById(id)){
          return ResponseEntity.notFound().build();
        }
      userToUpdate.setId(id);
      userRepository.save(userToUpdate);
      return ResponseEntity.noContent().build();

    }
    @PostMapping
    ResponseEntity<User> createUser(@RequestBody User userToCreate){
        User result = userRepository.save(userToCreate);
        return ResponseEntity.created(URI.create("/" + result.getId())).body(result);
    }
    @GetMapping("/serach")
    ResponseEntity<User> serachUser(String userEmail){


        return userRepository.getByEmail(userEmail).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
}
