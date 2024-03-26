package org.Xsis.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.Xsis.domain.Movie;
import org.Xsis.dto.MovieDto;
import org.Xsis.repository.MovieRepository;
import org.Xsis.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@Transactional
public class MovieServiceImpl implements MovieService {

    @Autowired
    private MovieRepository movieRepository;

    @Override
    public Movie save(MovieDto movieDto) {
        Movie movie = new Movie();
        movie.setTitle(movieDto.getTitle());
        movie.setDescription(movieDto.getDescription());
        movie.setRating(movieDto.getRating());
        movie.setImage(movieDto.getImage());
        movie.setCreatedAt(OffsetDateTime.now());

        return movieRepository.save(movie);
    }

    @Override
    public Movie update(MovieDto movieDto) {

        Movie movie = movieRepository.findById(movieDto.getId()).orElseThrow(() -> new RuntimeException("No Movie Id found with id : " + movieDto.getId()));
        movie.setTitle(movieDto.getTitle());
        movie.setDescription(movieDto.getDescription());
        movie.setRating(movieDto.getRating());
        movie.setImage(movieDto.getImage());
        movie.setUpdatedAt(OffsetDateTime.now());

        return movieRepository.save(movie);
    }

    @Override
    public void delete(String id) {
        Movie movie = movieRepository.findById(id).orElseThrow(() -> new RuntimeException("No Movie Id found with id : " + id));
        movieRepository.delete(movie);
    }

    @Override
    public List<Movie> findListMovie() {
        return movieRepository.findAllByOrderByTitleAsc();
    }

    @Override
    public Optional<Movie> findByMovie(String id) {
        return movieRepository.findById(id);
    }
}
