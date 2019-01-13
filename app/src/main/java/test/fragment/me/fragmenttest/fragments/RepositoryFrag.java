package test.fragment.me.fragmenttest.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import test.fragment.me.fragmenttest.R;
import test.fragment.me.fragmenttest.adapter.GitHubAdapter;
import test.fragment.me.fragmenttest.model.GitHubList;
import test.fragment.me.fragmenttest.utils.App;

public class RepositoryFrag extends Fragment {

    private static final String TAG = "RepositoryFrag";

    private RecyclerView recyclerView;
    private GitHubAdapter adapter;
    private ArrayList<GitHubList> mTexts = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_repository_layout, container, false);

        recyclerView = view.findViewById(R.id.recycler_view);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initRecyclerView();
    }

    @Override
    public void onStart() {
        super.onStart();

        addViews();
    }

    private void initRecyclerView(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new GitHubAdapter(mTexts);
        recyclerView.setAdapter(adapter);
    }

    private void addViews(){

        App.getNetClient().getRepos(App.getUsername(), new Callback<List<GitHubList>>() {
            @Override
            public void onResponse(Call<List<GitHubList>> call, Response<List<GitHubList>> response) {
                if(response.isSuccessful()){
                    mTexts.clear();
                    mTexts.addAll(response.body());
                    adapter.notifyDataSetChanged();
                }
                else Log.d(TAG, "Код ошибки: " + response.code());
            }

            @Override
            public void onFailure(Call<List<GitHubList>> call, Throwable t) {
                t.printStackTrace();
            }
        });

//        mTexts.add("кек1");
//        mTexts.add("кек2");
//        mTexts.add("кек3");
//        mTexts.add("кек4");
//        mTexts.add("кек5");
//        mTexts.add("кек6");
//        mTexts.add("кек7");
//        mTexts.add("кек8");
//        mTexts.add("кек9");
//        mTexts.add("кек10");
//        mTexts.add("кек11");
//        mTexts.add("кек12");
//        mTexts.add("кек13");
//        adapter.notifyDataSetChanged();
    }

//    private List<String> createList(int a){
//        List<String> tempList = new ArrayList<>();
//
//        for (int i = 0; i < a; i++) {
//            tempList.add("kek" + i);
//        }
//        return tempList;
//    }
}