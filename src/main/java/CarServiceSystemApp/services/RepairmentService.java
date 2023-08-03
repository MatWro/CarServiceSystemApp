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
import CarServiceSystemApp.mappers.RepairmentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RepairmentService {


    private RepairmentMapper repairmentMapper;
    private final RepairmentRepository repairmentRepository;
    private final CarRepository carRepository;

    private final UserRepository userRepository;

    @Autowired
    public RepairmentService(RepairmentRepository repairmentRepository, CarRepository carRepository, UserRepository userRepository,RepairmentMapper repairmentMapper) {
        this.repairmentRepository = repairmentRepository;
        this.carRepository = carRepository;
        this.userRepository = userRepository;
        this.repairmentMapper = repairmentMapper;
    }

    public List<RepairmentDTO> getRepairmentsByCarId(Long carId) throws CarNotFoundException {
        Car car = carRepository.findById(carId)
                .orElseThrow(() -> new CarNotFoundException("Car with ID " + carId + " not found."));

        List<Repairment> repairments = repairmentRepository.findByCar(car);
        return repairments.stream()
                .map(RepairmentMapper::convertToRepairmentDTO)
                .collect(Collectors.toList());
    }
    public List<RepairmentDTO> getRepairmentsByUserId(Long userId) throws UserNotFoundException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User with ID " + userId + " not found."));

        List<Repairment> repairments = repairmentRepository.findByUser(user);
        return repairments.stream()
                .map(RepairmentMapper::convertToRepairmentDTO)
                .collect(Collectors.toList());
    }
    public RepairmentDTO getRepairmentById(Long repairmentId) throws RepairmentNotFoundException {
        Repairment repairment = repairmentRepository.findById(repairmentId)
                .orElseThrow(() -> new RepairmentNotFoundException("Repairment with ID " + repairmentId + " not found."));

        return RepairmentMapper.convertToRepairmentDTO(repairment);
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


    public RepairmentDTO addRepairmentForCar(RepairmentDTO repairmentDTO) throws CarNotFoundException {

        Repairment repairment = repairmentMapper.convertToRepairment(repairmentDTO);

        Repairment savedRepairment = repairmentRepository.save(repairment);


        return RepairmentMapper.convertToRepairmentDTO(repairment);
    }



}