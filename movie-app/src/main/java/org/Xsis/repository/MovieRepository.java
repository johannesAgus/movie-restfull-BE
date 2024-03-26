package org.Xsis.repository;

import org.Xsis.domain.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movie, String> {

    List<Movie> findAllByOrderByTitleAsc();


}
