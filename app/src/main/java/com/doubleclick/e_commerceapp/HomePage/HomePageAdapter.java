package com.doubleclick.e_commerceapp.HomePage;


import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Build;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;


import com.doubleclick.e_commerceapp.Activites.User.HomeActivity;
import com.doubleclick.e_commerceapp.FirebaseAdapter.CategoryAdapter;
import com.doubleclick.e_commerceapp.FirebaseAdapter.ProductAdapter;
import com.doubleclick.e_commerceapp.Fragments.GridProduct.GridProductAdaper;
import com.doubleclick.e_commerceapp.Fragments.SeeAllCatergoryFragment;
import com.doubleclick.e_commerceapp.Model.Advertisement;
import com.doubleclick.e_commerceapp.Model.HomePage;
import com.doubleclick.e_commerceapp.Model.Products;
import com.doubleclick.e_commerceapp.R;
import com.doubleclick.e_commerceapp.Slider_Banner.SliderAdapter;
import com.google.firebase.database.DatabaseReference;
import com.squareup.picasso.Picasso;


import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import static com.doubleclick.e_commerceapp.Fragments.SeeAllCatergoryFragment.GridViewShowAll;
import static com.doubleclick.e_commerceapp.Fragments.SeeAllCatergoryFragment.appBarCategory;

public class HomePageAdapter extends RecyclerView.Adapter {

    public HomePageAdapter(List<HomePage> homePageList) {
        this.homePageList = homePageList;
    }

    public HomePageAdapter() {
    }

    public List<HomePage> getHomePageList() {
        return homePageList;
    }

    public void setHomePageList(List<HomePage> homePageList) {
        this.homePageList = homePageList;
    }

    private List<HomePage> homePageList;
    DatabaseReference ProductsRef;
    List<Products> productsList = new ArrayList<>();
    public static CategoryAdapter categoryAdapter;
    Class fragmentClass;
    public static Fragment fragment;


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        switch (viewType) {
            case HomePage.BANNER_SLIDER: // 0
                View bannerSliderView = LayoutInflater.from(parent.getContext()).inflate(R.layout.slids_ad_banner, parent, false);
                return new BannerSliderViewholder(bannerSliderView);
            case HomePage.STRIB_AD_BANNER:// for Image Ad => 1
                View StripAdView = LayoutInflater.from(parent.getContext()).inflate(R.layout.strip_ad_layout, parent, false);
                return new StripAdBannerViewHolder(StripAdView);
            case HomePage.HORIZONTAL_PRODUCT_VIEW: //2
                View HorizontalProductView = LayoutInflater.from(parent.getContext()).inflate(R.layout.horizonal_scroll_layout, parent, false);
                return new HotizonalProductViewHolder(HorizontalProductView);
            default:
                return null;
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (homePageList.get(position).getType()) {
            case HomePage.BANNER_SLIDER: //0
                List<Advertisement> advertisementList = homePageList.get(position).getAdvertisementList();
                ((BannerSliderViewholder) holder).setBannerSliderViewPager(advertisementList);
                break;
            case HomePage.STRIB_AD_BANNER: //1
                String resource = homePageList.get(position).getImageResource();
                String color = homePageList.get(position).getBackgrondColor();
                ((StripAdBannerViewHolder) holder).setStripAd(resource, color);
                break;
            case HomePage.HORIZONTAL_PRODUCT_VIEW: //2
                String titel = homePageList.get(position).getCategoryName();
                String Color = homePageList.get(position).getBackgrondColor();
                List<Products> productsList = homePageList.get(position).getProductsList();
                ((HotizonalProductViewHolder) holder).setHorizontalProductLayout(productsList,titel, position);
                break;
            default:
                return;
        }

    }


    @Override
    public int getItemCount() {
        return homePageList.size();
    }

    @Override
    public int getItemViewType(int position) {
        switch (homePageList.get(position).getType()) {
            case 0:
                return HomePage.BANNER_SLIDER;
            case 1:
                return HomePage.STRIB_AD_BANNER;
            case 2:
                return HomePage.HORIZONTAL_PRODUCT_VIEW;
            default:
                return -1;
        }
    }

    public class HotizonalProductViewHolder extends RecyclerView.ViewHolder {

        private TextView horizontallayouttitel;
        private Button viewAllBtn;
        private RecyclerView horizontalRecyelerView;
        private ConstraintLayout continer;
        private FragmentManager mFragmentManager;

