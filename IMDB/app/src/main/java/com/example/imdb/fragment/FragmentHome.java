package com.example.imdb.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import com.example.imdb.MainActivity;
import com.example.imdb.R;
import com.example.imdb.adapter.AdapterSliderViewPager2;
import com.example.imdb.adapter.CategoryAdapter;
import com.example.imdb.model.Category;
import com.example.imdb.presenter.PresenterHomeFragment;

import java.util.List;
import java.util.Timer;

import me.relex.circleindicator.CircleIndicator3;

public class FragmentHome extends Fragment implements PresenterHomeFragment.IOnHomeFragment {
    private RecyclerView rvCategory;
    private PresenterHomeFragment presenterHomeFragment;
    private CategoryAdapter categoryAdapter;
    private ProgressBar progressBar;
    private ViewPager2 viewPager2;
    private CircleIndicator3 circleIndicator3;
    private AdapterSliderViewPager2 adapterSliderViewPager2;
    private Handler handler= new Handler(Looper.getMainLooper());
    private Runnable runnable= new Runnable() {
        @Override
        public void run() {
            int currentItem=viewPager2.getCurrentItem();
            if(adapterSliderViewPager2.getData()==null) return;
            if(currentItem==adapterSliderViewPager2.getData().size()-1){
                viewPager2.setCurrentItem(0,true);
            }else viewPager2.setCurrentItem(currentItem+1,true);
        }
    };


    public FragmentHome() {
    }
    public static FragmentHome newInstance(){
        FragmentHome f= new FragmentHome();
        Bundle args= new Bundle();
        f.setArguments(args);
        return f;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragmenthome,container,false);

        rvCategory= view.findViewById(R.id.rvCategory);
        progressBar=view.findViewById(R.id.progressbar);
        circleIndicator3=view.findViewById(R.id.circleindicator);
        viewPager2= view.findViewById(R.id.viewpagerSlider);

        adapterSliderViewPager2= new AdapterSliderViewPager2(getContext());

        presenterHomeFragment= new PresenterHomeFragment(this);
        progressBar.setVisibility(View.VISIBLE);
        presenterHomeFragment.onGetAllCategory();

        categoryAdapter= new CategoryAdapter(getContext(),(MainActivity) getActivity());

        return view;
    }

    @Override
    public void onGetData(List<Category> list) {
        progressBar.setVisibility(View.GONE);
//        LinearLayoutManager linearLayoutManager1= new LinearLayoutManager(getContext(),RecyclerView.HORIZONTAL,false);
//        rvSlider.setLayoutManager(linearLayoutManager1);
//        rvSlider.setFocusable(false);
//        rvSlider.setNestedScrollingEnabled(false);
//
//        sliderAdapter.setData(list.get(0).getMovies());
//        rvSlider.setAdapter(sliderAdapter);
//        LinearSnapHelper linearSnapHelper= new LinearSnapHelper();
//        linearSnapHelper.attachToRecyclerView(rvSlider);
//        autoSlideAvatar(linearLayoutManager1);

        viewPager2.setOffscreenPageLimit(3);
        viewPager2.setClipToPadding(false);
        viewPager2.setClipChildren(false);

        CompositePageTransformer composite= new CompositePageTransformer();
        composite.addTransformer(new MarginPageTransformer(40));

        composite.addTransformer (new ViewPager2.PageTransformer() {
            @Override
            public void transformPage(@NonNull View page, float position) {
                float r = 1 - Math.abs(position);
                page.setScaleY(0.85f + r * 0.15f);
            }
        });


        viewPager2.setPageTransformer(composite);


        adapterSliderViewPager2.setData(list.get(0).getMovies());
        viewPager2.setAdapter(adapterSliderViewPager2);
        circleIndicator3.setViewPager(viewPager2);

        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                handler.removeCallbacks(runnable);
                handler.postDelayed(runnable,3000);
            }
        });

        LinearLayoutManager linearLayoutManager= new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false);

        categoryAdapter.setData(list);
        rvCategory.setLayoutManager(linearLayoutManager);
        rvCategory.setAdapter(categoryAdapter);


    }
//    private  void autoSlideAvatar(LinearLayoutManager linearLayoutManager1){
//
//        timer= new Timer();
//        timer.schedule(new TimerTask() {
//            @Override
//            public void run() {
//                if(linearLayoutManager1.findLastCompletelyVisibleItemPosition()<sliderAdapter.getItemCount()-1){
//                    linearLayoutManager1.smoothScrollToPosition(rvSlider,new RecyclerView.State(),
//                            linearLayoutManager1.findLastCompletelyVisibleItemPosition()+1);
//                }
//                else {
//                    linearLayoutManager1.smoothScrollToPosition(rvSlider,new RecyclerView.State(),
//                            0);
//                }
//                if(linearLayoutManager1.findLastCompletelyVisibleItemPosition()<sliderAdapter.getItemCount()-1);
//            }
//        },2000,4000);
//    }


    @Override
    public void onPause() {
        super.onPause();
        handler.removeCallbacks(runnable);
    }

    @Override
    public void onResume() {
        super.onResume();
        handler.postDelayed(runnable,3000);
    }
}
