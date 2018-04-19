package com.nikola.jakshic.dagger.view.fragment;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nikola.jakshic.dagger.R;
import com.nikola.jakshic.dagger.DaggerApp;
import com.nikola.jakshic.dagger.view.adapter.PlayerAdapter;
import com.nikola.jakshic.dagger.viewModel.BookmarkViewModel;

import javax.inject.Inject;

public class BookmarkFragment extends Fragment {

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    public BookmarkFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        ((DaggerApp) getActivity().getApplication()).getAppComponent().inject(this);
        super.onAttach(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_bookmark, container, false);

        BookmarkViewModel viewModel = ViewModelProviders.of(this, viewModelFactory).get(BookmarkViewModel.class);
        PlayerAdapter mAdapter = new PlayerAdapter(getContext());
        RecyclerView recyclerView = root.findViewById(R.id.recview_bookmark);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(mAdapter);
        recyclerView.setHasFixedSize(true);
        viewModel.getPlayers().observe(this, mAdapter::addData);

        return root;
    }
    @Override
    public void onResume() {
        super.onResume();
        // This fragment is an item from BottomNavigationView
        // Set the proper title when this fragment is not hidden
        if (!isHidden()) getActivity().setTitle("Bookmark");
    }
}