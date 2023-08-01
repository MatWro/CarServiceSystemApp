package CarServiceSystemApp.DTO;

import lombok.Data;

@Data
public class CarDTO {

    private Long id;
    private String brand;
    private String type;
    private String model;
    private String gasType;
    private Long millage;
    private float engine_Capacity;
    private Long user_id;

}