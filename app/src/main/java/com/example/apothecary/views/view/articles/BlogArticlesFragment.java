package com.example.apothecary.views.view.articles;


import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Toast;

import com.example.apothecary.R;
import com.example.apothecary.Utils;
import com.example.apothecary.adapter.PostAdapter;
import com.example.apothecary.models.articles.Articles;
import com.example.apothecary.models.articles.Item;
import com.example.apothecary.views.view.home.HomeFragment;
import com.github.ybq.android.spinkit.SpinKitView;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


public class BlogArticlesFragment extends Fragment {

    @BindView(R.id.postRecyclerListFrag)
    RecyclerView recyclerView;
    @BindView(R.id.articleProgressBarFrag)
    SpinKitView progressBar;

    @BindView(R.id.pharmacy_Toolbar)
    Toolbar toolbar;
    @BindView(R.id.pharmacy_CardView)
    CardView logoCardView;

    LinearLayoutManager manager;
    PostAdapter adapter;
    List<Item> items ;

    boolean isScrolling=false;

    int currentItems , totalItems, scrolledOutItems;

    String token="";
    View view;
    Context _context;

    HomeFragment homeFragment;

    public BlogArticlesFragment() {

    }

    public static BlogArticlesFragment newInstance() {
        BlogArticlesFragment fragment = new BlogArticlesFragment();
        Bundle args = new Bundle();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_blog_articles,container,false);

        ButterKnife.bind(this,view);
        homeFragment = new HomeFragment();
        items = new ArrayList<>();
        manager = new LinearLayoutManager(getActivity());
        adapter = new PostAdapter(getActivity(),items);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        view.findViewById(R.id.shimmerArticles).setVisibility(view.VISIBLE);
        getBlogsData();
        onScrollRecyclerView();
        setToolbar();

        return view;
    }



    private void getBlogsData(){
        String url = com.example.apothecary.api.BloggerClient.BASE_URL+"?key="+ com.example.apothecary.api.BloggerClient.KEY;
        if (token!=""){
            url = url+ "&pageToken="+token;
        }
        if(token==null){
            return;
        }
        progressBar.setVisibility(view.VISIBLE);
        Call<com.example.apothecary.models.articles.Articles> articlesCall= Utils.getBloggerApi().getAllArticles(url);
        articlesCall.enqueue(new Callback<Articles>() {
            @Override
            public void onResponse(Call<Articles> call, Response<Articles> response) {
                Articles articles = response.body();
                token = articles.getNextPageToken();
                items.addAll((Collection<? extends Item>) articles.getItems());
                adapter.notifyDataSetChanged();

                progressBar.setVisibility(view.GONE);
                view.findViewById(R.id.shimmerArticles).setVisibility(view.GONE);
            }

            @Override
            public void onFailure(Call<Articles> call, Throwable t) {
                Toast.makeText(getActivity(),"Error Occured",Toast.LENGTH_SHORT);
            }
        });
    }

    private void onScrollRecyclerView(){
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if(newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL){
                    isScrolling=true;
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                currentItems = manager.getChildCount();
                totalItems = manager.getItemCount();
                scrolledOutItems=manager.findFirstVisibleItemPosition();

                if(isScrolling && (currentItems+scrolledOutItems==totalItems)){
                    isScrolling=false;

                    getBlogsData();
                }

            }
        });
    }



    private void setToolbar(){
        toolbar.setNavigationIcon(null);

        logoCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragmentContainer,homeFragment,"Home_Fragment")
                        .commit();
            }
        });

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });


    }

}
