package CarServiceSystemApp.Repo;

import CarServiceSystemApp.entities.Car;
import CarServiceSystemApp.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {
    List<Car> findByUser_Id(Long user_id);
}
