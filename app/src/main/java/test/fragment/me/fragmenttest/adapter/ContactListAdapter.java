package test.fragment.me.fragmenttest.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tomash.androidcontacts.contactgetter.entity.ContactData;

import java.util.ArrayList;

import test.fragment.me.fragmenttest.R;
import test.fragment.me.fragmenttest.model.ContactModel;

public class ContactListAdapter extends RecyclerView.Adapter<ContactListAdapter.ViewHolder>{

    private ArrayList<ContactData> mTexts;
   // private ArrayList<ContactModel> mTexts;


    public ContactListAdapter(ArrayList<ContactData> mTexts){
        this.mTexts = mTexts;
    }
    //public ContactListAdapter(ArrayList<ContactModel> mTexts){
    //    this.mTexts = mTexts;
    //}

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.contact_item_layout, viewGroup, false);
        ViewHolder holder = new ViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.textView.setText(mTexts.get(i).getNameData().getFullName());
    }

    @Override
    public int getItemCount() {
         if (mTexts != null){
            return mTexts.size();
         } else return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView textView;
        RelativeLayout rLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.textContact);
            rLayout = itemView.findViewById(R.id.contact_layout);
        }
    }
}
