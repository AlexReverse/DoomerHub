package hub.doomer.search.repository;

import hub.doomer.search.entity.Post;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface PostRepository extends CrudRepository<Post, Integer> {

    //@Query(value = "select p from Post p where p.title ilike :filter")
    @Query(value = "select * from search.t_post where c_title ilike :filter", nativeQuery = true)
    Iterable<Post> findAllByTitleLikeIgnoreCase(@Param("filter") String filter);
}
