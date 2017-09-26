package pe.edu.upc.catchup.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import pe.edu.upc.catchup.R;
import pe.edu.upc.catchup.adapters.ArticlesAdapter;
import pe.edu.upc.catchup.models.Article;
import pe.edu.upc.catchup.models.Source;

import static pe.edu.upc.catchup.network.NewsApiService.ARTICLES_URL;

public class SourceArticleActivity extends AppCompatActivity {
    RecyclerView articlesRecyclerView;
    ArticlesAdapter articlesAdapter;
    RecyclerView.LayoutManager articlesLayoutManager;
    List<Article> articles;
    Source source;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_source_article);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        source = Source.from(getIntent().getExtras());
        articlesRecyclerView = (RecyclerView) findViewById(R.id.articlesRecylcerView);
        articles = new ArrayList<>();
        articlesAdapter = new ArticlesAdapter(articles);
        articlesLayoutManager = new GridLayoutManager(this, 2);
        articlesRecyclerView.setAdapter(articlesAdapter);
        articlesRecyclerView.setLayoutManager(articlesLayoutManager);
        updateArticles();
    }

    private void updateArticles() {
        AndroidNetworking.get(ARTICLES_URL)
                .addQueryParameter("source",source.getId())
                .addQueryParameter("apiKey", getString(R.string.news_api_key))
                .setTag(getString(R.string.app_name))
                .setPriority(Priority.LOW)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if ("error".equalsIgnoreCase(response.getString("status"))){
                                Log.d(getString(R.string.app_name),response.getString("message"));
                                return;
                            }
                            articles = Article.from(response.getJSONArray("articles"),source);
                            articlesAdapter.setArticles(articles);
                            articlesAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.d(getString(R.string.app_name), anError.getLocalizedMessage());
                    }
                });

    }

}
