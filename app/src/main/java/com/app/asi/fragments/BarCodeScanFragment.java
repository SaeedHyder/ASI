package com.app.asi.fragments;

import android.app.AlertDialog;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.asi.R;
import com.app.asi.activities.MainActivity;
import com.app.asi.fragments.abstracts.BaseFragment;
import com.app.asi.helpers.UIHelper;
import com.app.asi.interfaces.BarCodeValue;
import com.app.asi.ui.views.TitleBar;
import com.google.android.gms.samples.vision.barcodereader.BarcodeCapture;
import com.google.android.gms.samples.vision.barcodereader.BarcodeGraphic;
import com.google.android.gms.vision.barcode.Barcode;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import xyz.belvi.mobilevisionbarcodescanner.BarcodeRetriever;

public class BarCodeScanFragment extends BaseFragment implements BarcodeRetriever {
    private BarcodeCapture barcodeCapture;
    private BarCodeValue barCodeValue;

    public static BarCodeScanFragment newInstance() {
        Bundle args = new Bundle();

        BarCodeScanFragment fragment = new BarCodeScanFragment();
        fragment.setArguments(args);
        return fragment;
    }
    public void setBarCOdeListner(BarCodeValue barCodeValue){
        this.barCodeValue=barCodeValue;
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        if (getArguments() != null) {
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_barcode, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        barcodeCapture = (BarcodeCapture) getChildFragmentManager().findFragmentById(R.id.barcode);
        barcodeCapture.setRetrieval(this);
        barcodeCapture.setShowDrawRect(true);
        barcodeCapture.setShouldShowText(true);


    }

    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideTitleBar();
    }

    @Override
    public void onRetrieved(Barcode barcode) {
       getDockActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
               /* AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this)
                        .setTitle("code retrieved")
                        .setMessage(barcode.displayValue);
                builder.show();*/
                barCodeValue.getValue(barcode);
                getDockActivity().popFragment();
            }
        });



    }

    @Override
    public void onRetrievedMultiple(Barcode closetToClick, List<BarcodeGraphic> barcode) {

    }

    @Override
    public void onBitmapScanned(SparseArray<Barcode> sparseArray) {

    }

    @Override
    public void onRetrievedFailed(String reason) {

    }

    @Override
    public void onPermissionRequestDenied() {

    }

}
