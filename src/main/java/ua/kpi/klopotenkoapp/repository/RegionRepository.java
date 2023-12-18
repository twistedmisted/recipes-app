package ua.kpi.klopotenkoapp.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ua.kpi.klopotenkoapp.entity.RegionEntity;
import ua.kpi.klopotenkoapp.repository.projection.RegionNameOnly;

import java.util.List;
import java.util.Optional;

@Repository
public interface RegionRepository extends CrudRepository<RegionEntity, Integer> {

    Optional<RegionEntity> findByName(String name);

    @Query(value = "SELECT e.name as name FROM RegionEntity e")
    List<RegionNameOnly> findProjections();
}
