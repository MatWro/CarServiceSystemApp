package CarServiceSystemApp.services;

import CarServiceSystemApp.DTO.CarDTO;
import CarServiceSystemApp.DTO.UserDTO;
import CarServiceSystemApp.Repo.CarRepository;
import CarServiceSystemApp.Repo.UserRepository;
import CarServiceSystemApp.entities.Car;
import CarServiceSystemApp.entities.User;
import CarServiceSystemApp.exceptions.UserNotFoundException;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CarService {
    private CarRepository carRepository;
    private UserRepository userRepository;

    public CarService(CarRepository carRepository){
        this.carRepository = carRepository;
    }
    public List<Car> getAllCars(){
        return carRepository.findAll();
    }
    public Car saveCar(Car car) {
        return carRepository.save(car);
    }
    public Car getCarById(Long id) {
        return carRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Car with id " + id + " not found"));
    }
    public void deleteCar(Long id) {
        carRepository.deleteById(id);
    }
    public CarDTO addCarForUser(UserDTO userDTO, CarDTO carDTO) {

        Car newCar = new Car();
        newCar.setBrand(carDTO.getBrand());
        newCar.setType(carDTO.getType());
        newCar.setModel(carDTO.getModel());
        newCar.setGasType(carDTO.getGasType());
        newCar.setMillage(carDTO.getMillage());
        newCar.setEngine_Capacity(carDTO.getEngine_Capacity());


        User user = new User();
        user.setId(userDTO.getId());
        newCar.setUser(user);


        Car savedCar = carRepository.save(newCar);


        CarDTO addedCarDTO = new CarDTO();
        addedCarDTO.setId(savedCar.getId());
        addedCarDTO.setBrand(savedCar.getBrand());
        addedCarDTO.setType(savedCar.getType());
        addedCarDTO.setModel(savedCar.getModel());
        addedCarDTO.setGasType(savedCar.getGasType());
        addedCarDTO.setMillage(savedCar.getMillage());
        addedCarDTO.setEngine_Capacity(savedCar.getEngine_Capacity());

        return addedCarDTO;
    }

    public List<CarDTO> getCarsByUserId(Long user_Id) throws UserNotFoundException {
        List<Car> cars = carRepository.findByUser_Id(user_Id);
        if (cars.isEmpty()) {
            throw new UserNotFoundException("User with ID " + user_Id + " not found or has no cars.");
        }


        List<CarDTO> carDTOs = cars.stream()
                .map(this::convertToCarDTO)
                .collect(Collectors.toList());

        return carDTOs;
    }


    private CarDTO convertToCarDTO(Car car) {
        CarDTO carDTO = new CarDTO();
        carDTO.setId(car.getId());
        carDTO.setBrand(car.getBrand());
        carDTO.setType(car.getType());
        carDTO.setModel(car.getModel());
        carDTO.setGasType(car.getGasType());
        carDTO.setMillage(car.getMillage());
        carDTO.setEngine_Capacity(car.getEngine_Capacity());

        return carDTO;
    }
}
