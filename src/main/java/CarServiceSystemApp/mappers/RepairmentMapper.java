package CarServiceSystemApp.mappers;

import CarServiceSystemApp.DTO.RepairmentDTO;
import CarServiceSystemApp.Repo.CarRepository;
import CarServiceSystemApp.Repo.UserRepository;
import CarServiceSystemApp.entities.Car;
import CarServiceSystemApp.entities.Repairment;
import CarServiceSystemApp.entities.User;
import CarServiceSystemApp.exceptions.CarNotFoundException;
import CarServiceSystemApp.exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RepairmentMapper {

    private CarRepository carRepository;
    private UserRepository userRepository;
    @Autowired
    public RepairmentMapper(CarRepository carRepository, UserRepository userRepository) {
        this.carRepository = carRepository;
        this.userRepository = userRepository;
    }

    public Repairment convertToRepairment(RepairmentDTO repairmentDTO){
        Car car = carRepository.findById(repairmentDTO.getCar_id())
                .orElseThrow(() -> new CarNotFoundException("Car with ID " + repairmentDTO.getCar_id() + " not found."));
        User user = userRepository.findById(repairmentDTO.getUser_id())
                .orElseThrow(()-> new UserNotFoundException("User with ID " + repairmentDTO.getUser_id() + " not found."));

        Repairment newRepairment = new Repairment();
        newRepairment.setDescription(repairmentDTO.getDescription());
        newRepairment.setDate(repairmentDTO.getDate());
        newRepairment.setCar(car);
        newRepairment.setUser(user);

        return newRepairment;
    }

    public static RepairmentDTO convertToRepairmentDTO(Repairment repairment) {
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

    public Repairment convertToRepairment(RepairmentDTO repairmentDTO,User user, Car car){
        Repairment repairment = convertToRepairment(repairmentDTO);
        repairment.setUser(user);
        repairment.setCar(car);

        return repairment;
    }
}
