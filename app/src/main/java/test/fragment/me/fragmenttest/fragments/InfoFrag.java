package test.fragment.me.fragmenttest.fragments;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import test.fragment.me.fragmenttest.R;
import test.fragment.me.fragmenttest.utils.Instruments;

public class InfoFrag extends Fragment {

    private TextView wifiIpTV;
    private TextView mobileIpTV;
    private TextView tvModel;
    private TextView tvVersion;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_info_layout, container, false);
        wifiIpTV = view.findViewById(R.id.wifiIP);
        mobileIpTV = view.findViewById(R.id.mobileIP);
        tvModel = view.findViewById(R.id.tvModel);
        tvVersion = view.findViewById(R.id.tvVersion);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        String mobileIP = Instruments.getMobileIPAddress();
        String wifiIP = Instruments.getWifiIPAddress(getContext());

        wifiIpTV.setText("IPv4: " + wifiIP);
        mobileIpTV.setText("IPv6: " + mobileIP);

        String model = Build.MODEL;
        tvModel.setText("Модель: " + model);

        String ver = Build.VERSION.RELEASE;
        tvVersion.setText("Версия: " + ver);
    }
}
