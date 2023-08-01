package CarServiceSystemApp.controllers;

import CarServiceSystemApp.DTO.RepairmentDTO;
import CarServiceSystemApp.entities.Car;
import CarServiceSystemApp.entities.Repairment;
import CarServiceSystemApp.exceptions.CarNotFoundException;
import CarServiceSystemApp.exceptions.RepairmentNotFoundException;
import CarServiceSystemApp.exceptions.UserNotFoundException;
import CarServiceSystemApp.services.CarService;
import CarServiceSystemApp.services.RepairmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/repairment")
public class RepairmentController {
    private final RepairmentService repairmentService;

    private final CarService carService;

    @Autowired
    public RepairmentController(RepairmentService repairmentService, CarService carService) {
        this.repairmentService = repairmentService;
        this.carService = carService;
    }

    @GetMapping("/car/{carId}")
    public ResponseEntity<List<RepairmentDTO>> getRepairmentsByCarId(@PathVariable Long carId) {
        try {
            List<RepairmentDTO> repairments = repairmentService.getRepairmentsByCarId(carId);
            return ResponseEntity.ok(repairments);
        } catch (CarNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<RepairmentDTO>> getRepairmentsByUserId(@PathVariable Long userId) {
        try {
            List<RepairmentDTO> repairments = repairmentService.getRepairmentsByUserId(userId);
            return ResponseEntity.ok(repairments);
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/{repairmentId}")
    public ResponseEntity<RepairmentDTO> getRepairmentById(@PathVariable Long repairmentId) {
        try {
            RepairmentDTO repairmentDTO = repairmentService.getRepairmentById(repairmentId);
            return ResponseEntity.ok(repairmentDTO);
        } catch (RepairmentNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping
    public ResponseEntity<RepairmentDTO> addRepairmentForCar(@RequestBody RepairmentDTO repairmentDTO) {
        try {
            RepairmentDTO addedRepairmentDTO = repairmentService.addRepairmentForCar(repairmentDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(addedRepairmentDTO);
        } catch (CarNotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @DeleteMapping("/{repairmentId}")
    public ResponseEntity<Void> deleteRepairmentById(@PathVariable Long repairmentId) {
        repairmentService.deleteRepairmentById(repairmentId);
        return ResponseEntity.noContent().build();
    }


    @DeleteMapping("/car/{carId}")
    public ResponseEntity<Void> deleteRepairmentsByCarId(@PathVariable Long carId) {
        repairmentService.deleteRepairmentsByCarId(carId);
        return ResponseEntity.noContent().build();
    }


    @DeleteMapping("/user/{userId}")
    public ResponseEntity<Void> deleteRepairmentsByUserId(@PathVariable Long userId) {
        repairmentService.deleteRepairmentsByUserId(userId);
        return ResponseEntity.noContent().build();
    }
}
