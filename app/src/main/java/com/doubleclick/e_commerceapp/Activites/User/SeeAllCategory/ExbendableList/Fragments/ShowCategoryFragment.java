package com.doubleclick.e_commerceapp.Activites.User.SeeAllCategory.ExbendableList.Fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.doubleclick.e_commerceapp.FirebaseAdapter.ProductAdapter;
import com.doubleclick.e_commerceapp.R;
import com.google.firebase.firestore.FirebaseFirestore;

import org.jetbrains.annotations.NotNull;

import static com.doubleclick.e_commerceapp.Activites.User.SeeAllCategory.ExbendableList.Fragments.QurieChildCategory.loadChildren;
import static com.doubleclick.e_commerceapp.Activites.User.SeeAllCategory.ExbendableList.Fragments.QurieChildCategory.productsListChildren;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ShowCategoryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ShowCategoryFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String KEY_CATEGORY_HEAD = "key_head";
    private static final String KEY_CATEGORY_Child = "key_child";
    private static String Head, Child;
    private FirebaseFirestore firestore;
    private static RecyclerView ChildCategoryRecyclerVew;
    private static ProductAdapter productAdapter;
    private static View view;

    public ShowCategoryFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment ShowCategoryFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ShowCategoryFragment newInstance(String categoryHead, String categoryChild) {
        ShowCategoryFragment fragment = new ShowCategoryFragment();
        Bundle args = new Bundle();
        args.putString(KEY_CATEGORY_HEAD, categoryHead);
        args.putString(KEY_CATEGORY_Child, categoryChild);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_show_category, container, false);
        firestore = FirebaseFirestore.getInstance();
        Head = getArguments().getString(KEY_CATEGORY_HEAD);
        Child = getArguments().getString(KEY_CATEGORY_Child);

        loadproductsListChildren(getContext());
        return view;
    }

    @Override
    public void onViewCreated(@NotNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }
    public static void loadproductsListChildren(Context context) {
        ChildCategoryRecyclerVew = view.findViewById(R.id.ChildCategoryRecyclerVew);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        ChildCategoryRecyclerVew.setLayoutManager(linearLayoutManager);
        productAdapter = new ProductAdapter(productsListChildren, context);
        ChildCategoryRecyclerVew.setAdapter(productAdapter);
        if (productsListChildren.size() == 0) {
            loadChildren(productAdapter, Head, Child, context);
        } else {
            productAdapter.notifyDataSetChanged();
        }
    }
}