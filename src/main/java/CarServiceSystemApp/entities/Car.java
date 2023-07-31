package CarServiceSystemApp.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "cars")
public class Car {

    @Id
    @GeneratedValue
    private Long id;

    @Column(length = 15)
    private String brand;
    @Column(length = 15)
    private String type;
    @Column(length = 15)
    private String model;
    @Column(length = 8)
    private String gasType;
    @Column(length = 8)
    private Long millage;
    @Column(length = 5)
    private float engine_Capacity;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "fk_car_id", referencedColumnName = "id")
    private List<Repairment> repairments;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    
}
