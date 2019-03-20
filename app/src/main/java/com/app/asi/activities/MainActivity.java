package com.app.asi.activities;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.widget.DrawerLayout;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.app.asi.R;
import com.app.asi.fragments.HomeFragment;
import com.app.asi.fragments.LoginFragment;
import com.app.asi.fragments.SideMenuFragment;
import com.app.asi.fragments.WelcomeScreenFragment;
import com.app.asi.fragments.abstracts.BaseFragment;
import com.app.asi.global.AppConstants;
import com.app.asi.global.SideMenuChooser;
import com.app.asi.global.SideMenuDirection;
import com.app.asi.helpers.ScreenHelper;
import com.app.asi.helpers.UIHelper;
import com.app.asi.interfaces.ImageSetter;
import com.app.asi.interfaces.OnSettingActivateListener;
import com.app.asi.residemenu.ResideMenu;

import com.app.asi.ui.views.TitleBar;

import com.kbeanie.imagechooser.api.ChooserType;
import com.kbeanie.imagechooser.api.ChosenImage;
import com.kbeanie.imagechooser.api.ChosenImages;
import com.kbeanie.imagechooser.api.ImageChooserListener;
import com.kbeanie.imagechooser.api.ImageChooserManager;
import com.wang.avi.AVLoadingIndicatorView;


import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.app.asi.global.AppConstants.deletePush;
import static com.app.asi.global.AppConstants.inactivePush;


public class MainActivity extends DockActivity implements View.OnClickListener, ImageChooserListener {
    private final static String TAG = "MainActivity";
    public TitleBar titleBar;
    @BindView(R.id.sideMneuFragmentContainer)
    public FrameLayout sideMneuFragmentContainer;
    @BindView(R.id.header_main)
    TitleBar header_main;
    @BindView(R.id.mainFrameLayout)
    FrameLayout mainFrameLayout;
    @BindView(R.id.progressBar)
    AVLoadingIndicatorView progressBar;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;

    private MainActivity mContext;
    private boolean loading;

    public ResideMenu resideMenu;
    private ImageChooserManager imageChooserManager;
    private int chooserType;
    private String filePath;
    private String originalFilePath;
    private ImageSetter imageSetter;
    private String thumbnailFilePath;
    private String thumbnailSmallFilePath;
    private boolean isActivityResultOver = false;

    private float lastTranslate = 0.0f;

