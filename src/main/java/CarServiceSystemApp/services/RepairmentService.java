package CarServiceSystemApp.services;

import CarServiceSystemApp.DTO.RepairmentDTO;
import CarServiceSystemApp.Repo.CarRepository;
import CarServiceSystemApp.Repo.RepairmentRepository;
import CarServiceSystemApp.Repo.UserRepository;
import CarServiceSystemApp.entities.Car;
import CarServiceSystemApp.entities.Repairment;
import CarServiceSystemApp.entities.User;
import CarServiceSystemApp.exceptions.CarNotFoundException;
import CarServiceSystemApp.exceptions.RepairmentNotFoundException;
import CarServiceSystemApp.exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RepairmentService {
    private final RepairmentRepository repairmentRepository;
    private final CarRepository carRepository;

    private final UserRepository userRepository;

    @Autowired
    public RepairmentService(RepairmentRepository repairmentRepository, CarRepository carRepository, UserRepository userRepository) {
        this.repairmentRepository = repairmentRepository;
        this.carRepository = carRepository;
        this.userRepository = userRepository;
    }

    public List<RepairmentDTO> getRepairmentsByCarId(Long carId) throws CarNotFoundException {
        Car car = carRepository.findById(carId)
                .orElseThrow(() -> new CarNotFoundException("Car with ID " + carId + " not found."));

        List<Repairment> repairments = repairmentRepository.findByCar(car);
        return repairments.stream()
                .map(this::convertToRepairmentDTO)
                .collect(Collectors.toList());
    }
    public List<RepairmentDTO> getRepairmentsByUserId(Long userId) throws UserNotFoundException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User with ID " + userId + " not found."));

        List<Repairment> repairments = repairmentRepository.findByUser(user);
        return repairments.stream()
                .map(this::convertToRepairmentDTO)
                .collect(Collectors.toList());
    }
    public RepairmentDTO getRepairmentById(Long repairmentId) throws RepairmentNotFoundException {
        Repairment repairment = repairmentRepository.findById(repairmentId)
                .orElseThrow(() -> new RepairmentNotFoundException("Repairment with ID " + repairmentId + " not found."));

        return convertToRepairmentDTO(repairment);
    }

    public void deleteRepairmentById(Long repairmentId) {
        repairmentRepository.deleteById(repairmentId);
    }

    public void deleteRepairmentsByCarId(Long carId) {
        repairmentRepository.deleteByCarId(carId);
    }

    public void deleteRepairmentsByUserId(Long userId) {
        repairmentRepository.deleteByUserId(userId);
    }
//    public RepairmentDTO addRepairmentForUserAndCar(Long userId, Long carId, RepairmentDTO repairmentDTO) throws UserNotFoundException, CarNotFoundException {
//        User user = userRepository.findById(userId)
//                .orElseThrow(() -> new UserNotFoundException("User with ID " + userId + " not found."));
//
//        Car car = carRepository.findById(carId)
//                .orElseThrow(() -> new CarNotFoundException("Car with ID " + carId + " not found."));
//
//        Repairment repairment = new Repairment();
//        repairment.setDescription(repairmentDTO.getDescription());
//        repairment.setDate(repairmentDTO.getDate());
//        repairment.setCar(car);
//        repairment.setUser(user);
//
//        Repairment savedRepairment = repairmentRepository.save(repairment);
//
//        return convertToRepairmentDTO(savedRepairment);
//    }


    public RepairmentDTO addRepairmentForCar(RepairmentDTO repairmentDTO) throws CarNotFoundException {
        Car car = carRepository.findById(repairmentDTO.getCar_id())
                .orElseThrow(() -> new CarNotFoundException("Car with ID " + repairmentDTO.getCar_id() + " not found."));
        User user = userRepository.findById(repairmentDTO.getUser_id())
                .orElseThrow(()-> new UserNotFoundException("User with ID " + repairmentDTO.getUser_id() + " not found."));

        Repairment newRepairment = new Repairment();
        newRepairment.setDescription(repairmentDTO.getDescription());
        newRepairment.setDate(repairmentDTO.getDate());
        newRepairment.setCar(car);
        newRepairment.setUser(user);


        Repairment savedRepairment = repairmentRepository.save(newRepairment);

        RepairmentDTO addedRepairmentDTO = new RepairmentDTO();
        addedRepairmentDTO.setId(savedRepairment.getId());
        addedRepairmentDTO.setDescription(savedRepairment.getDescription());
        addedRepairmentDTO.setDate(savedRepairment.getDate());
        addedRepairmentDTO.setCar_id(savedRepairment.getCar().getId());
        addedRepairmentDTO.setUser_id(savedRepairment.getUser().getId());

        return addedRepairmentDTO;
    }

    private RepairmentDTO convertToRepairmentDTO(Repairment repairment) {
        RepairmentDTO repairmentDTO = new RepairmentDTO();
        repairmentDTO.setId(repairment.getId());
        repairmentDTO.setDescription(repairment.getDescription());
        repairmentDTO.setDate(repairment.getDate());

        Car car = repairment.getCar();
        if (car != null) {
            repairmentDTO.setCar_id(car.getId());
            User user = car.getUser();
            if (user != null) {
                repairmentDTO.setUser_id(user.getId());
            }
        }

        return repairmentDTO;
    }

}