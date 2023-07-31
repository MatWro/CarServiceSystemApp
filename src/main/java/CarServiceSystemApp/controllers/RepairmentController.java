package CarServiceSystemApp.controllers;

import CarServiceSystemApp.DTO.RepairmentDTO;
import CarServiceSystemApp.entities.Car;
import CarServiceSystemApp.entities.Repairment;
import CarServiceSystemApp.exceptions.CarNotFoundException;
import CarServiceSystemApp.services.CarService;
import CarServiceSystemApp.services.RepairmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

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

    @PostMapping("/car/{carId}")
    public ResponseEntity<RepairmentDTO> addRepairmentByCarId(@PathVariable Long carId, @RequestBody RepairmentDTO repairmentDTO) {
        try {
            RepairmentDTO addedRepairmentDTO = repairmentService.addRepairmentForCar(carId, repairmentDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(addedRepairmentDTO);
        } catch (CarNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
