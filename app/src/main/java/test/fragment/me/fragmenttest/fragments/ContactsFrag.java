package test.fragment.me.fragmenttest.fragments;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.tomash.androidcontacts.contactgetter.entity.ContactData;
import com.tomash.androidcontacts.contactgetter.main.FieldType;
import com.tomash.androidcontacts.contactgetter.main.contactsGetter.ContactsGetterBuilder;

import java.util.ArrayList;

import test.fragment.me.fragmenttest.R;
import test.fragment.me.fragmenttest.adapter.ContactListAdapter;

public class ContactsFrag extends Fragment {

    RecyclerView recyclerView;
    ContactListAdapter recyclerViewAdapter;
    private ArrayList<ContactData> mTexts = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contacts_layout, container, false);
        recyclerView = view.findViewById(R.id.contact_container);

        initRecyclerView();
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        permissionsRequest();
    }

    private void initRecyclerView(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerViewAdapter = new ContactListAdapter(mTexts);
        recyclerView.setAdapter(recyclerViewAdapter);
    }

    private void addContacts() {
        mTexts.clear();
        mTexts.addAll(new ContactsGetterBuilder(getContext()).addField(FieldType.NAME_DATA).buildList());
        recyclerViewAdapter.notifyDataSetChanged();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission is granted
                addContacts();
            } else {
                Toast.makeText(getActivity(), "Until you grant the permission, we cannot display the " +
                        "names", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void permissionsRequest() {
        // Check the SDK version and whether the permission is already granted or not.
        if ( ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_CONTACTS) != PackageManager.PERMISSION_GRANTED ) {
            requestPermissions(new String[]{Manifest.permission.READ_CONTACTS, Manifest.permission.WRITE_CONTACTS}, 1);
        } else {
            addContacts();
        }
    }

    

//    private void addContacts(){
//        mTexts.clear();
//        mTexts.addAll(getContacts());
//        recyclerViewAdapter.notifyDataSetChanged();
//    }

//    private List<ContactModel> getContacts(){
//        List<ContactModel> tempList = new ArrayList<>();
//        //ContentResolver
//        Cursor phones = getContext().getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,null,null, null);
//        while (phones.moveToNext())
//        {
//            String name=phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
//            while (phones.moveToNext()){
//                ContactModel info = new ContactModel();
//                info.name = name;
//                tempList.add(info);
//            }
//        }
//        phones.close();
//        return tempList;
//    }
}