package CarServiceSystemApp.DTO;


import lombok.Data;

import java.util.Date;

@Data
public class RepairmentDTO {

    private Long id;
    private String description;
    private Date date;
    private Long car_id;
    private Long user_id;


}