        public HotizonalProductViewHolder(@NonNull View itemView) {
            super(itemView);

            horizontallayouttitel = itemView.findViewById(R.id.h_s_titelScroll);
            viewAllBtn = itemView.findViewById(R.id.h_s_btnScroll);
            horizontalRecyelerView = itemView.findViewById(R.id.h_s_Recycler_Scroll);
            continer = itemView.findViewById(R.id.continer);
            HomeActivity activity = (HomeActivity) itemView.getContext();
            mFragmentManager = activity.getSupportFragmentManager();
        }

        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        private void setHorizontalProductLayout(List<Products> productsList,String titel, int position) {
            horizontallayouttitel.setText(titel);
            viewAllBtn.setVisibility(View.VISIBLE);

            // action on Btn
            viewAllBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showFragment(new SeeAllCatergoryFragment(),false);
                    GridProductAdaper gridProductAdaper = new GridProductAdaper(productsList);
                    GridViewShowAll.setVisibility(View.VISIBLE);
                    appBarCategory.setVisibility(View.GONE);
                    GridViewShowAll.setAdapter(gridProductAdaper);
                    gridProductAdaper.notifyDataSetChanged();
                }
            });
            ProductAdapter productAdapter = new ProductAdapter(productsList,itemView.getContext());
            /*productAdapter.loadData(titel,itemView.getContext());
            ///////
            categoryAdapter = new CategoryAdapter();
            categoryAdapter.loadData(titel, itemView.getContext());*/
            LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(itemView.getContext());
            linearLayoutManager1.setOrientation(LinearLayoutManager.HORIZONTAL);
            horizontalRecyelerView.setLayoutManager(linearLayoutManager1);
            horizontalRecyelerView.setHasFixedSize(true);
            /*horizontalRecyelerView.setAdapter(categoryAdapter.getAdapter());*/

            horizontalRecyelerView.setAdapter(productAdapter);
            productAdapter.notifyDataSetChanged();
            /*////////
            categoryAdapter.getAdapter().startListening();*/

        }
        private void showFragment(Fragment fragment, boolean allowStateLoss) {
            try {
                FragmentManager fm = mFragmentManager;
                if (allowStateLoss) {

                } else {
                    fm.beginTransaction().setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out).replace(R.id.frameLayout, fragment).commit();
                }
                fm.executePendingTransactions();
            }catch (IllegalArgumentException e){
                Log.e("HomePageAdapter At 219 "," == "+e.getMessage());
                Toast.makeText(itemView.getContext(),""+e.getMessage(),Toast.LENGTH_LONG).show();
            }
        }

    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public class BannerSliderViewholder extends RecyclerView.ViewHolder { //0

        private ViewPager bannerSliderViewPager;
        private int currentPage;
        private Timer timer;
        private final long DELAY_TIME = 2000;
        private final long PERIOD_TIME = 2000;

        public BannerSliderViewholder(@NonNull View itemView) {
            super(itemView);
            bannerSliderViewPager = itemView.findViewById(R.id.banner_slier_view_pager);
        }

        private void setBannerSliderViewPager(final List<Advertisement> advertisements) {
            currentPage = 2;
//////////////////////////////////////////////////////////////////
            SliderAdapter sliderAdapter = new SliderAdapter(advertisements);
            bannerSliderViewPager.setAdapter(sliderAdapter);
            bannerSliderViewPager.setClipToPadding(false);
            bannerSliderViewPager.setPageMargin(20);
            bannerSliderViewPager.setCurrentItem(currentPage);

            ///////////////////////////////////////////////////////////////////////////////////////////////////////
            //     onPageChangeListener
            bannerSliderViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() { //is not important
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                }

                @Override
                public void onPageSelected(int position) {
                    currentPage = position;
                }

                @Override
                public void onPageScrollStateChanged(int state) {
                    if (state == ViewPager.SCROLL_STATE_IDLE) {
//                        PageLooper(sliderModelList);  // is not important
                    }
                }
            });
            ///////////////////////////////////////////////////////////////////////////////////////////////////////////
            StartbannerSlideShow(advertisements);
            //if banner Touch this mathod is excut
            bannerSliderViewPager.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    StopBannerSlideShow();
                    //
                    if (event.getAction() == MotionEvent.ACTION_UP) {
                        StartbannerSlideShow(advertisements);
                    }
                    return false;
                }
            });
        }


        // this resbonsable to loop slider
        private void StartbannerSlideShow(final List<Advertisement> advertisementList) {
            final Handler handler = new Handler();
            final Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    if (currentPage >= advertisementList.size()) {
                        currentPage = 0;
                    }

                    bannerSliderViewPager.setCurrentItem(currentPage++);
                }
            };
            timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    handler.post(runnable);
                }
            }, DELAY_TIME, PERIOD_TIME);
        }

        private void StopBannerSlideShow() {
            timer.cancel();
        }

    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public class StripAdBannerViewHolder extends RecyclerView.ViewHolder { //1
        private ConstraintLayout constraintLayoutStrip;
        private ImageView imageViewStrip;
        private StripAdBannerViewHolder(@NonNull View itemView) {
            super(itemView);
            constraintLayoutStrip = itemView.findViewById(R.id.Strip_ad_Constraint);
            imageViewStrip = itemView.findViewById(R.id.StripImageView);
        }
        private void setStripAd(String resource, String color) {
            Picasso.get().load(resource).into(imageViewStrip);
        }
    }

}
