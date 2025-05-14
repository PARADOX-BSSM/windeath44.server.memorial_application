package com.example.memorial_application.domain.domain.repository;

import com.example.memorial_application.domain.domain.MemorialApplication;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemorialApplicationRepository extends JpaRepository<MemorialApplication, Long> {

  @Modifying
  @Query(value = "update MemorialApplication m set m.state = 'REJECTED' where m.characterId = :characterId and m.memorialApplicationId != :memorialApplicationId")
  void updateStateToRejectedByCharacterId(@Param("memorialApplicationId") Long memorialApplicationId, @Param("characterId") Long characterId);

  @Query(value = "select m from MemorialApplication m order by m.likes desc")
  List<MemorialApplication> findAllSortByLikes();

  @Query(value = "select m from MemorialApplication m where m.memorialApplicationId > :cursorId order by m.memorialApplicationId asc limit :size")
  List<MemorialApplication> findAllByCursor(@Param("cursorId") Long cursorId, @Param("size") Long size);
}
