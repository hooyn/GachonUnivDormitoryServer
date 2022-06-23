package gachonUniv.dormitory.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import gachonUniv.dormitory.domain.Post;
import gachonUniv.dormitory.domain.QPost;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.UUID;

import static gachonUniv.dormitory.domain.QPost.post;

@Repository
@RequiredArgsConstructor
public class PostRepository {
    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    public Long save(Post post){
        em.persist(post);
        return post.getId();
    }

    public List<Post> findAll(){
        return queryFactory
                .selectFrom(post)
                .fetch();
    }

    public Post findOne(Long id){
        return queryFactory
                .selectFrom(post)
                .where(post.id.eq(id))
                .fetchOne();
    }

    public List<Post> findByUuid(String uuid){
        return queryFactory
                .selectFrom(post)
                .where(post.member.id.eq(UUID.fromString(uuid)))
                .fetch();
    }

    public void update(Long id, String title, String content, String[] hash){
        long execute = queryFactory
                .update(post)
                .set(post.id, id)
                .set(post.title, title)
                .set(post.content, content)
                .set(post.hash_first, hash[0])
                .set(post.hash_second, hash[1])
                .set(post.hash_third, hash[2])
                .execute();
    }

    public void delete(Long id){
        queryFactory
                .delete(post)
                .where(post.id.eq(id))
                .execute();
    }

    public boolean checkAuthorization(String uuid, Long id){
        Post post = queryFactory
                .selectFrom(QPost.post)
                .where(QPost.post.member.id.eq(UUID.fromString(uuid)).and(
                        QPost.post.id.eq(id)
                ))
                .fetchOne();

        if(post!=null) return true;
        else return false;
    }
}