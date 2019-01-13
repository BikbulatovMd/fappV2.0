package test.fragment.me.fragmenttest.fragments;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import test.fragment.me.fragmenttest.R;
import test.fragment.me.fragmenttest.utils.Instruments;

public class PhotosFrag extends Fragment implements SensorEventListener {

    private ImageView imageView;
    private Button btnTakeScreenshot;
    private Button btnSaveScreenshot;
    private Bitmap b;
    private SensorManager sensorManager;
    private Sensor sensor;
    private TextView textView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_photo_sensor_layout, container, false);

        imageView = v.findViewById(R.id.imageView_screenshot);
        btnTakeScreenshot = v.findViewById(R.id.btn_take_screenshot);
        btnSaveScreenshot = v.findViewById(R.id.btn_save_screenshot);
        textView = v.findViewById(R.id.tvSensor);

        sensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);

        btnTakeScreenshot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                b = Instruments.takeScreenShotOfRootView(imageView);
                imageView.setImageBitmap(b);
                Toast.makeText(getActivity(), "Screenshot is taken", Toast.LENGTH_SHORT).show();
            }
        });

        return v;
    }

    @Override
    public void onStart() {
        super.onStart();

        permissionsRequest();

        btnSaveScreenshot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Instruments.saveScreenshot(b, String.valueOf(System.currentTimeMillis()));

                MediaScannerConnection.scanFile(getActivity(), new String[]{b.toString()}, null, new
                        MediaScannerConnection.OnScanCompletedListener() {
                            @Override
                            public void onScanCompleted(String path, Uri uri) {
                                //Закончилось сканирование
                            }
                        });
                Toast.makeText(getActivity(), "Screenshot is saved", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();

        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);

    }

    @Override
    public void onPause() {
        super.onPause();

        sensorManager.unregisterListener(this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 2) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission is granted
                Toast.makeText(getActivity(), "Permission granted", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getActivity(), "Until you grant the permission, we cannot display the " +
                        "names", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void permissionsRequest() {
        // Check the SDK version and whether the permission is already granted or not.
        if ( ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ) {
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, 2);
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_LIGHT) {
            textView.setText("" + event.values[0]);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
