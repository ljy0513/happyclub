package com.happynarae.happyclub.repo;

import com.happynarae.happyclub.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
 Member findByEmpNum(String empNum);
}
