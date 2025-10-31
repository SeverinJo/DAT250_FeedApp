package no.hvl.FeedApp.controllers;


import jakarta.persistence.EntityManager;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import no.hvl.FeedApp.dtos.UserDtos;
import no.hvl.FeedApp.entities.User;
import no.hvl.FeedApp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService service;

    @PostMapping
    public UserDtos.View create(@Valid @RequestBody UserDtos.Create dto) {
        return service.registerUser(dto);
    }

    @GetMapping("/{username}")
    public UserDtos.View get(@PathVariable String username) {
        return service.findUserByUsername(username);
    }

    @GetMapping
    public List<UserDtos.View> list() {
        return service.list();
    }

    @PutMapping("/{username}")
    public UserDtos.View update(@PathVariable String username,
                                @Valid @RequestBody UserDtos.Update dto) {
        return service.update(username, dto);
    }

    @DeleteMapping("/{username}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable String username) {
        service.delete(username);
    }
}
