package com.happynarae.happyclub.repo;

import com.happynarae.happyclub.domain.ClubJoin;
import com.happynarae.happyclub.web.JoinStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ClubJoinRepository extends JpaRepository<ClubJoin, Long> {

    // 1) “이 멤버가 이 클럽에 대기중인지” 체크할 때 사용
    boolean existsByClub_ClubIdAndMember_MemberIdAndStatus(Long clubId, Long memberId, JoinStatus status);

    // 2) 매니저 화면에서 “이 클럽의 PENDING 요청만” 보기
    List<ClubJoin> findByClub_ClubIdAndStatus(Long clubId, JoinStatus status);

    // 3) 내 신청 목록
    List<ClubJoin> findByMember_MemberIdOrderByRequestedAtDesc(Long memberId);

    // 4) 승인할 때 특정 row 찾기
    Optional<ClubJoin> findByClubJoinIdAndClub_ClubId(Long clubJoinId, Long clubId);
}

