package bcy.moviespider.dao;

import bcy.moviespider.entity.DoubanInfo;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Author YZ
 * @Date 2018/6/7
 */
public interface DoubanDao extends JpaRepository<DoubanInfo,Integer>{
}
