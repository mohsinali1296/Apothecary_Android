package com.example.apothecary.views.view.Category;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;
import butterknife.ButterKnife;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

import com.example.apothecary.R;
import com.example.apothecary.adapter.RecyclerViewProductsByCategoryAdapter;
import com.example.apothecary.adapter.RecyclerViewProductsByCategoryAdapter2;
import com.example.apothecary.adapter.ViewPagerCategoryAdapter;
import com.example.apothecary.models.listdata.ListData;
import com.google.android.material.tabs.TabLayout;

import java.util.List;

public class CategroryActivity extends AppCompatActivity implements
        CategoriesFragment.CategoriesFragmentListener
        //, AddtoCart_BottomSheet.BottomSheetListener
{

    @BindView(R.id.toolbar_CategoryMain)
    Toolbar toolbar;
    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.imageCardView)
    CardView logoCardView;

    Menu menu;


   // AddtoCart_BottomSheet addtoCart_bottomSheet;

    private static List<ListData> categories;
    private static int category_position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categrory);
        ButterKnife.bind(this);
      //  addtoCart_bottomSheet = new AddtoCart_BottomSheet();

        setViewPagerAdapter();
        setToolbar();
       // initActionBar();
        //initIntent();
    }

    private void setViewPagerAdapter(){

        Intent intent = getIntent();
        categories  =
                ( List<ListData>) intent.getSerializableExtra("EXTRA_CATEGORY");
        category_position = intent.getIntExtra("EXTRA_POSITION", 0);

        ViewPagerCategoryAdapter adapter =
                new ViewPagerCategoryAdapter(getSupportFragmentManager(), this.categories,this.category_position);

        

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
  //      listener.setTabPosition(category_position);
        viewPager.setCurrentItem(category_position,true);
        adapter.notifyDataSetChanged();
    }

    private void setToolbar(){
        //toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        //toolbar.setTitle("Product Categories");
        toolbar.setNavigationIcon(null);
        logoCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
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
    public void sendProductsAdapter(RecyclerViewProductsByCategoryAdapter adapter) {

    }

    @Override
    public void sendStocksAdapter(RecyclerViewProductsByCategoryAdapter2 adapter) {

    }

    @Override
    public void sendStockDetailsToDetailedActivity(int stockId) {

    }

    @Override
    public void sendProductDetailsToDetailedActivity(int productId) {

    }

}
