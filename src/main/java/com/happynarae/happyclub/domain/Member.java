package com.happynarae.happyclub.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "member")
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long memberId;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "email", nullable = false)
    private String email;
    @Column(name = "emp_num", nullable = false)
    private String empNum;

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY)
    private List<ClubMember> clubMembers = new ArrayList<>();

    // 편의 메서드
    public Member(String name, String email, String empNum) {
        this.name = name;
        this.email = email;
        this.empNum = empNum;
    }

    /**
     * ClubMember가 생성될 때 호출된다.
     * ClubMember <-> Member 양방향 관계 동기화를 위해 필요.
     * 외부에서 함부로 add하지 않고 ClubMember.create(...)를 통해서만 추가되게 유도.
     */
    protected void addClubMemberInternal(ClubMember clubMember) {
        this.clubMembers.add(clubMember);
    }

}
