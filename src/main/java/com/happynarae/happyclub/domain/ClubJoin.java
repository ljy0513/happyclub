package com.happynarae.happyclub.domain;

import com.happynarae.happyclub.web.JoinStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "club_join")
public class ClubJoin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "club_join_id")
    private Long clubJoinId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "club_id", nullable = false)
    private Club club;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private JoinStatus status;

    @Column(name = "requested_at", nullable = false)
    private LocalDateTime requestedAt;

    @Column(name = "decided_at")
    private LocalDateTime decidedAt;

    @Column(name = "request_reason")
    private String requestReason;

    @Column(name = "decide_reason")
    private String decideReason;


    /**
     * 새로 "신청"
     * 거절/탈퇴 후 새로신청할때는 데이터 새로 생성
     */
    public static ClubJoin request(Club club, Member member, String requestReason) {
        ClubJoin join = new ClubJoin();
        join.club = club;
        join.member = member;
        join.status = JoinStatus.PENDING;
        join.requestedAt = LocalDateTime.now();
        join.requestReason = requestReason;
        return join;
    }

    /** 승인 */
    public void approve(String decideReason) {
        this.status = JoinStatus.APPROVED;
        this.decidedAt = LocalDateTime.now();
        this.decideReason = decideReason;
    }

    /** 거절 */
    public void reject(String decideReason) {
        this.status = JoinStatus.REJECTED;
        this.decidedAt = LocalDateTime.now();
        this.decideReason = decideReason;
    }

    /** 탈퇴(이 사이클의 끝) */
    public void leave(String reason) {
        this.status = JoinStatus.LEAVE;
        this.decidedAt = LocalDateTime.now();
        this.decideReason = reason;
    }
}
