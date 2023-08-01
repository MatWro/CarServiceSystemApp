package CarServiceSystemApp.Repo;

import CarServiceSystemApp.entities.Car;
import CarServiceSystemApp.entities.Repairment;
import CarServiceSystemApp.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RepairmentRepository extends JpaRepository<Repairment, Long> {
    List<Repairment> findByCar(Car car);
    List<Repairment> findByUser(User user);

    @Modifying
    @Query("DELETE FROM Repairment r WHERE r.user.id = ?1")
    void deleteByUserId(Long userId);

    @Modifying
    @Query("DELETE FROM Repairment r WHERE r.car.id = ?1")
    void deleteByCarId(Long carId);
}