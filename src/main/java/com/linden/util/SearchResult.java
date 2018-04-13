package com.linden.util;

public class SearchResult {
    private class SearchResultCount{
        private long movies;
        private long tvShow;
        private long actors;
        private long all;

        public SearchResultCount(long movies, long tvShow, long actors, long all) {
            this.movies = movies;
            this.tvShow = tvShow;
            this.actors = actors;
            this.all = all;
        }

        public long getMovies() {
            return movies;
        }

        public long getTvShow() {
            return tvShow;
        }

        public long getActors() {
            return actors;
        }

        public long getAll() {
            return all;
        }
    }

}
