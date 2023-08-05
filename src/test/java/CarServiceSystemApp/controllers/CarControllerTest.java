package CarServiceSystemApp.controllers;


import CarServiceSystemApp.DTO.CarDTO;
import CarServiceSystemApp.entities.Car;

import CarServiceSystemApp.services.CarService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static CarServiceSystemApp.TestCarProvider.CAR_ID;
import static CarServiceSystemApp.TestCarProvider.testedCarWithId;
import static org.mockito.Mockito.doReturn;

@WebMvcTest(CarController.class)
@ExtendWith(MockitoExtension.class)
class CarControllerTest {


    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private CarService carService;
    @MockBean
    private CarDTO carDTO;

    @Test
    public void testGetAllCars() {

    }

    @Test
    @WithMockUser
    void getCarById() throws Exception {
        //give
        Car car = testedCarWithId(CAR_ID);
        doReturn(car).when(carService).getCarById(CAR_ID);

        mockMvc.perform(get("/cars/1"))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    void addCar() {
    }

    @Test
    void deleteCar() {
        Car car = testedCarWithId(CAR_ID);
        doReturn(car).when(carService).getCarById(CAR_ID);

    }

    @Test
    void getCarsByUserId() {
    }

    @Test
    void getAllCars() {
    }

    @Test
    void testGetCarById() {
    }

    @Test
    void testAddCar() {
    }

    @Test
    void testDeleteCar() {
    }

    @Test
    void testGetCarsByUserId() {
    }
}