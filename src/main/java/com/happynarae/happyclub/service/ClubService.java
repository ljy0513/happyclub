package com.happynarae.happyclub.service;


import com.happynarae.happyclub.domain.Club;
import com.happynarae.happyclub.domain.Member;
import com.happynarae.happyclub.repo.ClubRepository;
import com.happynarae.happyclub.repo.MemberRepository;
import com.happynarae.happyclub.web.ClubCreateForm;
import com.happynarae.happyclub.web.ClubDetailDto;
import com.happynarae.happyclub.web.ClubListItemDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ClubService {
    private final ClubRepository clubRepository;
    private final MemberRepository memberRepository;

    public List<ClubListItemDto> getClubList() {
        return clubRepository.findAll().stream()
                .map(c -> new ClubListItemDto(
                        c.getClubId(),
                        c.getClubName(),
                        (c.getManager() != null ? c.getManager().getName() : ""),
                        c.getCreatedDate()
                ))
                .toList();
    }

    /** 생성 — 엔티티 생성자 사용 */
    @Transactional
    public Long createClub(ClubCreateForm form) {
        Member manager = memberRepository.findById(form.managerId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 매니저입니다. id=" + form.managerId()));

        Club club = new Club(form.clubName().trim(), form.description(), manager);
        Club saved = clubRepository.save(club);
        return saved.getClubId();
    }

    /** 상세 */
    public ClubDetailDto getClubDetail(Long id) {
        Club c = clubRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("클럽이 존재하지 않습니다. id=" + id));
        return new ClubDetailDto(
                c.getClubId(),
                c.getClubName(),
                (c.getManager() != null ? c.getManager().getName() : ""),
                c.getDescription(),
                c.isActive(),
                c.getCreatedDate()
        );
    }


}
