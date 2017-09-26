package pe.edu.upc.catchup.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.androidnetworking.widget.ANImageView;

import java.util.List;

import pe.edu.upc.catchup.R;
import pe.edu.upc.catchup.models.Article;

/**
 * Created by juanjogramo on 23/09/17.
 */

public class ArticlesAdapter extends RecyclerView.Adapter<ArticlesAdapter.ViewHolder>{

    private List<Article> articles;

    public ArticlesAdapter(List<Article> articles) {
        this.articles = articles;
    }

    public ArticlesAdapter() {
    }

    @Override
    public ArticlesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater
        .from(parent.getContext())
        .inflate(R.layout.card_article, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Article article = articles.get(position);
        holder.pictureANImageView.setDefaultImageResId(R.mipmap.ic_launcher);
        holder.pictureANImageView.setErrorImageResId(R.mipmap.ic_launcher);
        holder.pictureANImageView.setImageUrl(article.getUrlToImage());
        holder.titleTextView.setText(article.getTitle());
        holder.moreTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: Start About Article Activity
            }
        });
    }


    @Override
    public int getItemCount() {
        return articles.size();
    }

    public List<Article> getArticles() {
        return articles;
    }

    public ArticlesAdapter setArticles(List<Article> articles) {
        this.articles = articles;
        return this;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ANImageView pictureANImageView;
        TextView titleTextView;
        TextView moreTextView;
        public ViewHolder(View itemView) {
            super(itemView);
            pictureANImageView = (ANImageView) itemView.findViewById(R.id.pictureANImageView);
            titleTextView = (TextView) itemView.findViewById(R.id.titleTextView);
            moreTextView = (TextView) itemView.findViewById(R.id.moreTextView);
        }
    }
}
