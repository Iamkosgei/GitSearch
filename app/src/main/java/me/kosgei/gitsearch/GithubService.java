package me.kosgei.gitsearch;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import me.kosgei.gitsearch.model.Repo;
import me.kosgei.gitsearch.model.User;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class GithubService {

    public static void getUserProfile(String name, Callback callback) {
        OkHttpClient client = new OkHttpClient.Builder().build();

        HttpUrl.Builder urlBuilder = HttpUrl.parse("https://api.github.com/users/"+name).newBuilder();
        urlBuilder.addQueryParameter("access_token",Constants.GITHUB_TOKEN);
        String url = urlBuilder.build().toString();
        Request request = new Request.Builder()
                .url(url)
                .build();
        Call call = client.newCall(request);
        call.enqueue(callback);
    }

    public User processProfileResults(Response response) {
        User user;
        String login="",avatar = "",url="",name="",blog="",location="",email="",bio="",repos="",followers="",following="";
        try {
            String jsonData = response.body().string();
            JSONObject profileJson = new JSONObject(jsonData);

            login = profileJson.getString("login");
            avatar = profileJson.getString("avatar_url");
            url= profileJson.getString("url");
            name= profileJson.getString("name");
            blog= profileJson.getString("blog");
            location= profileJson.getString("location");
            email= profileJson.getString("email");
            bio= profileJson.getString("bio");
            repos= profileJson.getString("public_repos");
            followers= profileJson.getString("followers");
            following= profileJson.getString("following");

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        user = new User(login,avatar,url,name,blog,location,email,bio,repos,followers, following);
        return user;
    }



    public static void getUserRepos(String name, Callback callback) {
        OkHttpClient client = new OkHttpClient.Builder().build();

        HttpUrl.Builder urlBuilder = HttpUrl.parse("https://api.github.com/users/"+name+"/repos").newBuilder();
        urlBuilder.addQueryParameter("access_token",Constants.GITHUB_TOKEN);
        String url = urlBuilder.build().toString();
        Request request = new Request.Builder()
                .url(url)
                .build();
        Call call = client.newCall(request);
        call.enqueue(callback);
    }

    public ArrayList<Repo> processReposResults(Response response) {

        ArrayList<Repo> repos = new ArrayList<>();
        try {
            String jsonData = response.body().string();
           JSONArray reposJsonArray = new JSONArray(jsonData);

           for (int i=0; i<reposJsonArray.length(); i++)
           {
               JSONObject repo = reposJsonArray.getJSONObject(i);

               String name = repo.getString("name");
               String description= repo.getString("description");
               String language= repo.getString("language");
               String forks= repo.getString("forks");
               String watchers= repo.getString("watchers_count");
               String url= repo.getString("html_url");

               Repo newRepo = new Repo(name,description,language,forks,watchers,url);
               repos.add(newRepo);
           }

        } catch (IOException e) {
            e.printStackTrace();
        }
        catch (JSONException e) {
            e.printStackTrace();
        }

        return repos;
    }
}
