package com.happynarae.happyclub.repo;

import com.happynarae.happyclub.domain.ClubMember;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClubMemberRepository extends JpaRepository<ClubMember, Long> {

    // 이미 가입된 멤버인지 체크
    boolean existsByClub_ClubIdAndMember_MemberId(Long clubId, Long memberId);

    // 클럽에 현재 속한 멤버 목록
    List<ClubMember> findByClub_ClubId(Long clubId);
}