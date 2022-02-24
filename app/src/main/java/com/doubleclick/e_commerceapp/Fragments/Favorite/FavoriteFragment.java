package com.doubleclick.e_commerceapp.Fragments.Favorite;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.doubleclick.e_commerceapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

import static com.doubleclick.e_commerceapp.Fragments.Favorite.DBQurieFavorite.CheckFavorite;
import static com.doubleclick.e_commerceapp.Fragments.Favorite.DBQurieFavorite.favoriteList;
import static com.doubleclick.e_commerceapp.Fragments.Favorite.DBQurieFavorite.favorites;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FavoriteFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FavoriteFragment extends Fragment {

    private FirebaseFirestore firestore;
    private FirebaseAuth mAuth;
    private String UserID;
    private Favorite favorite;
    private RecyclerView favoriteRecycler;
//    private List<Products> favoriteList = new ArrayList<>();
    public static FavoriteAdapter FragmentfavoriteAdapter;
    private List<Favorite> favoritesList = favorites;



    public FavoriteFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static FavoriteFragment newInstance(String param1, String param2) {
        FavoriteFragment fragment = new FavoriteFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_favorite, container, false);
        firestore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        UserID = mAuth.getCurrentUser().getUid();
        favoriteRecycler = view.findViewById(R.id.favoriteRecycler);
        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(getContext());
        linearLayoutManager1.setOrientation(LinearLayoutManager.VERTICAL);
        favoriteRecycler.setLayoutManager(linearLayoutManager1);
        favoriteRecycler.setHasFixedSize(true);
        FragmentfavoriteAdapter = new FavoriteAdapter(favoriteList,UserID);
        favoriteRecycler.setAdapter(FragmentfavoriteAdapter);
        favoriteList.clear();
        if (favoriteList.size()==0){
            CheckFavorite(FragmentfavoriteAdapter,getContext(), UserID);
        }else {
            FragmentfavoriteAdapter.notifyDataSetChanged();
        }


        /*new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull @NotNull RecyclerView recyclerView, @NonNull @NotNull RecyclerView.ViewHolder viewHolder, @NonNull @NotNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull @NotNull RecyclerView.ViewHolder viewHolder, int direction) {
                int pos = viewHolder.getAdapterPosition();
                firestore.collection("Users").document(UserID)
                        .collection("Favorite").document(favoritesList.get(pos).getFid())
                        .delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        if (favoritesList.size()==0){
                            FragmentfavoriteAdapter = new FavoriteAdapter(favoriteList,UserID);
                            favoriteRecycler.setAdapter(FragmentfavoriteAdapter);
                        }else {
                            FragmentfavoriteAdapter.notifyDataSetChanged();
                        }
                        FragmentfavoriteAdapter.notifyDataSetChanged();
                        Toast.makeText(getContext(), "Deleted from favorite", Toast.LENGTH_LONG).show();
                    }
                });

            }
        }).attachToRecyclerView(favoriteRecycler);*/


        return view;
    }

}