package hu.onlinepizzeria.server.dao;

import hu.onlinepizzeria.server.core.model.SchedulingAlgorithms;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

public interface SchedulingRepo extends CrudRepository<SchedulingAlgorithms, Integer> {
    @Modifying
    @Transactional
    @Query(value = "UPDATE scheduling SET is_active=1 WHERE id=:id", nativeQuery = true)
    void setActiveAlgorithm(Integer id);

    @Modifying
    @Transactional
    @Query(value = "UPDATE scheduling SET is_active=0 WHERE :id NOT IN (SELECT * FROM(SELECT id FROM scheduling as S) as S)", nativeQuery = true) //doesnt work? idk why
    void setOtherAlgorithmsNonActive(Integer id);

    @Query(value="SELECT id FROM scheduling", nativeQuery = true)
    ArrayList<Integer> getAlgorithms();

    @Modifying
    @Transactional
    @Query(value = "UPDATE scheduling SET is_active=0", nativeQuery = true)
    void setAllAlgorithmsNonActive();

    @Query(value="SELECT id FROM scheduling WHERE is_active=1", nativeQuery = true)
    Integer getActiveAlgorithm();
}
