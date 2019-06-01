package me.kosgei.gitsearch.model;

public class User {
    private String login,avatar,url,name,blog,location,email,bio,repos,followers,following;

    public User() {
    }

    public User(String login, String avatar, String url, String name, String blog, String location, String email, String bio, String repos, String followers, String following) {
        this.login = login;
        this.avatar = avatar;
        this.url = url;
        this.name = name;
        this.blog = blog;
        this.location = location;
        this.email = email;
        this.bio = bio;
        this.repos = repos;
        this.followers = followers;
        this.following = following;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBlog() {
        return blog;
    }

    public void setBlog(String blog) {
        this.blog = blog;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getRepos() {
        return repos;
    }

    public void setRepos(String repos) {
        this.repos = repos;
    }

    public String getFollowers() {
        return followers;
    }

    public void setFollowers(String followers) {
        this.followers = followers;
    }

    public String getFollowing() {
        return following;
    }

    public void setFollowing(String following) {
        this.following = following;
    }
}
