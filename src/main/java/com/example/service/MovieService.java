package com.example.service;

import com.example.domain.entity.Log;
import com.example.domain.entity.Movie;
import com.example.domain.request.MovieRequest;
import com.example.domain.response.MovieResponse;
import com.example.repository.LogRepository;
import com.example.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MovieService {
    private final MovieRepository movieRepository;
    private final LogRepository logRepository;

    public MovieResponse getMovie(long movieId) {
        Movie movie = movieRepository.findById(movieId).orElseThrow();
        return MovieResponse.of(movie);
    }

    public List<MovieResponse> getMovies(Integer overYear) {
        return List.of();
    };

    @Transactional
    public void saveMovie(MovieRequest movieRequest) {
        Movie movie = new Movie(movieRequest.getName(), movieRequest.getProductionYear());
        movieRepository.save(movie);

        Log log = new Log();
        logRepository.save(log);
    }

    public void updateMovie(long movieId, MovieRequest movieRequest) {
    }

    public void removeMovie(long movieId, MovieRequest movieRequest) {
    }

}
