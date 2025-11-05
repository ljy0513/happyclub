package com.happynarae.happyclub.repo;

import com.happynarae.happyclub.domain.Club;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClubRepository extends JpaRepository<Club, Long> {
    /** 상세 화면에서 N+1 방지용 매니저 함께 로딩 */
    @EntityGraph(attributePaths = "manager")
    Optional<Club> findByClubId(Long clubId);
}
