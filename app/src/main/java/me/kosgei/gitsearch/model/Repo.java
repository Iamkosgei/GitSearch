package me.kosgei.gitsearch.model;

public class Repo {
    private String name,description,language,forks,watchers,url;

    public Repo(String name) {
        this.name = name;
    }

    public Repo(String name, String description, String language, String forks, String watchers, String url) {
        this.name = name;
        this.description = description;
        this.language = language;
        this.forks = forks;
        this.watchers = watchers;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getForks() {
        return forks;
    }

    public void setForks(String forks) {
        this.forks = forks;
    }

    public String getWatchers() {
        return watchers;
    }

    public void setWatchers(String watchers) {
        this.watchers = watchers;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
