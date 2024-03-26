package org.Xsis.web.rest;

import org.Xsis.domain.Movie;
import org.Xsis.dto.MovieDto;
import org.Xsis.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/movies/")
public class MovieController {

    @Autowired
    private MovieService movieService;

    @GetMapping("{id}")
    public MovieDto findByMovie(@PathVariable(name = "id") String id) {
        return toDto(movieService.findByMovie(id).orElseThrow(() -> new RuntimeException("No Movie found with id : " + id)));
    }

    @GetMapping("")
    public List<MovieDto> findList() {
        return movieService.findListMovie().stream().map(this::toDto).collect(Collectors.toList());
    }

    @PostMapping("")
    public MovieDto save(@RequestBody @Valid MovieDto movieDto) {
        return toDto(movieService.save(movieDto));
    }

    @PatchMapping("")
    public MovieDto update(@RequestBody @Valid MovieDto movieDto) {
        return toDto(movieService.update(movieDto));
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable(name = "id") String id) {
        movieService.delete(id);
    }

    private MovieDto toDto(Movie movie) {
        MovieDto movieDto = new MovieDto();
        movieDto.setId(movie.getId());
        movieDto.setTitle(movie.getTitle());
        movieDto.setDescription(movie.getDescription());
        movieDto.setRating(movie.getRating());
        movieDto.setImage(movie.getImage());
        movieDto.setCreatedAt(movie.getCreatedAt().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")));
        if (null != movie.getUpdatedAt()){
            movieDto.setUpdatedAt(movie.getUpdatedAt().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")));
        }
        return movieDto;
    }

}
