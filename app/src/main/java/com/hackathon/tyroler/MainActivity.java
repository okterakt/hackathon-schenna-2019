package com.hackathon.tyroler;

import android.os.Bundle;
import android.os.RemoteException;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.altbeacon.beacon.BeaconConsumer;
import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.BeaconParser;
import org.altbeacon.beacon.Identifier;
import org.altbeacon.beacon.MonitorNotifier;
import org.altbeacon.beacon.Region;

public class MainActivity extends AppCompatActivity implements BeaconConsumer, MonitorNotifier {

    private static final String TAG = "MainActivity";
    private BeaconManager beaconManager;
    SharedViewModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        BottomNavigationView bottomNavView = findViewById(R.id.bottom_navigation);
        NavigationUI.setupWithNavController(bottomNavView, navController);

        Navigation.findNavController(this, R.id.nav_host_fragment).navigate(R.id.challenges_dest);

        model = ViewModelProviders.of(this).get(SharedViewModel.class);
    }

    @Override
    protected void onResume() {
        super.onResume();
        beaconManager = BeaconManager.getInstanceForApplication(this.getApplicationContext());
        beaconManager.getBeaconParsers().add(new BeaconParser().
                setBeaconLayout(BeaconParser.EDDYSTONE_UID_LAYOUT));
        beaconManager.bind(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        beaconManager.unbind(this);
    }

    @Override
    public void onBeaconServiceConnect() {
        Identifier beaconNamespaceId = Identifier.parse("6a84c7166a63bd873dd9");
        Identifier beaconInstanceId = Identifier.parse("fa6ffd05739a");
        Region region = new Region("com.hackathon.tyroler", beaconNamespaceId, beaconInstanceId, null);
        beaconManager.addMonitorNotifier(this);
        try {
            beaconManager.startMonitoringBeaconsInRegion(region);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void didEnterRegion(Region region) {
        model.increaseCurr();
        AlertDialog.Builder builder = new AlertDialog
                .Builder(this)
                .setMessage("Congratulations! You have achieved a new milestone in the Fast Lodge Collector"
                        + " challenge. Complete the last step and you will receive an awesome gift!")
                .setTitle("New milestone!")
                .setPositiveButton("Great", (dialog, id) -> {
                    // Empty
                });
        builder.create().show();
        beaconManager.unbind(this);
    }

    @Override
    public void didExitRegion(Region region) {

    }

    @Override
    public void didDetermineStateForRegion(int i, Region region) {

    }
}
