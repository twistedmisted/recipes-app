package ua.kpi.klopotenkoapp.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ua.kpi.klopotenkoapp.entity.StaffEntity;

import java.util.Optional;

@Repository
public interface StaffRepository extends CrudRepository<StaffEntity, Long> {

    Optional<StaffEntity> findByUsername(String username);

    Optional<StaffEntity> findByUsernameAndPassword(String username, String password);
}
