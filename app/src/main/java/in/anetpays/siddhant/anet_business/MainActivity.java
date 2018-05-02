package in.anetpays.siddhant.anet_business;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.github.omadahealth.lollipin.lib.PinCompatActivity;
import com.github.omadahealth.lollipin.lib.managers.AppLock;
import com.mikepenz.crossfader.Crossfader;
import com.mikepenz.crossfader.util.UIUtils;
import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.MiniDrawer;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.SectionDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;
import com.mikepenz.materialdrawer.model.interfaces.Nameable;

import in.anetpays.siddhant.anet_business.Constants.SharedPreferencesConstants;
import in.anetpays.siddhant.anet_business.Extras.CrossfadeWrapper;
import in.anetpays.siddhant.anet_business.Extras.CustomPinActivity;
import in.anetpays.siddhant.anet_business.UI.AboutApp;
import in.anetpays.siddhant.anet_business.UI.RuntimePermissionUtil;
import in.anetpays.siddhant.anet_business.UI.StartPayment;
import in.anetpays.siddhant.anet_business.UI.UserHelp;
import in.anetpays.siddhant.anet_business.UI.UserProfile;
import in.anetpays.siddhant.anet_business.UI.UserTransactions;

import static in.anetpays.siddhant.anet_business.Constants.SharedPreferencesConstants.PREF_FIRST_RUN;
import static in.anetpays.siddhant.anet_business.Constants.SharedPreferencesConstants.PREF_LOGIN;
import static in.anetpays.siddhant.anet_business.Constants.SharedPreferencesConstants.PREF_MISC;

public class MainActivity extends PinCompatActivity {

