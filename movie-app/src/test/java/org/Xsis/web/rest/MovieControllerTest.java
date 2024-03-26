package org.Xsis.web.rest;

import net.minidev.json.JSONObject;
import org.Xsis.domain.Movie;
import org.Xsis.dto.MovieDto;
import org.Xsis.service.MovieService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class MovieControllerTest {

    private static final String BASE_URL = "/api/v1/movies/";

    private MockMvc mockMvc;

    @InjectMocks
    private MovieController movieController;

    @Mock
    private MovieService movieService;

    @Captor
    private ArgumentCaptor<MovieDto> argumentCaptor;

    @Captor
    private ArgumentCaptor<String> movieIdArgumentCaptor;

    @Before
    public void before(){
        mockMvc = MockMvcBuilders.standaloneSetup(movieController).build();
    }

    private Movie getMovie(){
        Movie movie = new Movie();
        movie.setId("1");
        movie.setTitle("Pengabdi Setan 2 Comunion");
        movie.setDescription("Film horor tahun 2017");
        movie.setRating(7);
        movie.setImage("");
        movie.setCreatedAt(OffsetDateTime.now());
        movie.setUpdatedAt(OffsetDateTime.now());
        return movie;
    }

    private MovieDto getMovieDto() {
        MovieDto movieDto = new MovieDto();
        movieDto.setId("1");
        movieDto.setTitle("Pengabdi Setan 2 Comunion");
        movieDto.setDescription("Film horor tahun 2017");
        movieDto.setRating(7);
        movieDto.setImage("");
        movieDto.setCreatedAt(OffsetDateTime.now().toString());
        movieDto.setUpdatedAt(OffsetDateTime.now().toString());
        return movieDto;
    }

    @Test
    public void testFindByMovie() throws Exception{

        when(movieService.findByMovie("1")).thenAnswer((Answer<Optional<Movie>>) invocationOnMock->Optional.of(getMovie()));

        mockMvc.perform(MockMvcRequestBuilders.get(BASE_URL+"1"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("Pengabdi Setan 2 Comunion"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value("Film horor tahun 2017"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.rating").value(7))
                .andExpect(MockMvcResultMatchers.jsonPath("$.image").value(""));

    }

    @Test
    public void testFindListMovie() throws Exception{

        when(movieService.findListMovie()).then(invocationOnMock -> {
            List<Movie> movieList = new ArrayList<>();
            movieList.add(getMovie());
            return movieList;
        });

        mockMvc.perform(MockMvcRequestBuilders.get(BASE_URL+""))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].id").value("1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].title").value("Pengabdi Setan 2 Comunion"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].description").value("Film horor tahun 2017"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].rating").value(7))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].image").value(""));

    }

    @Test
    public void testSave() throws Exception{
        JSONObject requestData = new JSONObject();
        requestData.put("title", "Pengabdi Setan 2 Comunion");
        requestData.put("description", "Film horor tahun 2017");
        requestData.put("rating", 7);
        requestData.put("image", "");

        when(movieService.save(any())).thenReturn(getMovie());

        mockMvc.perform(MockMvcRequestBuilders.post(BASE_URL + "")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestData.toString()))
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("Pengabdi Setan 2 Comunion"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value("Film horor tahun 2017"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.rating").value(7))
                .andExpect(MockMvcResultMatchers.jsonPath("$.image").value(""));

        verify(movieService).save(argumentCaptor.capture());

        Assert.assertNotNull(argumentCaptor.getValue());
        Assert.assertEquals("Pengabdi Setan 2 Comunion", argumentCaptor.getValue().getTitle());
        Assert.assertEquals("Film horor tahun 2017", argumentCaptor.getValue().getDescription());
        Assert.assertEquals(10, 3, 7);
        Assert.assertEquals("", argumentCaptor.getValue().getImage());
    }

    @Test
    public void testUpdate() throws Exception {
        JSONObject requestData = new JSONObject();
        requestData.put("id", "1");
        requestData.put("title", "Pengabdi Setan 2 Comunion");
        requestData.put("description", "Film horor tahun 2017");
        requestData.put("rating", 7);
        requestData.put("image", "");

        when(movieService.update(any())).thenReturn(getMovie());

        mockMvc.perform(MockMvcRequestBuilders.patch(BASE_URL + "")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestData.toString()))
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value("1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("Pengabdi Setan 2 Comunion"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value("Film horor tahun 2017"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.rating").value(7))
                .andExpect(MockMvcResultMatchers.jsonPath("$.image").value(""));

        verify(movieService).update(argumentCaptor.capture());

        Assert.assertNotNull(argumentCaptor.getValue());
        Assert.assertEquals("1", argumentCaptor.getValue().getId());
        Assert.assertEquals("Pengabdi Setan 2 Comunion", argumentCaptor.getValue().getTitle());
        Assert.assertEquals("Film horor tahun 2017", argumentCaptor.getValue().getDescription());
        Assert.assertEquals(10, 3, 7);
        Assert.assertEquals("", argumentCaptor.getValue().getImage());

    }

    @Test
    public void testDelete() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.delete(BASE_URL + "/1"))
                .andExpect(MockMvcResultMatchers.status().isOk());

        verify(movieService).delete(movieIdArgumentCaptor.capture());

        String movieId = movieIdArgumentCaptor.getValue();
        Assert.assertEquals("1", movieId);
    }

}
