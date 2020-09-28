package com.hao.demo.api;

import com.hao.demo.bean.User;
import com.hao.demo.dao.UserRepository;
import com.hao.demo.exception.UserNotFoundException;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/v2")
public class UserResourceV2 {

    @Autowired
    private UserRepository userRepository;

    @GetMapping(value = "/users")
    public ResponseEntity<List<User>> getUsers() {
        List<User> users = userRepository.findAll();
        if (users.isEmpty()) {
            return ResponseEntity.noContent().build(); //or HttpStatus.NOT_FOUND
        }
        return new ResponseEntity<List<User>>(users, HttpStatus.OK);
    }

    @GetMapping(value = "/user/{id}")
    @ApiOperation(value = "Find user by id",
            notes = "Also returns a link to retrieve all users with rel - all-users")
    public EntityModel<User> getUser(@PathVariable("id") long id) {
        Optional<User> user = userRepository.findById(id);
        if (!user.isPresent()) {
            throw new UserNotFoundException("User not found, id:" + id);
        }

        EntityModel<User> resource = EntityModel.of(user.get());
        WebMvcLinkBuilder clb = WebMvcLinkBuilder.linkTo(methodOn(this.getClass()).getUsers());
        resource.add(clb.withRel("all-users"));
        resource.add(WebMvcLinkBuilder.linkTo(methodOn(this.getClass()).getUser(id)).withSelfRel());

        return resource;
    }

    @PostMapping(value = "/user")
    public ResponseEntity<?> createUser(@Valid @RequestBody User user) {

        if(userRepository.existsById(user.getId())){
            return new ResponseEntity("Unable to create. A User " + user.getFirstName() + " already exist.", HttpStatus.CONFLICT);
        }
        userRepository.save(user);


        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(user.getId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping(value = "/user/{id}")
    public ResponseEntity<?> updateUser(@PathVariable("id") long id, @Valid @RequestBody User user) {
        Optional<User> currentUser = userRepository.findById(id);
        if (!currentUser.isPresent()) {
            return new ResponseEntity("User " + id + " cannot found.",HttpStatus.NOT_FOUND);
        }

        currentUser.get().setFirstName(user.getFirstName());
        currentUser.get().setLastName(user.getLastName());

        userRepository.save(currentUser.get());
        return new ResponseEntity<User>(currentUser.get(), HttpStatus.OK);
    }

    @DeleteMapping(value = "/user/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable("id") long id) {
        Optional<User> user = userRepository.findById(id);
        if (!user.isPresent()) {
            return new ResponseEntity("User " + id + " cannot found.",HttpStatus.NOT_FOUND);
        }

        userRepository.delete(user.get());
        return new ResponseEntity<User>(HttpStatus.NO_CONTENT);
    }
}