    private Drawer drawerResult = null;
    private AccountHeader accountHeader = null;
    private MiniDrawer miniDrawer = null;
    private Crossfader crossfader;
    private SharedPreferences preferences, preferences2;
    private FragmentManager fragmentManager;
    public Fragment fragment;
    private static final int REQUEST_CODE_ENABLE = 11;
    private static final String cameraPermission = Manifest.permission.CAMERA;
    private Snackbar snackbar;
    private CoordinatorLayout coordinatorLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main_activity);
        fragmentManager = getSupportFragmentManager();
        preferences = getSharedPreferences(PREF_LOGIN, MODE_PRIVATE);
        preferences2 = getSharedPreferences(PREF_MISC, MODE_PRIVATE);
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout);


        Boolean isFirstRun = getSharedPreferences(PREF_FIRST_RUN, MODE_PRIVATE).getBoolean(SharedPreferencesConstants.isFIRSTrun, true);
        if (isFirstRun)
        {
            Intent intent = new Intent(this, CustomPinActivity.class);
            intent.putExtra(AppLock.EXTRA_TYPE, AppLock.ENABLE_PINLOCK);
            startActivityForResult(intent, REQUEST_CODE_ENABLE);
            getSharedPreferences(PREF_FIRST_RUN, MODE_PRIVATE).edit().putBoolean(SharedPreferencesConstants.isFIRSTrun, false).apply();
        }
        else
        {
            snackbar = Snackbar.make(coordinatorLayout, "Error", Snackbar.LENGTH_SHORT);
            snackbar.show();
        }

        if (preferences2.getBoolean(SharedPreferencesConstants.hasCameraPer, false))
        {
            snackbar = Snackbar.make(coordinatorLayout, "It Has Camera Permissions", Snackbar.LENGTH_SHORT);
            snackbar.show();
        }
        else
        { try
        {
            RuntimePermissionUtil.requestPermission(MainActivity.this, cameraPermission, 100);
            snackbar = Snackbar.make(coordinatorLayout, "Asking for Permissions Fruitful", Snackbar.LENGTH_SHORT);
            snackbar.show();
        }
            catch (Exception e)
            {
                e.printStackTrace();
                snackbar = Snackbar.make(coordinatorLayout, "Could not Take camera permissions", Snackbar.LENGTH_SHORT);
                snackbar.show();
            }
        }

        String tempName = preferences.getString(SharedPreferencesConstants.NAME, "");
        String tempEmail = preferences.getString(SharedPreferencesConstants.EMAIL, "");
        String tempURL = preferences.getString(SharedPreferencesConstants.URLImage, "");

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.app_name);

        final IProfile profile = new ProfileDrawerItem().withName(preferences.getString(SharedPreferencesConstants.NAME, "NAME"))
                .withEmail(preferences.getString(SharedPreferencesConstants.EMAIL, "EMAIL")).withIcon(tempURL);

        accountHeader = new AccountHeaderBuilder()
                .withActivity(this)
                .withHeaderBackground(R.drawable.header)
                .withTranslucentStatusBar(false)
                .addProfiles(
                        profile
                )
                .withSelectionListEnabledForSingleProfile(false)
                .withSavedInstance(savedInstanceState)
                .build();

        drawerResult = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(toolbar)
                .withTranslucentStatusBar(false)
                .withAccountHeader(accountHeader)
                .addDrawerItems(
                        new PrimaryDrawerItem().withName("Profile").withIcon(GoogleMaterial.Icon.gmd_person).withIdentifier(1),
                        new PrimaryDrawerItem().withName("Accept Payment").withIcon(GoogleMaterial.Icon.gmd_monetization_on).withIdentifier(2),
                        new PrimaryDrawerItem().withName("Transactions").withIcon(GoogleMaterial.Icon.gmd_history).withIdentifier(3),
                        new SectionDrawerItem().withName("Extras"),
                        new SecondaryDrawerItem().withName("About").withIcon(GoogleMaterial.Icon.gmd_cached).withIdentifier(4),
                        new SecondaryDrawerItem().withName("Help").withIcon(GoogleMaterial.Icon.gmd_help).withIdentifier(5)
                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        if (drawerItem instanceof Nameable)
                        {
                            if (drawerItem.getIdentifier() == 2)
                            {
                                Intent intent = new Intent(MainActivity.this, StartPayment.class);
                                startActivity(intent);
                                /*fragmentManager
                                        .beginTransaction()
                                        .replace(R.id.crossfade_content, new AcceptPayment())
                                        .addToBackStack(null)
                                        .commitAllowingStateLoss();*/
                            }
                            else if (drawerItem.getIdentifier() == 3)
                            {
                                fragmentManager
                                        .beginTransaction()
                                        .replace(R.id.crossfade_content, new UserTransactions())
                                        .addToBackStack(null)
                                        .commitAllowingStateLoss();
                            }

                            else if (drawerItem.getIdentifier() == 1)
                            {
                                fragmentManager
                                        .beginTransaction()
                                        .replace(R.id.crossfade_content, new UserProfile())
                                        .addToBackStack(null)
                                        .commitAllowingStateLoss();
                            }

                            else if (drawerItem.getIdentifier() == 5)
                            {
                                fragmentManager
                                        .beginTransaction()
                                        .replace(R.id.crossfade_content, new UserHelp())
                                        .addToBackStack(null)
                                        .commitAllowingStateLoss();
                            }
                            else if(drawerItem.getIdentifier() == 4)
                            {
                                fragmentManager
                                        .beginTransaction()
                                        .replace(R.id.crossfade_content, new AboutApp())
                                        .addToBackStack(null)
                                        .commitAllowingStateLoss();
                            }
                            else
                            {
                                fragmentManager
                                        .beginTransaction()
                                        .replace(R.id.crossfade_content, new UserProfile())
                                        .addToBackStack(null)
                                        .commitAllowingStateLoss();
                            }
                        }
                        return false;
                    }
                })
                .withGenerateMiniDrawer(true)
                .withSavedInstance(savedInstanceState)
                .buildView();
        miniDrawer =  drawerResult.getMiniDrawer();

        int firstWidth = (int) UIUtils.convertDpToPixel(300, this);
        int SecondWidth = (int) UIUtils.convertDpToPixel(72, this);

        crossfader = new Crossfader()
                .withContent(findViewById(R.id.crossfade_content))
                .withFirst(drawerResult.getSlider(),firstWidth)
                .withSecond(miniDrawer.build(this), SecondWidth)
                .withSavedInstance(savedInstanceState)
                .build();

        miniDrawer.withCrossFader(new CrossfadeWrapper(crossfader));
        crossfader.getCrossFadeSlidingPaneLayout().setShadowResourceLeft(R.drawable.material_drawer_shadow_left);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode){
            case REQUEST_CODE_ENABLE:
                Toast.makeText(this, "PinCode enabled", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
