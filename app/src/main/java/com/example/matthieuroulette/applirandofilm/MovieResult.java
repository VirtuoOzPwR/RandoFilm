package com.example.matthieuroulette.applirandofilm;

public class MovieResult {
    private final String backdropPath;
    private final String originalTitle;
    private final int id;
    private final String popularity;
    private final String posterPath;
    private final String releaseDate;
    private final String title;
    private final String overview;
    
    public MovieResult(Builder builder) {
        backdropPath = builder.backdropPath;
        originalTitle = builder.originalTitle;
        id = builder.id;
        popularity = builder.popularity;
        posterPath = builder.posterPath;
        releaseDate = builder.releaseDate;
        overview = builder.overview;
        title = builder.title;
    }
    
    
    public static class Builder {
        private String backdropPath;
        private String originalTitle;
        private int id;
        private String popularity;
        private String posterPath;
        private String releaseDate;
        private String overview;
        private String title;
        
        public Builder(int id, String title) {
            this.id = id;
            this.title = title;
        }

        public Builder setBackdropPath(String backdropPath) {
            this.backdropPath = backdropPath;
            return this;
        }

        public Builder setOverview(String overview) {
            this.overview = overview;
            return this;
        }
        public Builder setOriginalTitle(String originalTitle) {
            this.originalTitle = originalTitle;
            return this;
        }

        public Builder setId(int id) {
            this.id = id;
            return this;
        }

        public Builder setPopularity(String popularity) {
            this.popularity = popularity;
            return this;
        }

        public Builder setPosterPath(String posterPath) {
            this.posterPath = posterPath;
            return this;
        }

        public Builder setReleaseDate(String releaseDate) {
            this.releaseDate = releaseDate;
            return this;
        }

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }
        
        public MovieResult build() {
            return new MovieResult(this);
        }
        
    }

    
    public static Builder newBuilder(int id, String title) {
        return new Builder(id, title);
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public String getOverview() {
        return overview;
    }
    public int getId() {
        return id;
    }

    public String getPopularity() {
        return popularity;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public String getTitle() {
        return title;
    }
    
    @Override
    public String toString() {
        return getTitle();
    }
    
}
