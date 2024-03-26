package org.Xsis.service;

import org.Xsis.domain.Movie;
import org.Xsis.dto.MovieDto;

import java.util.List;
import java.util.Optional;

public interface MovieService {

    Movie save(MovieDto movieDto);

    Movie update(MovieDto movieDto);

    void delete(String id);

    List<Movie> findListMovie();

    Optional<Movie> findByMovie(String id);

}
