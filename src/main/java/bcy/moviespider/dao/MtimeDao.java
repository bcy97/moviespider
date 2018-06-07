package bcy.moviespider.dao;

import bcy.moviespider.entity.MTime;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MtimeDao extends JpaRepository<MTime, Integer> {
}
