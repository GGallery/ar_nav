package com.android.ggallery.beaconar;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.os.Handler;
import java.util.ArrayList;


/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions and extra parameters.
 */
public final class GgalleryBeaconListenerIntentService extends IntentService {

    private BluetoothManager btManager;
    private BluetoothAdapter btAdapter;
    private Handler scanHandler = new Handler();
    private int scan_interval_ms = 2000;
    private boolean isScanning = false;

    private String Uuid;
    private Integer Rssi;
    private Integer min;
    private Integer Max;
    private ArrayList<Beacon> beacons;
    private Beacon nearestbeacon;
    private String LocationUUid="C336AA38-054B-B048-3B0A-E75027061982";//CONFIGURAZIONE UUID DI CLIENTE

    public GgalleryBeaconListenerIntentService() {
        super("GgalleryBeaconListenerIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {


            // init BLE
            btManager = (BluetoothManager)getSystemService(Context.BLUETOOTH_SERVICE);
            btAdapter = btManager.getAdapter();
            beacons=new ArrayList<>();

            scanHandler.post(scanRunnable);

        }
    }

    /**
     * Handle action Foo in the provided background thread with the provided
     * parameters.
     */
    private void handleActionFoo(String param1, String param2) {
        // TODO: Handle action Foo
        throw new UnsupportedOperationException("Not yet implemented");
    }

    /**
     * Handle action Baz in the provided background thread with the provided
     * parameters.
     */
    private void handleActionBaz(String param1, String param2) {
        // TODO: Handle action Baz
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private Runnable scanRunnable = new Runnable()
    {
        String nearest_beacon_uuid;
        Integer nearest_beacon_min;
        Integer nearest_beacon_Max;
        Integer nearest_beacon_rssi;

        @Override
        public void run() {

            if (isScanning)
            {
                if (btAdapter != null)
                {
                    btAdapter.stopLeScan(leScanCallback);
                }
            }
            else
            {
                if (btAdapter != null)
                {
                    if(!beacons.isEmpty()) {
                        beacons.clear();
                    }
                    btAdapter.startLeScan(leScanCallback);
                }
            }

            isScanning = !isScanning;
            if(beacons!=null) {
                if (!beacons.isEmpty()) {

                    nearest_beacon_uuid = "";
                    nearest_beacon_rssi = -1000;
                    nearest_beacon_min=0;
                    nearest_beacon_Max=0;

                    for (Beacon beacon : beacons) {
                        if (beacon.getRssi() > nearest_beacon_rssi) {

                            nearest_beacon_rssi = beacon.getRssi();
                            nearest_beacon_uuid = beacon.getUuid();
                            nearest_beacon_Max=beacon.getMajor();
                            nearest_beacon_min=beacon.getMinor();

                        }
                    }

                    Beacon nearestbeacon = new Beacon(nearest_beacon_uuid, nearest_beacon_rssi,nearest_beacon_Max,nearest_beacon_min);
                    Global global = (Global)getApplicationContext();
                    global.setNearestBeacon(nearestbeacon);


                }
            }
            scanHandler.postDelayed(this, scan_interval_ms);
        }
    };
    private BluetoothAdapter.LeScanCallback leScanCallback = new BluetoothAdapter.LeScanCallback()
    {
        @Override
        public void onLeScan(final BluetoothDevice device, final int rssi, final byte[] scanRecord)
        {
            // label.setText("nothing");
            int startByte = 2;
            boolean patternFound = false;
            while (startByte <= 5)
            {
                if (    ((int) scanRecord[startByte + 2] & 0xff) == 0x02 && //Identifies an iBeacon
                        ((int) scanRecord[startByte + 3] & 0xff) == 0x15)
                { //Identifies correct data length
                    patternFound = true;
                    break;
                }
                startByte++;
            }

            if (patternFound)
            {
                //Convert to hex String
                byte[] uuidBytes = new byte[16];
                System.arraycopy(scanRecord, startByte + 4, uuidBytes, 0, 16);
                String hexString = bytesToHex(uuidBytes);

                //UUID detection
                String uuid =  hexString.substring(0,8) + "-" +
                        hexString.substring(8,12) + "-" +
                        hexString.substring(12,16) + "-" +
                        hexString.substring(16,20) + "-" +
                        hexString.substring(20,32);

                // major
                final int major = (scanRecord[startByte + 20] & 0xff) * 0x100 + (scanRecord[startByte + 21] & 0xff);

                // minor
                final int minor = (scanRecord[startByte + 22] & 0xff) * 0x100 + (scanRecord[startByte + 23] & 0xff);


                if(uuid.equals(LocationUUid)) {
                    Beacon beacon = new Beacon(uuid, rssi, major, minor);
                    beacons.add(beacon);
                }
            }

        }
    };

    static final char[] hexArray = "0123456789ABCDEF".toCharArray();
    private static String bytesToHex(byte[] bytes)
    {
        char[] hexChars = new char[bytes.length * 2];
        for ( int j = 0; j < bytes.length; j++ )
        {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }


}
