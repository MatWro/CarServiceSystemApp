package CarServiceSystemApp.services;

import CarServiceSystemApp.Repo.CarRepository;
import CarServiceSystemApp.Repo.UserRepository;
import CarServiceSystemApp.entities.Car;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static CarServiceSystemApp.TestCarProvider.testedCarWithId;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;



@ExtendWith(MockitoExtension.class)
class CarServiceTest {

    private static final Long CAR_ID = 1L;
    @Mock
    CarRepository carRepository;
    @Mock
    UserRepository userRepository;

    @InjectMocks
    CarService carService;



    @Test
    void getAllCarsFromRepo() {
        List<Car> cars = new ArrayList<>();
        Car car1 = new Car();
        car1.setId(1L);
        car1.setBrand("Toyota");
        car1.setType("Sedan");
        car1.setModel("Corolla");
        car1.setGasType("Gasoline");
        car1.setMillage(100000L);
        car1.setEngine_Capacity(1.8f);

        Car car2 = new Car();
        car2.setId(2L);
        car2.setBrand("Honda");
        car2.setType("SUV");
        car2.setModel("CR-V");
        car2.setGasType("Gasoline");
        car2.setMillage(80000L);
        car2.setEngine_Capacity(2.4f);
        doReturn(cars).when(carRepository).findAll();

        cars.add(car1);
        cars.add(car2);

        // When
        List<Car> result = carService.getAllCars();

        // Then
        assertEquals(2, result.size());
        assertEquals("Toyota", result.get(0).getBrand());
        assertEquals("Honda", result.get(1).getBrand());



    }

    @Test
    void saveCar_success() {
        //given
        Car car = testedCarWithId(CAR_ID);
        doReturn(car).when(carRepository).save(any());
        //when
        Car savedCar = carService.saveCar(car);
        //then
        Assertions.assertNotNull(savedCar);
        assertEquals(CAR_ID, savedCar.getId());
    }

    @Test
    void getCarById_success() {
        //given
        Car testedCarWithId = testedCarWithId(CAR_ID);
        doReturn(Optional.of(testedCarWithId)).when(carRepository).findById(CAR_ID);

        Car car = carService.getCarById(CAR_ID);

        Assertions.assertNotNull(car);
        assertEquals(CAR_ID, car.getId());
    }
    @Test
    void getCarById_fail() {
        doReturn(Optional.empty()).when(carRepository).findById(CAR_ID);

        //doThrow(NoSuchElementException.class).when(carService).getCarById(CAR_ID); mockownanie metody by rzucała wyjątek

        Assertions.assertThrows(NoSuchElementException.class, ()->carService.getCarById(CAR_ID));

    }

    @Test
    void deleteCar() {
    }

    @Test
    void addCarForUser() {
    }

    @Test
    void getCarsByUserId() {
    }

    @Test
    void testAddCarForUser() {
    }




}