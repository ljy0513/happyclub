package com.happynarae.happyclub.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "club")
public class Club {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "club_id")
    private Long clubId;

    @Column(name = "club_name", nullable = false)
    private String clubName;

    @Column
    private String description;

    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @Column(name = "updated_date")
    private LocalDateTime updatedDate;

    @Column(name = "is_active", columnDefinition = "BOOLEAN DEFAULT TRUE")
    private boolean isActive = true;

    /**
     * 현재 이 클럽에 실제로 소속된 멤버들
     * (ClubMember는 "현재 멤버" 전용 테이블이니까 이 리스트도 현재 기준)
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "manager_id", nullable = false)
    private Member manager;

    @OneToMany(mappedBy = "club", fetch = FetchType.LAZY)
    private List<ClubMember> clubMembers = new ArrayList<>();

    // 편의 메서드
    public Club(String clubName, String description, Member manager) {
        this.clubName = clubName;
        this.description = description;
        this.manager = manager;
        this.createdDate = LocalDateTime.now();
        this.updatedDate = LocalDateTime.now();
    }

    /**
     * 동호회의 매니저를 변경한다.
     */
    public void changeManager(Member newManager) {
        this.manager = newManager;
        this.updatedDate = LocalDateTime.now();
    }

    /**
     * ClubMember가 생성될 때 호출된다.
     * ClubMember <-> Club 양방향 관계 동기화를 위해 필요.
     * 외부에서 함부로 add하지 않고 ClubMember.create(...)를 통해서만 추가되게 유도.
     */
    protected void addClubMemberInternal(ClubMember clubMember) {
        this.clubMembers.add(clubMember);
    }
}
