package test.fragment.me.fragmenttest.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import test.fragment.me.fragmenttest.R;
import test.fragment.me.fragmenttest.model.GitHubList;


public class GitHubAdapter extends RecyclerView.Adapter<GitHubAdapter.ViewHolder> {

    private ArrayList<GitHubList> mTexts;

    public GitHubAdapter(ArrayList<GitHubList> mTexts){
        this.mTexts = mTexts;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView textView;
        RelativeLayout parentLay;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.txt_view_recycler);
            parentLay = itemView.findViewById(R.id.parent_layout);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_layout, viewGroup, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.textView.setText(mTexts.get(i).getName());
    }

    @Override
    public int getItemCount() {
        if(mTexts != null)
            return mTexts.size();
        else return 0;
    }

}
