package com.volleyball.volleyballapi.api.repo;

import com.volleyball.volleyballapi.api.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepo extends JpaRepository<Member, Long> {

    Member findByEmail(String email);

    Member findByPhone(String phone);
}
