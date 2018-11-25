package com.hao.demo.api;

import com.hao.demo.bean.User;
import com.hao.demo.exception.UserNotFoundException;
import com.hao.demo.service.UserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@RequestMapping("/api")
public class UserResource {

    @Autowired
    UserService userService;

    @GetMapping(value = "/users")
    public ResponseEntity<List<User>> getUsers() {
        List<User> users = userService.getUsers();
        if (users.isEmpty()) {
            return ResponseEntity.noContent().build(); //or HttpStatus.NOT_FOUND
        }
        return new ResponseEntity<List<User>>(users, HttpStatus.OK);
    }

    @GetMapping(value = "/user/{id}")
    @ApiOperation(value = "Find user by id",
            notes = "Also returns a link to retrieve all users with rel - all-users")
    public Resource<User> getUser(@PathVariable("id") long id) {
        User user = userService.getById(id);
        if (user == null) {
            throw new UserNotFoundException("User not found, id:" + id);
        }
        Resource<User> resource = new Resource<User>(user);
        ControllerLinkBuilder controllerLinkBuilder = ControllerLinkBuilder.linkTo(methodOn(this.getClass()).getUsers());

        resource.add(controllerLinkBuilder.withRel("all-users"));

        return resource;
    }

    @PostMapping(value = "/user")
    public ResponseEntity<?> createUser(@Valid @RequestBody User user) {

        if (userService.exists(user)) {
            return new ResponseEntity("Unable to create. A User " + user.getFirstName() + " already exist.", HttpStatus.CONFLICT);

        }
        userService.createUser(user);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(user.getId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping(value = "/user/{id}")
    public ResponseEntity<?> updateUser(@PathVariable("id") long id, @Valid @RequestBody User user) {

        User currentUser = userService.getById(id);
        if (currentUser == null) {
            return new ResponseEntity("User " + id + " cannot found.",HttpStatus.NOT_FOUND);
        }

        currentUser.setFirstName(user.getFirstName());
        currentUser.setLastName(user.getLastName());

        userService.updateUser(currentUser);
        return new ResponseEntity<User>(currentUser, HttpStatus.OK);
    }

    @DeleteMapping(value = "/user/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable("id") long id) {
        User user = userService.getById(id);
        if (user == null) {
            return new ResponseEntity("User " + id + " cannot found.",HttpStatus.NOT_FOUND);
        }
        userService.deleteUser(user);
        return new ResponseEntity<User>(HttpStatus.NO_CONTENT);
    }
}