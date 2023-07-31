package CarServiceSystemApp.controllers;


import CarServiceSystemApp.DTO.LoginDTO;
import CarServiceSystemApp.DTO.UserDTO;
import CarServiceSystemApp.Repo.CarRepository;
import CarServiceSystemApp.Repo.RepairmentRepository;
import CarServiceSystemApp.Repo.UserRepository;
import CarServiceSystemApp.entities.User;
import CarServiceSystemApp.exceptions.UserLoginException;
import CarServiceSystemApp.exceptions.UserRegistrationException;
import CarServiceSystemApp.services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RepairmentRepository repairmentRepository;
    @Autowired
    private CarRepository carRepository;

    @Autowired
    public UserController(UserRepository userRepository, RepairmentRepository repairmentRepository, CarRepository carRepository) {
        this.userRepository = userRepository;
        this.repairmentRepository = repairmentRepository;
        this.carRepository = carRepository;
    }


    @GetMapping
    public List<User> retrieveAllUsers() {
        return userRepository.findAll();
    }

    @PostMapping("/register")
    public ResponseEntity<UserDTO> registerUser(@RequestBody UserDTO userDTO) {
        try {
            UserDTO registeredUser = userService.registerUser(userDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(registeredUser);
        } catch (UserRegistrationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PostMapping("/login")
    public ResponseEntity<UserDTO> login(@RequestBody LoginDTO loginDTO) {
        try {
            UserDTO loggedInUserDTO = userService.loginUser(loginDTO.getEmail(), loginDTO.getPassword());
            return ResponseEntity.ok(loggedInUserDTO);
        } catch (UserLoginException e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", "Invalid email or password.");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }
}