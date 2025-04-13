package f5.health.app.repository;

import f5.health.app.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
    // CRUD...
}
