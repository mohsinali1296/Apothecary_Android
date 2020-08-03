package com.example.apothecary.views.view.Category;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;
import butterknife.ButterKnife;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.apothecary.R;
import com.example.apothecary.adapter.RecyclerViewProductsByCategoryAdapter;
import com.example.apothecary.adapter.RecyclerViewProductsByCategoryAdapter2;
import com.example.apothecary.adapter.ViewPagerCategoryAdapter;
import com.example.apothecary.models.listdata.ListData;
import com.example.apothecary.models.user.User;
import com.example.apothecary.views.view.home.HomeFragment;
import com.google.android.material.tabs.TabLayout;

import java.util.List;

public class CategoryMainFragment extends Fragment  {


    @BindView(R.id.toolbar_CategoryMain)
    Toolbar toolbar;
    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.viewPager)
    ViewPager viewPager;

    static int TabPosition;

    CategoryMainFragmentLister listener;

    static View view;
    private static List<ListData> categories;
    private static int category_position;
    private static  int CategoryId;
    private static String name=null , desc=null;
    private static User user;

    static RecyclerViewProductsByCategoryAdapter recyclerViewProductsByCategoryAdapter;
    static RecyclerViewProductsByCategoryAdapter2 recyclerViewProductsByCategoryAdapter2;
    //private List<Products> productsList;
    FragmentManager manager;
    Menu menu;
    SearchView searchView;
    HomeFragment homeFragment;

    public CategoryMainFragment() {
        // Required empty public constructor
    }

    public static CategoryMainFragment newInstance() {
        CategoryMainFragment fragment = new CategoryMainFragment();
        Bundle args = new Bundle();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_category_main, container, false);
        ButterKnife.bind(this,view);

        homeFragment =  new HomeFragment();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getArgumentsFromMainScreen();
        setToolbar();
        setViewPagerAdapter();
        TabEvent();
        ViewPager_Event();

    }

    private  void ViewPager_Event(){
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
               listener.setTabPosition(position);
            }

            @Override
            public void onPageSelected(int position) {
            //    listener.setTabPosition(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                //listener.setTabPosition(tabLayout.getSelectedTabPosition());
            }
        });
    }

    private void TabEvent(){
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                listener.setTabPosition(tab.getPosition());
                //TabPosition = tab.getPosition();
                //Toast.makeText(getActivity(),"Tab Position "+String.valueOf(TabPosition),Toast.LENGTH_LONG).show();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
//                listener.setTabPosition(tab.getPosition());
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                listener.setTabPosition(tab.getPosition());
                //TabPosition = tab.getPosition();
                //Toast.makeText(getActivity(),"Tab Position "+String.valueOf(TabPosition),Toast.LENGTH_LONG).show();
            }
        });
    }

    public static int getCurrentTabPosition(){
        //TabPosition  = tabLayout.getSelectedTabPosition();
        //TabLayout tb = view.findViewById(R.id.tabLayout);
        //return TabPosition;
        //return tb.getSelectedTabPosition();
        return 0;
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof CategoryMainFragmentLister){
            listener = (CategoryMainFragmentLister) context;
        }
        else{
            throw new RuntimeException(context.toString()+" must implement CategoryMain_ClickListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener=null;
    }

    public void setCategoryData(List<ListData> categories , int id, int position){

        this.categories=categories;
        this.category_position=position;
        this.CategoryId=id;
    }

    private void getArgumentsFromMainScreen(){
        if(getArguments()!=null){
        user = getArguments().getParcelable("LOGINUSER_DATA_EXTRA");

        }
    }


    public void setRecyclerViewProductByCategory(RecyclerViewProductsByCategoryAdapter recyclerViewProductByCategory){
       // recyclerViewProductsByCategoryAdapter = recyclerViewProductByCategory;
    }

    public void setRecyclerViewProductByCategory2(RecyclerViewProductsByCategoryAdapter2 recyclerViewProductByCategory){
        //recyclerViewProductsByCategoryAdapter2 = recyclerViewProductByCategory;
    }

    private void setViewPagerAdapter(){
        ViewPagerCategoryAdapter adapter =
                new ViewPagerCategoryAdapter(getActivity().getSupportFragmentManager(), this.categories,this.category_position);

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        listener.setTabPosition(category_position);
        viewPager.setCurrentItem(category_position,true);
        adapter.notifyDataSetChanged();
    }

    private void setToolbar(){
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragmentContainer,homeFragment,"Home_Fragment")
                        .commit();
            }
        });

        toolbar.inflateMenu(R.menu.category_main_menu);
        menu = toolbar.getMenu();

/*
       toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

            }
        });
*/
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {

        // toolbar.inflateMenu(R.menu.category_mian_menu);
        inflater.inflate(R.menu.category_main_menu,this.menu);
        MenuItem item = menu.findItem(R.id.action_Searchbar);
        searchView = (SearchView) item.getActionView();

        searchView.setQueryHint("Search");

        super.onCreateOptionsMenu(menu, inflater);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                if (newText == null || newText.trim().isEmpty()) {
                    //resetSearch();
                    return false;
                }

                // CategoriesFragment fragment = new CategoriesFragment();
               /// recyclerViewMealByCategory.getFilter().filter(newText);
                return false;
            }
        });

        super.onCreateOptionsMenu(menu, inflater);

    }


    public interface CategoryMainFragmentLister{
        void setTabPosition(int position);
    }

}
