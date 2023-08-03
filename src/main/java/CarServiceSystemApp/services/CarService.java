package CarServiceSystemApp.services;

import CarServiceSystemApp.DTO.CarDTO;


import CarServiceSystemApp.Repo.CarRepository;
import CarServiceSystemApp.Repo.UserRepository;
import CarServiceSystemApp.entities.Car;
import CarServiceSystemApp.entities.User;
import CarServiceSystemApp.mappers.CarMapper;

import CarServiceSystemApp.exceptions.UserNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;


import java.util.List;
import java.util.NoSuchElementException;

import java.util.stream.Collectors;

@Service
public class CarService {


    private final CarRepository carRepository;
    private final UserRepository userRepository;
    @Autowired
    public CarService(CarRepository carRepository, UserRepository userRepository){
        this.carRepository = carRepository;
        this.userRepository= userRepository;
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


    public List<CarDTO> getCarsByUserId(Long user_id) throws UserNotFoundException {
        List<Car> cars = carRepository.findByUser_Id(user_id);
        if (cars.isEmpty()) {
            throw new UserNotFoundException("User with ID " + user_id + " not found or has no cars.");
        }


        return cars.stream()
                .map(CarMapper::convertToCarDTO)
                .collect(Collectors.toList());
    }

    public CarDTO addCarForUser(CarDTO carDTO) throws UserNotFoundException {
        User user = userRepository.findById(carDTO.getUser_id())
                .orElseThrow(() -> new UserNotFoundException("User with ID " + carDTO.getUser_id() + " not found."));

        Car car = CarMapper.convertToCar(carDTO, user);

        Car savedCar = carRepository.save(car);

        return CarMapper.convertToCarDTO(savedCar);
    }

}
