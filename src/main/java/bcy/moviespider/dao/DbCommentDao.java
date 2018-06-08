package bcy.moviespider.dao;

import bcy.moviespider.entity.DbComment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DbCommentDao extends JpaRepository<DbComment, Integer> {
}
