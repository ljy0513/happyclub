package com.happynarae.happyclub.domain;

import com.happynarae.happyclub.web.JoinStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Entity
@Table(
        name = "club_member",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"club_id", "member_id"})
        }
)
public class ClubMember {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long clubMemberId;

    // ClubMember 가 관계의 주인 (외래키를 가짐)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "club_id", nullable = false)
    private Club club;

    // ClubMember 가 관계의 주인 (외래키를 가짐)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Column(name = "joined_at", nullable = false)
    private LocalDateTime joinedAt;

    @Column(name = "modified_at")
    private LocalDateTime modifiedAt;

    // 편의 메서드
    // 생성 전용 팩토리 메서드
    /**
     * ClubMember를 올바르게 생성하는 유일한 입구.
     * - club, member 연결
     * - 양방향 관계 동기화(컬렉션 추가)
     * - 기본 상태값 세팅
     *
     * 서비스 레이어에서는 이걸 사용해서 ClubMember를 만들고
     * 실제 가입 시점에만 만든다 (승인 시점)
     */
    public static ClubMember create(Club club, Member member) {
        ClubMember cm = new ClubMember();
        cm.club = club;
        cm.member = member;
        cm.joinedAt = LocalDateTime.now();
        cm.modifiedAt = LocalDateTime.now();

        // 양방향 동기화
        club.addClubMemberInternal(cm);
        member.addClubMemberInternal(cm);

        return cm;
    }

}

