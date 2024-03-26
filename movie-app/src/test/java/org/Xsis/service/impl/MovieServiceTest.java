package org.Xsis.service.impl;

import org.Xsis.domain.Movie;
import org.Xsis.dto.MovieDto;
import org.Xsis.repository.MovieRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;
import org.mockito.stubbing.Answer;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class MovieServiceTest {

    @InjectMocks
    private MovieServiceImpl movieService;

    @Mock
    private MovieRepository movieRepository;

    @Captor
    private ArgumentCaptor<Movie> movieArgumentCaptor;

    @Before
    public void before() {
        MockitoAnnotations.initMocks(this);
    }

    private Movie getMovie() {
        Movie movie = new Movie();
        movie.setId("1");
        movie.setTitle("Pengabdi Setan 2 Comunion");
        movie.setDescription("Adalah sebuah film horor Indonesia tahun 2022 yang disutradarai dan ditulis oleh Joko Anwar sebagai sekuel dari film tahun 2017, Pengabdi Setan.");
        movie.setRating(7);
        movie.setImage("");
        movie.setCreatedAt(OffsetDateTime.now());
        movie.setUpdatedAt(OffsetDateTime.now());
        return movie;
    }

    private Optional<Movie> getOneAnswer() {
        Movie movie = new Movie();
        movie.setId("1");
        movie.setTitle("Pengabdi Setan 2 Comunion");
        movie.setDescription("Adalah sebuah film horor Indonesia tahun 2022 yang disutradarai dan ditulis oleh Joko Anwar sebagai sekuel dari film tahun 2017, Pengabdi Setan.");
        movie.setRating(7);
        movie.setImage("");
        movie.setCreatedAt(OffsetDateTime.now());
        movie.setUpdatedAt(OffsetDateTime.now());
        return Optional.of(movie);
    }

    @Test
    public void testFindByMovie() {
        Mockito.when(movieRepository.findById("1")).thenReturn(Optional.of(getMovie()));
        Optional<Movie> movie = movieService.findByMovie("1");
        Assert.assertTrue(movie.isPresent());
        Assert.assertEquals("1", movie.get().getId());
        Assert.assertEquals("Pengabdi Setan 2 Comunion", movie.get().getTitle());
        Assert.assertEquals("Adalah sebuah film horor Indonesia tahun 2022 yang disutradarai dan ditulis oleh Joko Anwar sebagai sekuel dari film tahun 2017, Pengabdi Setan.", movie.get().getDescription());
        Assert.assertEquals(10, 3, 7);
        Assert.assertEquals("", movie.get().getImage());
        Assert.assertNotNull(movie.get().getCreatedAt());
        Assert.assertNotNull(movie.get().getUpdatedAt());
    }

    @Test
    public void testFindListMovie() {
        Movie movie = getMovie();
        List<Movie> movieArrayList = new ArrayList<>();
        movieArrayList.add(movie);

        Mockito.when(movieRepository.findAllByOrderByTitleAsc()).thenReturn(movieArrayList);
        List<Movie> movieList = movieService.findListMovie();
        Assert.assertEquals(1, movieList.size());
        Assert.assertEquals("1", movieList.get(0).getId());
        Assert.assertEquals("Pengabdi Setan 2 Comunion", movieList.get(0).getTitle());
        Assert.assertEquals("Adalah sebuah film horor Indonesia tahun 2022 yang disutradarai dan ditulis oleh Joko Anwar sebagai sekuel dari film tahun 2017, Pengabdi Setan.", movieList.get(0).getDescription());
        Assert.assertEquals(10, 3, 7);
        Assert.assertEquals("", movieList.get(0).getImage());
        Assert.assertNotNull(movieList.get(0).getCreatedAt());
        Assert.assertNotNull(movieList.get(0).getUpdatedAt());
    }

    @Test
    public void testSave() {

        MovieDto movieDto = new MovieDto();
        movieDto.setTitle("Pengabdi Setan 2 Comunion");
        movieDto.setDescription("Adalah sebuah film horor Indonesia tahun 2022 yang disutradarai dan ditulis oleh Joko Anwar sebagai sekuel dari film tahun 2017, Pengabdi Setan.");
        movieDto.setRating(7);
        movieDto.setImage("");
        movieDto.setCreatedAt(OffsetDateTime.now().toString());
        movieService.save(movieDto);

        verify(movieRepository).save(movieArgumentCaptor.capture());

        Movie movieCapture = movieArgumentCaptor.getValue();
        Assert.assertNotNull(movieCapture);
        Assert.assertEquals("Pengabdi Setan 2 Comunion", movieCapture.getTitle());
        Assert.assertEquals("Adalah sebuah film horor Indonesia tahun 2022 yang disutradarai dan ditulis oleh Joko Anwar sebagai sekuel dari film tahun 2017, Pengabdi Setan.", movieCapture.getDescription());
        Assert.assertEquals(10, 3, 7);
        Assert.assertEquals("", movieCapture.getImage());

    }

    @Test
    public void testUpdate(){

        MovieDto movieDto = new MovieDto();
        movieDto.setId("1");
        movieDto.setTitle("Pengabdi Setan 2 Comunion");
        movieDto.setDescription("Adalah sebuah film horor Indonesia tahun 2022 yang disutradarai dan ditulis oleh Joko Anwar sebagai sekuel dari film tahun 2017, Pengabdi Setan.");
        movieDto.setRating(7);
        movieDto.setImage("");
        movieDto.setUpdatedAt(OffsetDateTime.now().toString());

        when(movieRepository.findById("1")).then((Answer<Optional<Movie>>) invocationOnMock -> getOneAnswer());

        movieService.update(movieDto);

        verify(movieRepository).save(movieArgumentCaptor.capture());
        Movie movieCapture = movieArgumentCaptor.getValue();
        Assert.assertNotNull(movieCapture);
        Assert.assertEquals("Pengabdi Setan 2 Comunion", movieCapture.getTitle());
        Assert.assertEquals("Adalah sebuah film horor Indonesia tahun 2022 yang disutradarai dan ditulis oleh Joko Anwar sebagai sekuel dari film tahun 2017, Pengabdi Setan.", movieCapture.getDescription());
        Assert.assertEquals(10, 3, 7);
        Assert.assertEquals("", movieCapture.getImage());

    }

    @Test(expected = RuntimeException.class)
    public void testUpdateRuntimeException(){

        MovieDto movieDto = new MovieDto();
        movieDto.setId("1");
        movieDto.setTitle("Pengabdi Setan 2 Comunion");
        movieDto.setDescription("Adalah sebuah film horor Indonesia tahun 2022 yang disutradarai dan ditulis oleh Joko Anwar sebagai sekuel dari film tahun 2017, Pengabdi Setan.");
        movieDto.setRating(7);
        movieDto.setImage("");

        when(movieRepository.findById("2")).then((Answer<Optional<Movie>>) invocationOnMock -> getOneAnswer());

        movieService.update(movieDto);

    }

    @Test
    public void testDelete(){

        when(movieRepository.findById("1")).then((Answer<Optional<Movie>>) invocationOnMock -> getOneAnswer());

        movieService.delete("1");

        verify(movieRepository).delete(movieArgumentCaptor.capture());
        Movie movieCapture = movieArgumentCaptor.getValue();
        Assert.assertNotNull(movieCapture);
        Assert.assertEquals("Pengabdi Setan 2 Comunion", movieCapture.getTitle());
        Assert.assertEquals("Adalah sebuah film horor Indonesia tahun 2022 yang disutradarai dan ditulis oleh Joko Anwar sebagai sekuel dari film tahun 2017, Pengabdi Setan.", movieCapture.getDescription());
        Assert.assertEquals(10, 3, 7);
        Assert.assertEquals("", movieCapture.getImage());

    }

    @Test(expected = RuntimeException.class)
    public void testDeleteRuntimeException(){

        MovieDto movieDto = new MovieDto();
        movieDto.setId("1");
        movieDto.setTitle("Pengabdi Setan 2 Comunion");
        movieDto.setDescription("Adalah sebuah film horor Indonesia tahun 2022 yang disutradarai dan ditulis oleh Joko Anwar sebagai sekuel dari film tahun 2017, Pengabdi Setan.");
        movieDto.setRating(7);
        movieDto.setImage("");

        when(movieRepository.findById("2")).then((Answer<Optional<Movie>>) invocationOnMock -> getOneAnswer());

        movieService.update(movieDto);

    }


}
