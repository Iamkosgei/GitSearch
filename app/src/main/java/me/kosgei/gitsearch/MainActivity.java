package me.kosgei.gitsearch;

import android.app.ProgressDialog;
import android.os.Build;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.view.MenuInflater;
import android.view.View;

import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;

import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;

import androidx.core.view.MenuItemCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Menu;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.kosgei.gitsearch.adapters.GithubAdapter;
import me.kosgei.gitsearch.model.Repo;
import me.kosgei.gitsearch.model.User;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    TextView followers,following,nameTv,blog,url,location,reposTv,bio;
    ImageView profile_image;

    private GithubAdapter githubAdapter;


    private User user;
    ArrayList<Repo> repos;

    String username ="Iamkosgei";

    ProgressDialog progressDialog;

    @BindView(R.id.recycler)
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        createProgressDialog();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getRepos(username);
                getUserInfo(username);
            }
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        View view = navigationView.getHeaderView(0);
        followers = view.findViewById(R.id.followers);
        following = view.findViewById(R.id.following);
        nameTv = view.findViewById(R.id.name);
        blog = view.findViewById(R.id.blog);
        url = view.findViewById(R.id.url);
        location = view.findViewById(R.id.location);
        reposTv = view.findViewById(R.id.repos);
        bio = view.findViewById(R.id.bio);
        profile_image = view.findViewById(R.id.profile_image);



        getRepos(username);
        getUserInfo(username);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_search, menu);
        ButterKnife.bind(this);

        MenuItem menuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menuItem);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                username = query;
                getRepos(username);
                getUserInfo(username);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        return  true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return super.onOptionsItemSelected(item);
    }





    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void getUserInfo(String name)
    {
        final GithubService githubService = new GithubService();

        githubService.getUserProfile(name, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.code() == 200)
                {
                    user= githubService.processProfileResults(response);

                    MainActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            followers.setText(user.getFollowers());
                            following.setText(user.getFollowing());
                            nameTv.setText(user.getName());
                            blog.setText(String.format("Blog: %s", user.getBlog()));
                            url.setText(String.format("URL: %s", user.getUrl()));
                            location.setText(user.getLocation());
                            reposTv.setText(user.getRepos());
                            bio.setText(user.getBio());

                            Picasso.get().load(user.getAvatar()).into(profile_image);

                        }
                    });
                }


            }
        });
    }

    public void getRepos(String name)
    {
        progressDialog.show();

        final GithubService githubService = new GithubService();

        githubService.getUserRepos(name, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                if (response.code() == 200)
                {
                    repos = githubService.processReposResults(response);

                    MainActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {


                            githubAdapter = new GithubAdapter(repos);
                            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                            recyclerView.setLayoutManager(layoutManager);
                            recyclerView.setHasFixedSize(true);
                            recyclerView.setAdapter(githubAdapter);

                            progressDialog.hide();


                        }
                    });

                }

                else
                {
                    MainActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            progressDialog.hide();
                            Toast.makeText(MainActivity.this, "User Not Found", Toast.LENGTH_SHORT).show();
                        }
                    });

                }



            }
        });

    }

    private void createProgressDialog() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Loading...");
        progressDialog.setMessage("Fetching Data...");
        progressDialog.setCancelable(false);
    }
}
