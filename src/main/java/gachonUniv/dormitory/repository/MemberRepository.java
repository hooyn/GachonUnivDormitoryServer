package gachonUniv.dormitory.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import gachonUniv.dormitory.domain.Member;
import gachonUniv.dormitory.domain.QMember;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.UUID;

import static gachonUniv.dormitory.domain.QMember.member;

@Repository
@RequiredArgsConstructor
public class MemberRepository {
    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    public void save(Member member){
        em.persist(member);
    }

    public List<Member> findAll(){
        return queryFactory
                .selectFrom(member)
                .fetch();
    }

    public Member findByUuid(UUID uuid){
        return queryFactory
                .select(member)
                .from(member)
                .where(member.id.eq(uuid))
                .fetchOne();
    }
}
