package bcy.moviespider.dao;

import bcy.moviespider.entity.MTimeComment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MtimeCommentDao extends JpaRepository<MTimeComment, Integer> {
}
