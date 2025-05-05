package com.example.memorial_application.domain.domain.repository;

import com.example.memorial_application.domain.domain.MemorialApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemorialApplicationRepository extends JpaRepository<MemorialApplication, Long> {

  @Query(value = "update MemorialApplication m set m.state = 'REJECTED' where m.characterId = :characterId and m.memorialApplicationId != :memorialApplicationId")
  void updateStateToRejectedByCharacterId(@Param("memorialApplicationId") Long memorialApplicationId, @Param("characterId") Long characterId);

  @Query(value = "select m from MemorialApplication m order by m.likes desc")
  List<MemorialApplication> findAllSortByLikes();

  @Modifying
  @Query(value = "update MemorialApplication m set m.likes = m.likes + 1 where m.memorialApplicationId = :memorialApplicationId")
  void incrementLikes(@Param("memorialApplicationId") Long memorialApplicationId);

  @Modifying
  @Query(value = "update MemorialApplication m set m.likes = m.likes - 1 where m.memorialApplicationId = :memorialApplicationId")
  void decrementLikes(@Param("memorialApplicationId") Long memorialApplicationId);
}
