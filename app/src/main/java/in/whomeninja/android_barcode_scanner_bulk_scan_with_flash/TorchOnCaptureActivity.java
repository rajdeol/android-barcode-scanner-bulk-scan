package in.whomeninja.android_barcode_scanner_bulk_scan_with_flash;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.KeyEvent;
import android.view.View;

import com.journeyapps.barcodescanner.CaptureManager;
import com.journeyapps.barcodescanner.DecoratedBarcodeView;

/**
 * Created by RajinderPal on 1/29/2017.
 */

public class TorchOnCaptureActivity extends Activity {
    private CaptureManager capture;
    private DecoratedBarcodeView barcodeScannerView;
    private View turnflashOn, turnflashOff;
    private boolean cameraFlashOn = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        barcodeScannerView = initializeContent();
        TorchEventListener torchEventListener = new TorchEventListener(this);
        barcodeScannerView.setTorchListener(torchEventListener);

        turnflashOn = findViewById(R.id.switch_flashlight_on);
        turnflashOff = findViewById(R.id.switch_flashlight_off);

        // turn the flash on if set via intent
        Intent scanIntent = getIntent();
        if(scanIntent.hasExtra(appConstants.CAMERA_FLASH_ON)){
            if(scanIntent.getBooleanExtra(appConstants.CAMERA_FLASH_ON,false)){
                barcodeScannerView.setTorchOn();
                updateView();
            }
        }

        capture = new CaptureManager(this, barcodeScannerView);
        capture.initializeFromIntent(getIntent(), savedInstanceState);
        capture.decode();
    }

    /**
     * Override to use a different layout.
     *
     * @return the DecoratedBarcodeView
     */
    protected DecoratedBarcodeView initializeContent() {
        setContentView(R.layout.capture_flash);
        //setContentView(com.google.zxing.client.android.R.layout.zxing_capture);
        return (DecoratedBarcodeView)findViewById(com.google.zxing.client.android.R.id.zxing_barcode_scanner);
    }

    @Override
    protected void onResume() {
        super.onResume();
        capture.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        capture.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        capture.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        capture.onSaveInstanceState(outState);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        capture.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return barcodeScannerView.onKeyDown(keyCode, event) || super.onKeyDown(keyCode, event);
    }

    public void toggleFlash(View view){
        if(cameraFlashOn){
            barcodeScannerView.setTorchOff();
        }else{
            barcodeScannerView.setTorchOn();
        }
    }

    public void updateView(){
        if(cameraFlashOn){
            turnflashOn.setVisibility(View.GONE);
            turnflashOff.setVisibility(View.VISIBLE);
        }else{
            turnflashOn.setVisibility(View.VISIBLE);
            turnflashOff.setVisibility(View.GONE);
        }
    }

    class TorchEventListener implements DecoratedBarcodeView.TorchListener{
        private TorchOnCaptureActivity activity;

        TorchEventListener(TorchOnCaptureActivity activity){
            this.activity = activity;
        }

        @Override
        public void onTorchOn() {
            this.activity.cameraFlashOn = true;
            this.activity.updateView();
        }

        @Override
        public void onTorchOff() {
            this.activity.cameraFlashOn = false;
            this.activity.updateView();
        }
    }
}
