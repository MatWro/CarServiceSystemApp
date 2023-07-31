package CarServiceSystemApp.services;

import CarServiceSystemApp.DTO.RepairmentDTO;
import CarServiceSystemApp.Repo.CarRepository;
import CarServiceSystemApp.Repo.RepairmentRepository;
import CarServiceSystemApp.entities.Car;
import CarServiceSystemApp.entities.Repairment;
import CarServiceSystemApp.entities.User;
import CarServiceSystemApp.exceptions.CarNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class RepairmentService {
    private final RepairmentRepository repairmentRepository;
    private final CarRepository carRepository;

    @Autowired
    public RepairmentService(RepairmentRepository repairmentRepository, CarRepository carRepository) {
        this.repairmentRepository = repairmentRepository;
        this.carRepository = carRepository;
    }

    public RepairmentDTO addRepairmentForCar(Long carId, RepairmentDTO repairmentDTO) throws CarNotFoundException {
        Car car = carRepository.findById(carId).orElseThrow(() -> new CarNotFoundException("Car with ID " + carId + " not found."));

        Repairment repairment = new Repairment();
        repairment.setDescription(repairmentDTO.getDescription());
        repairment.setDate(new Date());
        repairment.setCar(car);
        repairment.setUser(repairment.getUser());

        Repairment savedRepairment = repairmentRepository.save(repairment);

        return convertToRepairmentDTO(savedRepairment);
    }

    private RepairmentDTO convertToRepairmentDTO(Repairment repairment) {
        RepairmentDTO repairmentDTO = new RepairmentDTO();
        repairmentDTO.setId(repairment.getId());
        repairmentDTO.setDescription(repairment.getDescription());
        repairmentDTO.setDate(repairment.getDate());
        repairmentDTO.setCarId(repairment.getCar().getId());
        repairmentDTO.setUserId(repairment.getCar().getUser().getId());
        return repairmentDTO;
    }

}