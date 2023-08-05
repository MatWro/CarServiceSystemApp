package CarServiceSystemApp;

import CarServiceSystemApp.entities.Car;
import CarServiceSystemApp.entities.User;

public class TestCarProvider {

    public static final Long CAR_ID = 1L;


    public static Car testedCarWithId(Long id){
        Car car = new Car();
        car.setId(id);

        return car;
    }
    public static Car testedCarDTO(){
        Car car = new Car();
        car.setId(1L);
        car.setBrand("Mercedes");
        car.setType("Sedan");
        car.setModel("S class");
        car.setMillage(300213L);
        car.setEngine_Capacity(4.0f);
        car.setUser(new User());

        return car;
    }
}
