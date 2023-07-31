package CarServiceSystemApp.Repo;

import CarServiceSystemApp.entities.Repairment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepairmentRepository extends JpaRepository<Repairment, Long> {
}