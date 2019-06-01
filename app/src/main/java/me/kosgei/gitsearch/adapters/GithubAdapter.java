package me.kosgei.gitsearch.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.kosgei.gitsearch.R;
import me.kosgei.gitsearch.model.Repo;

public class GithubAdapter extends RecyclerView.Adapter<GithubAdapter.GithubViewHolder> {
    List<Repo> repos;

    public GithubAdapter(List<Repo> repos) {
        this.repos = repos;
    }

    @NonNull
    @Override
    public GithubAdapter.GithubViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new GithubViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.repo,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull GithubAdapter.GithubViewHolder holder, int position) {
        holder.bindRepos(repos.get(position));
    }

    @Override
    public int getItemCount() {
        return repos.size();
    }



    public class GithubViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener  {

        @BindView(R.id.name)
        TextView name;
        @BindView(R.id.description)
        TextView description;
        @BindView(R.id.language)
        TextView language;
        @BindView(R.id.forks)
        TextView forks;
        @BindView(R.id.watchers)
        TextView watchers;
        @BindView(R.id.open)
        Button open;

        Context context;

        public GithubViewHolder(@NonNull View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);

            context = itemView.getContext();
            open.setOnClickListener(this);


        }
        public void bindRepos(Repo repo)
        {
            name.setText(repo.getName());
            description.setText(String.format("Description: %s", repo.getDescription()));
            language.setText(String.format("Language: %s", repo.getLanguage()));
            forks.setText(String.format("Forks: %s", repo.getForks()));
            watchers.setText(String.format("Watchers: %s", repo.getWatchers()));

        }


        @Override
        public void onClick(View v) {
            if (v == open)
            {
                int position = getLayoutPosition();
                context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(repos.get(position).getUrl())));
            }
        }
    }
}

