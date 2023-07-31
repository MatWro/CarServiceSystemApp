package CarServiceSystemApp.controllers;

import CarServiceSystemApp.DTO.CarDTO;
import CarServiceSystemApp.DTO.UserDTO;
import CarServiceSystemApp.entities.Car;
import CarServiceSystemApp.exceptions.UserNotFoundException;
import CarServiceSystemApp.services.CarService;
import CarServiceSystemApp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cars")
public class CarController {
    private final CarService carService;
    private final UserService userService;


    @Autowired
    public CarController(CarService carService, UserService userService) {
        this.carService = carService;
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<Car>> getAllCars() {
        List<Car> cars = carService.getAllCars();
        return ResponseEntity.ok(cars);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Car> getCarById(@PathVariable Long id) {
        Car car = carService.getCarById(id);
        return ResponseEntity.ok(car);
    }

    @PostMapping
    public ResponseEntity<Car> addCar(@RequestBody Car car) {
        Car savedCar = carService.saveCar(car);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedCar);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCar(@PathVariable Long id) {
        carService.deleteCar(id);
        return ResponseEntity.noContent().build();
    }
    @PostMapping("/user/{userId}")
    public ResponseEntity<CarDTO> addCarForUser(@PathVariable Long userId, @RequestBody CarDTO carDTO) {
        try {
            UserDTO userDTO = userService.getUserById(userId);
            CarDTO addedCarDTO = carService.addCarForUser(userDTO, carDTO);
            return ResponseEntity.ok(addedCarDTO);
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
    @GetMapping("/user/{id}")
    public ResponseEntity<List<CarDTO>> getCarsByUserId(@PathVariable Long id) {
        try {
            List<CarDTO> cars = carService.getCarsByUserId(id);
            return ResponseEntity.ok(cars);
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}