package com.example.service;

import com.example.domain.entity.Movie;
import com.example.domain.request.MovieRequest;
import com.example.domain.response.MovieResponse;
import com.example.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MovieService {
    //주입 객체
    private final MovieRepository movieRepository;
    private final LogService logService;

    @Transactional
    public MovieResponse getMovie(long movieId) {
        Movie movie = movieRepository.findById(movieId).orElseThrow(); //반환값이 null이면 Throw
        return MovieResponse.of(movie);
    }

    public List<MovieResponse> getMovies() {
        List<Movie> movies = movieRepository.findByProductionYear(2020);
        return movies.stream().map(MovieResponse::of).toList();
    };

    @Transactional
    public void saveMovie(MovieRequest movieRequest) {
        Movie movie = new Movie(movieRequest.getName(), movieRequest.getProductionYear());
        movieRepository.save(movie);
        logService.saveLog();
    }

    @Transactional
    public void updateMovie(long movieId, MovieRequest movieRequest) {
        //조회가 되는 순간 write 쿼리가 안먹음
        Movie movie = movieRepository.findById(movieId).orElseThrow();

        movie.setName("변경1"); //이름 변경 -> dirty checking
        movie.setName("변경2");
        movie.setName("변경3");
    }

    @Transactional
    public void removeMovie(long movieId) {
        Movie movie = movieRepository.findById(movieId).orElseThrow();
        movieRepository.delete(movie);
    }

}
