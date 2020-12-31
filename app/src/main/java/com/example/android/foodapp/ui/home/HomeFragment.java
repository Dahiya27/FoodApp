package com.example.android.foodapp.ui.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.foodapp.MainActivity;
import com.example.android.foodapp.Model;
import com.example.android.foodapp.R;
import com.example.android.foodapp.StartActivity;
import com.example.android.foodapp.myadapter;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    myadapter adapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        setHasOptionsMenu(true);


        RecyclerView rView =(RecyclerView)root.findViewById(R.id.recView);
        rView.setLayoutManager(new LinearLayoutManager(getActivity()));

        adapter = new myadapter(inf(),getContext().getApplicationContext());
        rView.setAdapter(adapter);
        return root;
    }


    public ArrayList<Model> inf(){
        ArrayList<Model> holder = new ArrayList<>();

        Model ob1 = new Model();
        ob1.setHeader("Cafe");
        ob1.setDesc("Refresh yourSelf!");
        ob1.setImgName(R.drawable.caf);
        holder.add(ob1);

        Model ob2 = new Model();
        ob2.setHeader("Cake");
        ob2.setDesc("Necessary for facials");
        ob2.setImgName(R.drawable.cakee);
        holder.add(ob2);

        Model ob3 = new Model();
        ob3.setHeader("Family Restaurant");
        ob3.setDesc("Best way to get together!");
        ob3.setImgName(R.drawable.__knife_fork_512);
        holder.add(ob3);

        Model ob4 = new Model();
        ob4.setHeader("FastFood");
        ob4.setDesc("Have crispy, have Fun!");
        ob4.setImgName(R.drawable.burger__fastfood__food__hamburger__junkfood__beef__drink_512);
        holder.add(ob4);

        Model ob5 = new Model();
        ob5.setHeader("IceCream");
        ob5.setDesc("let your soul feel cool!");
        ob5.setImgName(R.drawable.ice_cream_1_512);
        holder.add(ob5);

        Model ob6 = new Model();
        ob6.setHeader("Juice");
        ob6.setDesc("Necessary for Health");
        ob6.setImgName(R.drawable.juices);
        holder.add(ob6);

        Model ob7 = new Model();
        ob7.setHeader("Pizza");
        ob7.setDesc("Love at first Bite!");
        ob7.setImgName(R.drawable.pizza);
        holder.add(ob7);

        return holder;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.searching,menu);
        final MenuItem item = menu.findItem(R.id.search_menu);

        SearchView searchView = (SearchView)item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });
        super.onCreateOptionsMenu(menu, inflater);
    }
}