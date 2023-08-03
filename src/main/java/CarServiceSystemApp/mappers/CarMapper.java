package CarServiceSystemApp.mappers;

import CarServiceSystemApp.DTO.CarDTO;
import CarServiceSystemApp.entities.Car;
import CarServiceSystemApp.entities.User;
import lombok.experimental.UtilityClass;



@UtilityClass
public class CarMapper {



    public static CarDTO convertToCarDTO(Car car) {
        CarDTO carDTO = new CarDTO();
        carDTO.setId(car.getId());
        carDTO.setBrand(car.getBrand());
        carDTO.setType(car.getType());
        carDTO.setModel(car.getModel());
        carDTO.setGasType(car.getGasType());
        carDTO.setMillage(car.getMillage());
        carDTO.setEngine_Capacity(car.getEngine_Capacity());
        carDTO.setUser_id(car.getUser().getId());

        return carDTO;
    }
    public Car convertToCar(CarDTO carDTO){

        Car car = new Car();
        carDTO.setId(car.getId());
        car.setBrand(carDTO.getBrand());
        car.setType(carDTO.getType());
        car.setModel(carDTO.getModel());
        car.setGasType(carDTO.getGasType());
        car.setMillage(carDTO.getMillage());
        car.setEngine_Capacity(carDTO.getEngine_Capacity());

        return car;
    }
    public Car convertToCar(CarDTO carDTO, User user){

        Car car = convertToCar(carDTO);
        car.setUser(user);


        return car;
    }
}


