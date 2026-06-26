package org.valor.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.valor.model.dto.UsersDto;
import org.valor.service.users.UsersService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/users")
public class UsersController {
    private final UsersService service;

    @Autowired
    public UsersController(UsersService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<UsersDto>> getAllUsers(@RequestParam("date") String date, Pageable pageable) {
        List<UsersDto> usersDtos = service.getAllUsers(date, pageable);
        return ResponseEntity.ok(usersDtos);
    }


    @GetMapping("/{id}")
    public ResponseEntity<UsersDto> getByUsersById(@PathVariable UUID id) {
        UsersDto usersDto = service.getById(id);
        return ResponseEntity.ok(usersDto);
    }

    @PostMapping
    public ResponseEntity<UUID> createUsers(@RequestBody UsersDto request) {
        UUID id = service.createUsers(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UUID> updateUsers(@PathVariable UUID id, @RequestBody UsersDto request) {
        UUID getId = service.updateUsers(id, request);
        return ResponseEntity.ok(getId);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<UUID> deleteUsers(@PathVariable UUID id) {
        UUID getId = service.deleteUsers(id);
        return ResponseEntity.ok(getId);
    }


}