    private String sideMenuType;
    private String sideMenuDirection;
    private AlertDialog alert;
    public final int WifiResultCode = 2;
    public final int LocationResultCode = 1;
    private String address = "";
    private String country = "";
    private LocationManager locationManager;
    private OnSettingActivateListener settingActivateListener;
    protected BroadcastReceiver broadcastReceiver;
    private boolean isFirstTime = true;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dock);
        ButterKnife.bind(this);
        titleBar = header_main;
        // setBehindContentView(R.layout.fragment_frame);
        mContext = this;
        Log.i("Screen Density", ScreenHelper.getDensity(this) + "");

        sideMenuType = SideMenuChooser.DRAWER.getValue();
        sideMenuDirection = SideMenuDirection.LEFT.getValue();
        lockDrawer();

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        settingSideMenu(sideMenuType, sideMenuDirection);
        printHashKey(getDockActivity());
        //onNotificationReceived();

       //setCurrentLocale();

        titleBar.setMenuButtonListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (sideMenuType.equals(SideMenuChooser.DRAWER.getValue()) && getDrawerLayout() != null) {
                    if (sideMenuDirection.equals(SideMenuDirection.LEFT.getValue())) {
                        InputMethodManager inputMethodManager = (InputMethodManager) getDockActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
                        inputMethodManager.hideSoftInputFromWindow(getDockActivity().getCurrentFocus().getWindowToken(), 0);
                        drawerLayout.openDrawer(Gravity.LEFT);
                    } else {
                        drawerLayout.openDrawer(Gravity.RIGHT);
                    }
                } else {
                    resideMenu.openMenu(sideMenuDirection);
                }

            }
        });

        titleBar.setBackButtonListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (loading) {
                    UIHelper.showLongToastInCenter(getApplicationContext(),
                            R.string.message_wait);
                } else {

                    popFragment();
                    UIHelper.hideSoftKeyboard(getApplicationContext(),
                            titleBar);
                }
            }
        });

        titleBar.setNotificationButtonListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            //    replaceDockableFragment(NotificationsFragment.newInstance(), "NotificationsFragment");
            }
        });

        //  if (savedInstanceState == null)
        initFragment();

        // ATTENTION: This was auto-generated to handle app links.


    }

    private void setCurrentLocale() {
        if (prefHelper.isLanguageArabian()) {
            Resources resources = getResources();
            DisplayMetrics dm = resources.getDisplayMetrics();
            Configuration conf = resources.getConfiguration();
            conf.locale = new Locale("ar");
            resources.updateConfiguration(conf, dm);

        } else {
            Resources resources = getResources();
            DisplayMetrics dm = resources.getDisplayMetrics();
            Configuration conf = resources.getConfiguration();
            conf.locale = new Locale("en");
            resources.updateConfiguration(conf, dm);

        }
    }

    public DrawerLayout getDrawerLayout() {
        return drawerLayout;
    }


    public void setOnSettingActivateListener(OnSettingActivateListener ActivateListener) {
        this.settingActivateListener = ActivateListener;
    }

    public View getDrawerView() {
        return getLayoutInflater().inflate(getSideMenuFrameLayoutId(), null);
    }

    private void settingSideMenu(String type, String direction) {

        if (type.equals(SideMenuChooser.DRAWER.getValue())) {


            DrawerLayout.LayoutParams params = new DrawerLayout.LayoutParams((int) getResources().getDimension(R.dimen.x250), (int) DrawerLayout.LayoutParams.MATCH_PARENT);


            if (direction.equals(SideMenuDirection.LEFT.getValue())) {
                params.gravity = Gravity.LEFT;
                sideMneuFragmentContainer.setLayoutParams(params);
            } else {
                params.gravity = Gravity.RIGHT;
                sideMneuFragmentContainer.setLayoutParams(params);
            }
            drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

            sideMenuFragment = SideMenuFragment.newInstance();
            FragmentTransaction transaction = getSupportFragmentManager()
                    .beginTransaction();
            transaction.replace(getSideMenuFrameLayoutId(), sideMenuFragment).commit();

            drawerLayout.closeDrawers();
        } else {
            resideMenu = new ResideMenu(this);
            resideMenu.attachToActivity(this);
            resideMenu.setMenuListener(getMenuListener());
            resideMenu.setScaleValue(0.52f);

            setMenuItemDirection(direction);
        }
    }

    private void setMenuItemDirection(String direction) {

        if (direction.equals(SideMenuDirection.LEFT.getValue())) {

            SideMenuFragment leftSideMenuFragment = SideMenuFragment.newInstance();
            resideMenu.addMenuItem(leftSideMenuFragment, "LeftSideMenuFragment", direction);

        } else if (direction.equals(SideMenuDirection.RIGHT.getValue())) {

            SideMenuFragment rightSideMenuFragment = SideMenuFragment.newInstance();
            resideMenu.addMenuItem(rightSideMenuFragment, "RightSideMenuFragment", direction);

        }

    }

    private int getSideMenuFrameLayoutId() {
        return R.id.sideMneuFragmentContainer;

    }


    public void initFragment() {
        getSupportFragmentManager().addOnBackStackChangedListener(getListener());
        Intent intent = getIntent();
        if (prefHelper.isLogin()) {
            popBackStackTillEntry(0);
            replaceDockableFragment(HomeFragment.newInstance(), "HomeFragment", false);
        } else {
            replaceDockableFragment(WelcomeScreenFragment.newInstance(), "WelcomeScreenFragment", false);
        }

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            String Type = bundle.getString("actionType");
            String Title = bundle.getString("title");
            String id = bundle.getString("redId");

            if (prefHelper.isLogin()) {
               /* if (Type != null && Type.equals(companyPush)) {
                    replaceDockableFragment(ServiceDetailFragment.newInstance(id), "ServiceDetailFragment");
                } else if (Type != null && Type.equals(chatPush)) {
                    if (id != null) {
                        replaceDockableFragment(ChatFragment.newInstance(id), "ChatFragment");
                    }
                } else if (Type != null && Title != null && !Title.equals("")) {
                    replaceDockableFragment(NotificationsFragment.newInstance(), "NotificationsFragment");
                }*/

            }
        }
    }

    private void onNotificationReceived() {
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                if (intent.getAction().equals(AppConstants.PUSH_NOTIFICATION)) {

                    Bundle bundle = intent.getExtras();
                    if (bundle != null) {
                        String Type = bundle.getString("actionType");

                        if (Type != null && Type.equals(deletePush)) {
                            UIHelper.showShortToastInDialoge(getDockActivity(), getDockActivity().getResources().getString(R.string.deleted_by_admin));
                            getDockActivity().popBackStackTillEntry(0);
                            prefHelper.setLoginStatus(false);
                            prefHelper.setSocialLogin(false);
                           /* if (AccessToken.getCurrentAccessToken() != null) {
                                LoginManager.getInstance().logOut();
                            }*/
                            NotificationManager notificationManager = (NotificationManager) getDockActivity().getSystemService(Context.NOTIFICATION_SERVICE);
                            notificationManager.cancelAll();
                            getDockActivity().replaceDockableFragment(LoginFragment.newInstance(), "LoginFragment");
                        } else if (Type != null && Type.equals(inactivePush)) {
                            UIHelper.showShortToastInDialoge(getDockActivity(), getDockActivity().getResources().getString(R.string.blocked_by_admin));
                            getDockActivity().popBackStackTillEntry(0);
                            prefHelper.setLoginStatus(false);
                            prefHelper.setSocialLogin(false);
                          /*  if (AccessToken.getCurrentAccessToken() != null) {
                                LoginManager.getInstance().logOut();
                            }*/
                            NotificationManager notificationManager = (NotificationManager) getDockActivity().getSystemService(Context.NOTIFICATION_SERVICE);
                            notificationManager.cancelAll();
                            getDockActivity().replaceDockableFragment(LoginFragment.newInstance(), "LoginFragment");
                        }
                    }
                }

            }

        };
    }

    private FragmentManager.OnBackStackChangedListener getListener() {
        FragmentManager.OnBackStackChangedListener result = new FragmentManager.OnBackStackChangedListener() {
            public void onBackStackChanged() {
                FragmentManager manager = getSupportFragmentManager();

                if (manager != null) {
                    BaseFragment currFrag = (BaseFragment) manager.findFragmentById(getDockFrameLayoutId());
                    if (currFrag != null) {
                        currFrag.fragmentResume();
                    }

                }
            }
        };

        return result;
    }

    @Override
    public void onLoadingStarted() {

        if (mainFrameLayout != null) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
            mainFrameLayout.setVisibility(View.VISIBLE);
            if (progressBar != null) {
                progressBar.show();
                progressBar.setVisibility(View.VISIBLE);
                getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                        WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
            }
            loading = true;
        }
    }

    @Override
    public void onLoadingFinished() {
        mainFrameLayout.setVisibility(View.VISIBLE);

        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        if (progressBar != null) {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
            progressBar.setVisibility(View.INVISIBLE);
            progressBar.hide();
        }
        loading = false;

    }

    @Override
    public void onProgressUpdated(int percentLoaded) {

    }

    @Override
    public int getDockFrameLayoutId() {
        return R.id.mainFrameLayout;
    }

    @Override
    public void onMenuItemActionCalled(int actionId, String data) {

    }

    @Override
    public void setSubHeading(String subHeadText) {

    }

    @Override
    public boolean isLoggedIn() {
        return false;
    }

    @Override
    public void hideHeaderButtons(boolean leftBtn, boolean rightBtn) {
    }

    @Override
    public void onBackPressed() {
        if (loading) {
            UIHelper.showLongToastInCenter(getApplicationContext(),
                    R.string.message_wait);
        } else
            super.onBackPressed();

    }

    @Override
    public void onClick(View view) {

    }

    public void setImageSetter(ImageSetter imageSetter) {
        this.imageSetter = imageSetter;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK
                && (requestCode == ChooserType.REQUEST_PICK_PICTURE || requestCode == ChooserType.REQUEST_CAPTURE_PICTURE)) {
            if (imageChooserManager == null) {
                reinitializeImageChooser();
            }
            imageChooserManager.submit(requestCode, data);
        }
        if (resultCode == Activity.RESULT_OK) {
            if (settingActivateListener != null)
                settingActivateListener.onLocationActivateListener();
        }


    }

    private void reinitializeImageChooser() {
        imageChooserManager = new ImageChooserManager(this, chooserType, true);
        Bundle bundle = new Bundle();
        bundle.putBoolean(Intent.EXTRA_ALLOW_MULTIPLE, false);
        imageChooserManager.setExtras(bundle);
        imageChooserManager.setImageChooserListener(this);
        imageChooserManager.reinitialize(filePath);
    }

    public void chooseImage() {
        chooserType = ChooserType.REQUEST_PICK_PICTURE;
        imageChooserManager = new ImageChooserManager(this,
                ChooserType.REQUEST_PICK_PICTURE, true);
        Bundle bundle = new Bundle();
        bundle.putBoolean(Intent.EXTRA_ALLOW_MULTIPLE, true);
        imageChooserManager.setExtras(bundle);
        imageChooserManager.setImageChooserListener(this);
        imageChooserManager.clearOldFiles();
        try {
            //pbar.setVisibility(View.VISIBLE);
            filePath = imageChooserManager.choose();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void takePicture() {
        chooserType = ChooserType.REQUEST_CAPTURE_PICTURE;
        imageChooserManager = new ImageChooserManager(this,
                ChooserType.REQUEST_CAPTURE_PICTURE, true);
        imageChooserManager.setImageChooserListener(this);
        try {
            //pbar.setVisibility(View.VISIBLE);
            filePath = imageChooserManager.choose();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onImageChosen(final ChosenImage image) {
        runOnUiThread(new Runnable() {

            @Override
            public void run() {
                Log.i(TAG, "Chosen Image: O - " + image.getFilePathOriginal());
                Log.i(TAG, "Chosen Image: T - " + image.getFileThumbnail());
                Log.i(TAG, "Chosen Image: Ts - " + image.getFileThumbnailSmall());
                isActivityResultOver = true;
                originalFilePath = image.getFilePathOriginal();
                thumbnailFilePath = image.getFileThumbnail();
                thumbnailSmallFilePath = image.getFileThumbnailSmall();
                //pbar.setVisibility(View.GONE);
                if (image != null) {
                    Log.i(TAG, "Chosen Image: Is not null");

                    // Toast.makeText(getApplication(),thumbnailFilePath,Toast.LENGTH_LONG).show();
                    imageSetter.setImage(originalFilePath);

                    //loadImage(imageViewThumbnail, image.getFileThumbnail());
                } else {
                    Log.i(TAG, "Chosen Image: Is null");
                }

            }
        });

    }

    @Override
    public void onError(final String reason) {
        runOnUiThread(new Runnable() {

            @Override
            public void run() {
                Log.i(TAG, "OnError: " + reason);
                // pbar.setVisibility(View.GONE);
                Toast.makeText(MainActivity.this, reason,
                        Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onImagesChosen(final ChosenImages images) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Log.i(TAG, "On Images Chosen: " + images.size());
                onImageChosen(images.getImage(0));
            }
        });

    }


    public String getCurrentAddress(double lat, double lng) {
        try {


            Geocoder geocoder;
            List<Address> addresses;
            geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
            addresses = geocoder.getFromLocation(lat, lng, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
            if (addresses.size() > 0) {
                address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
            }
// String city = addresses.get(0).getLocality();
// String state = addresses.get(0).getAdminArea();
            if (addresses.size() > 0) {
                country = addresses.get(0).getCountryName();
            }
// String postalCode = addresses.get(0).getPostalCode();
// String knownName = addresses.get(0).getFeatureName(); // Only if available else return NULL
            return address + ", " + country;

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void printHashKey(Context pContext) {
        try {
            PackageInfo info = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                String hashKey = new String(Base64.encode(md.digest(), 0));
                Log.i(TAG, "printHashKey() Hash Key: " + hashKey);
            }
        } catch (NoSuchAlgorithmException e) {
            Log.e(TAG, "printHashKey()", e);
        } catch (Exception e) {
            Log.e(TAG, "printHashKey()", e);
        }
    }

    public void refreshSideMenu() {
        sideMenuFragment.refreshSideMenuData();
    }
    public void guestSideMenu() {
        sideMenuFragment.setGuestSideMenu();
    }
    public void refreshSideMenuData() {
        sideMenuFragment.setProfile();
    }

    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(getDockActivity()).unregisterReceiver(broadcastReceiver);
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance(getDockActivity()).registerReceiver(broadcastReceiver,
                new IntentFilter(AppConstants.REGISTRATION_COMPLETE));

        LocalBroadcastManager.getInstance(getDockActivity()).registerReceiver(broadcastReceiver,
                new IntentFilter(AppConstants.PUSH_NOTIFICATION));

       /* if (!prefHelper.isLogin() && !isFirstTime) {
            replaceDockableFragment(LoginFragment.newInstance(), "LoginFragment");
        }*/

    }

    @Override
    protected void onStop() {
        super.onStop();
        isFirstTime = false;
    }

    public void lockDrawer() {
        try {
            drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void closeDrawer() {
        drawerLayout.closeDrawers();

    }

    public void releaseDrawer() {
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
    }

    public void restartActivity() {
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }


}
