/*
 *  Copyright (C) 2013 The OmniROM Project
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package org.omnirom.omniswitch;

import java.util.List;
import java.util.Map;
import java.util.ArrayList;

import org.omnirom.omniswitch.ui.BitmapUtils;
import org.omnirom.omniswitch.ui.CheckboxListDialog;
import org.omnirom.omniswitch.ui.IconPackHelper;
import org.omnirom.omniswitch.ui.NumberPickerPreference;
import org.omnirom.omniswitch.ui.SeekBarPreference;
import org.omnirom.omniswitch.ui.SettingsGestureView;
import org.omnirom.omniswitch.ui.FavoriteDialog;

import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.preference.PreferenceScreen;
import android.preference.SwitchPreference;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Switch;
import android.widget.Toast;

public class SettingsActivity extends PreferenceActivity implements
        OnPreferenceChangeListener, IEditFavoriteActivity  {
    private static final String TAG = "SettingsActivity";

    public static final String PREF_OPACITY = "opacity";
    public static final String PREF_ANIMATE = "animate";
    public static final String PREF_ICON_SIZE = "icon_size";
    public static final String PREF_DRAG_HANDLE_LOCATION = "drag_handle_location_new";
    private static final String PREF_ADJUST_HANDLE = "adjust_handle";
    public static final String PREF_DRAG_HANDLE_COLOR_NEW = "drag_handle_color_new";
    public static final String PREF_SHOW_RAMBAR = "show_rambar";
    public static final String PREF_SHOW_LABELS = "show_labels";
    public static final String PREF_FAVORITE_APPS_CONFIG = "favorite_apps_config";
    public static final String PREF_FAVORITE_APPS = "favorite_apps";
    public static final String PREF_HANDLE_POS_START_RELATIVE = "handle_pos_start_relative";
    public static final String PREF_HANDLE_HEIGHT = "handle_height";
    public static final String PREF_BUTTON_CONFIG = "button_config";
    public static final String PREF_BUTTONS_NEW = "buttons_new";
    public static final String PREF_BUTTON_DEFAULT_NEW = "0:1,1:1,2:1,3:0,4:1,5:1,6:0,7:1,8:1,9:0";
    public static final String PREF_AUTO_HIDE_HANDLE = "auto_hide_handle";
    public static final String PREF_DRAG_HANDLE_ENABLE = "drag_handle_enable";
    public static final String PREF_ENABLE = "enable";
    public static final String PREF_DIM_BEHIND = "dim_behind";
    public static final String PREF_ICONPACK = "iconpack";
    public static final String PREF_SPEED_SWITCHER = "speed_switcher";
    public static final String PREF_SHOW_FAVORITE = "show_favorite";
    public static final String PREF_SPEED_SWITCHER_COLOR = "speed_switch_color";
    public static final String PREF_SPEED_SWITCHER_LIMIT = "speed_switch_limit";
    public static final String PREF_SPEED_SWITCHER_BUTTON_CONFIG = "speed_switch_button_config";
    public static final String PREF_SPEED_SWITCHER_BUTTON_NEW = "speed_switch_button_new";
    public static final String PREF_SPEED_SWITCHER_BUTTON_DEFAULT_NEW = "0:0,1:0,2:1,3:1,4:1,5:1,6:1,7:0";
    public static final String PREF_SPEED_SWITCHER_ITEMS = "speed_switch_items";
    public static final String PREF_BUTTON_POS = "button_pos";
    public static final String PREF_BG_STYLE = "bg_style";
    public static final String PREF_APP_FILTER_BOOT = "app_filter_boot";
    public static final String PREF_LAYOUT_STYLE = "layout_style";
    public static final String PREF_APP_FILTER_TIME = "app_filter_time";
    public static final String PREF_THUMB_SIZE = "thumb_size";
    public static final String PREF_APP_FILTER_RUNNING = "app_filter_running";
    public static final String PREF_HANDLE_WIDTH = "handle_width";
    public static final String PREF_LAUNCHER_MODE = "launcher_mode";
    public static final String PREF_REVERT_RECENTS = "revert_recents";
    public static final String PREF_SWIPE_THUMB_UPDATE = "swipe_thumb_update";
    public static final String PREF_DIM_ACTION_BUTTON ="dim_action_buttons";
    public static final String PREF_LOCKED_APPS_LIST ="locked_apps_list";
    public static final String PREF_LOCKED_APPS_SORT ="locked_apps_sort";

    public static int BUTTON_KILL_ALL = 0;
    public static int BUTTON_KILL_OTHER = 1;
    public static int BUTTON_TOGGLE_APP = 2;
    public static int BUTTON_HOME = 3;
    public static int BUTTON_SETTINGS = 4;
    public static int BUTTON_ALLAPPS = 5;
    public static int BUTTON_BACK = 6;
    public static int BUTTON_LOCK_APP = 7;
    public static int BUTTON_CLOSE = 8;
    public static int BUTTON_MENU = 9;

    public static int BUTTON_SPEED_SWITCH_HOME = 0;
    public static int BUTTON_SPEED_SWITCH_BACK = 1;
    public static int BUTTON_SPEED_SWITCH_KILL_CURRENT = 2;
    public static int BUTTON_SPEED_SWITCH_KILL_ALL = 3;
    public static int BUTTON_SPEED_SWITCH_KILL_OTHER = 4;
    public static int BUTTON_SPEED_SWITCH_LOCK_APP = 5;
    public static int BUTTON_SPEED_SWITCH_TOGGLE_APP = 6;
    public static int BUTTON_SPEED_SWITCH_MENU = 7;

    private ListPreference mIconSize;
    private SeekBarPreference mOpacity;
    private Preference mFavoriteAppsConfig;
    private Preference mAdjustHandle;
    private SharedPreferences mPrefs;
    private SettingsGestureView mGestureView;
    private Preference mButtonConfig;
    private String[] mButtonEntries;
    private Drawable[] mButtonImages;
    private String mButtons;
    private Preference mIconpack;
    private Switch mToggleServiceSwitch;
    private SharedPreferences.OnSharedPreferenceChangeListener mPrefsListener;
    private Preference mSpeedSwitchButtonConfig;
    private String[] mSpeedSwitchButtonEntries;
    private Drawable[] mSpeedSwitchButtonImages;
    private String mSpeedSwitchButtons;
    private NumberPickerPreference mSpeedSwitchItems;
    private ListPreference mButtonPos;
    private ListPreference mBgStyle;
    private ListPreference mLayoutStyle;
    private ListPreference mAppFilterTime;
    private ListPreference mThumbSize;
    private SwitchPreference mEnable;
    private SwitchPreference mLauncherMode;
    private CheckBoxPreference mRevertRecents;
    private CheckBoxPreference mThumbLoading;

    @Override
    public void onPause() {
        if (mGestureView != null) {
            mGestureView.hide();
            mGestureView = null;
        }
        mPrefs.unregisterOnSharedPreferenceChangeListener(mPrefsListener);
        super.onPause();
    }

    @Override
    public void onResume() {
        mPrefs.registerOnSharedPreferenceChangeListener(mPrefsListener);
        mEnable.setChecked(SwitchService.isRunning() && mPrefs.getBoolean(SettingsActivity.PREF_ENABLE, false));
        super.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPrefs = PreferenceManager.getDefaultSharedPreferences(this);

        addPreferencesFromResource(R.xml.recents_settings);

        mEnable = (SwitchPreference) findPreference(PREF_ENABLE);
        mEnable.setChecked(SwitchService.isRunning() && mPrefs.getBoolean(SettingsActivity.PREF_ENABLE, false));
        mEnable.setOnPreferenceChangeListener(this);
        mIconSize = (ListPreference) findPreference(PREF_ICON_SIZE);
        mIconSize.setOnPreferenceChangeListener(this);
        int idx = mIconSize.findIndexOfValue(mPrefs.getString(PREF_ICON_SIZE,
                mIconSize.getEntryValues()[1].toString()));
        mIconSize.setValueIndex(idx);
        mIconSize.setSummary(mIconSize.getEntries()[idx]);
        mOpacity = (SeekBarPreference) findPreference(PREF_OPACITY);
        mOpacity.setInitValue(mPrefs.getInt(PREF_OPACITY, 70));
        mOpacity.setOnPreferenceChangeListener(this);
        mAdjustHandle = (Preference) findPreference(PREF_ADJUST_HANDLE);
        mButtonConfig = (Preference) findPreference(PREF_BUTTON_CONFIG);
        mButtons = mPrefs.getString(PREF_BUTTONS_NEW, PREF_BUTTON_DEFAULT_NEW);
        mFavoriteAppsConfig = (Preference) findPreference(PREF_FAVORITE_APPS_CONFIG);
        mIconpack = (Preference) findPreference(PREF_ICONPACK);
        mSpeedSwitchItems = (NumberPickerPreference) findPreference(PREF_SPEED_SWITCHER_ITEMS);
        mSpeedSwitchItems.setMinValue(8);
        mSpeedSwitchItems.setMaxValue(20);
        mSpeedSwitchButtonConfig = (Preference) findPreference(PREF_SPEED_SWITCHER_BUTTON_CONFIG);
        mSpeedSwitchButtons = mPrefs.getString(PREF_SPEED_SWITCHER_BUTTON_NEW, PREF_SPEED_SWITCHER_BUTTON_DEFAULT_NEW);

        mButtonPos = (ListPreference) findPreference(PREF_BUTTON_POS);
        mButtonPos.setOnPreferenceChangeListener(this);
        idx = mButtonPos.findIndexOfValue(mPrefs.getString(PREF_BUTTON_POS,
                mButtonPos.getEntryValues()[0].toString()));
        mButtonPos.setValueIndex(idx);
        mButtonPos.setSummary(mButtonPos.getEntries()[idx]);

        initButtons();

        mBgStyle = (ListPreference) findPreference(PREF_BG_STYLE);
        mBgStyle.setOnPreferenceChangeListener(this);
        idx = mBgStyle.findIndexOfValue(mPrefs.getString(PREF_BG_STYLE,
                mBgStyle.getEntryValues()[0].toString()));
        mBgStyle.setValueIndex(idx);
        mBgStyle.setSummary(mBgStyle.getEntries()[idx]);

        mLayoutStyle = (ListPreference) findPreference(PREF_LAYOUT_STYLE);
        mLayoutStyle.setOnPreferenceChangeListener(this);
        idx = mLayoutStyle.findIndexOfValue(mPrefs.getString(PREF_LAYOUT_STYLE,
                mLayoutStyle.getEntryValues()[0].toString()));
        mLayoutStyle.setValueIndex(idx);
        mLayoutStyle.setSummary(mLayoutStyle.getEntries()[idx]);

        mAppFilterTime = (ListPreference) findPreference(PREF_APP_FILTER_TIME);
        mAppFilterTime.setOnPreferenceChangeListener(this);
        idx = mAppFilterTime.findIndexOfValue(mPrefs.getString(PREF_APP_FILTER_TIME,
                mAppFilterTime.getEntryValues()[0].toString()));
        mAppFilterTime.setValueIndex(idx);
        mAppFilterTime.setSummary(mAppFilterTime.getEntries()[idx]);

        mThumbSize = (ListPreference) findPreference(PREF_THUMB_SIZE);
        mThumbSize.setOnPreferenceChangeListener(this);
        idx = mThumbSize.findIndexOfValue(mPrefs.getString(PREF_THUMB_SIZE,
                mThumbSize.getEntryValues()[2].toString()));
        mThumbSize.setValueIndex(idx);
        mThumbSize.setSummary(mThumbSize.getEntries()[idx]);
        mThumbLoading = (CheckBoxPreference) findPreference(PREF_SWIPE_THUMB_UPDATE);
        mLauncherMode = (SwitchPreference) findPreference(PREF_LAUNCHER_MODE);
        mRevertRecents = (CheckBoxPreference) findPreference(PREF_REVERT_RECENTS);

        boolean vertical = mLayoutStyle.getValue().equals("1");
        mRevertRecents.setEnabled(vertical);
        mThumbSize.setEnabled(vertical);
        mThumbLoading.setEnabled(vertical);

        mPrefsListener = new SharedPreferences.OnSharedPreferenceChangeListener() {
            public void onSharedPreferenceChanged(SharedPreferences prefs,
                    String key) {
                updatePrefs(prefs, key);
            }
        };

        updatePrefs(mPrefs, null);
    }

    private class ButtonsApplyRunnable implements CheckboxListDialog.ApplyRunnable {
        public void apply(Map<Integer, Boolean> buttons) {
            mButtons = Utils.buttonMapToString(buttons);
            mPrefs.edit().putString(PREF_BUTTONS_NEW, mButtons).commit();
        }
    }

    private class SpeedSwitchButtonsApplyRunnable implements CheckboxListDialog.ApplyRunnable {
        public void apply(Map<Integer, Boolean> buttons) {
            mSpeedSwitchButtons = Utils.buttonMapToString(buttons);
            mPrefs.edit().putString(PREF_SPEED_SWITCHER_BUTTON_NEW, mSpeedSwitchButtons).commit();
        }
    }

    @Override
    public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen,
            Preference preference) {
        if (preference == mAdjustHandle) {
            if (mGestureView != null) {
                mGestureView.hide();
                mGestureView = null;
            }
            mGestureView = new SettingsGestureView(this);
            mGestureView.show();
            return true;
        } else if (preference == mButtonConfig){
            Map<Integer, Boolean> buttons = Utils.buttonStringToMap(mButtons, PREF_BUTTON_DEFAULT_NEW);
            CheckboxListDialog dialog = new CheckboxListDialog(this,
                    mButtonEntries, mButtonImages, buttons, new ButtonsApplyRunnable(),
                    getResources().getString(R.string.buttons_title));
            dialog.show();
            return true;
        } else if (preference == mSpeedSwitchButtonConfig){
            Map<Integer, Boolean> buttons = Utils.buttonStringToMap(mSpeedSwitchButtons, PREF_SPEED_SWITCHER_BUTTON_DEFAULT_NEW);
            CheckboxListDialog dialog = new CheckboxListDialog(this,
                    mSpeedSwitchButtonEntries, mSpeedSwitchButtonImages, buttons, new SpeedSwitchButtonsApplyRunnable(),
                    getResources().getString(R.string.buttons_title));
            dialog.show();
            return true;
        } else if (preference == mIconpack){
            IconPackHelper.getInstance(SettingsActivity.this).pickIconPack(this);
            return true;
        } else if (preference == mFavoriteAppsConfig) {
            String favoriteListString = mPrefs.getString(PREF_FAVORITE_APPS, "");
            List<String> favoriteList = new ArrayList<String>();
            Utils.parseFavorites(favoriteListString, favoriteList);
            doShowFavoritesList(favoriteList);
            return true;
        } else if (preference == mLauncherMode){
            Utils.enableLauncherMode(this, mLauncherMode.isChecked());
            return true;
        }
        return false;
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        if (preference == mIconSize) {
            String value = (String) newValue;
            int idx = mIconSize.findIndexOfValue(value);
            mIconSize.setSummary(mIconSize.getEntries()[idx]);
            mIconSize.setValueIndex(idx);
            return true;
        } else if (preference == mOpacity) {
            float val = Float.parseFloat((String) newValue);
            mPrefs.edit().putInt(PREF_OPACITY, (int) val).commit();
            return true;
        } else if (preference == mButtonPos) {
            String value = (String) newValue;
            int idx = mButtonPos.findIndexOfValue(value);
            mButtonPos.setSummary(mButtonPos.getEntries()[idx]);
            mButtonPos.setValueIndex(idx);
            return true;
        } else if (preference == mBgStyle) {
            String value = (String) newValue;
            int idx = mBgStyle.findIndexOfValue(value);
            mBgStyle.setSummary(mBgStyle.getEntries()[idx]);
            mBgStyle.setValueIndex(idx);
            return true;
        } else if (preference == mLayoutStyle) {
            String value = (String) newValue;
            int idx = mLayoutStyle.findIndexOfValue(value);
            mLayoutStyle.setSummary(mLayoutStyle.getEntries()[idx]);
            mLayoutStyle.setValueIndex(idx);
            boolean vertical = mLayoutStyle.getValue().equals("1");
            mRevertRecents.setEnabled(vertical);
            mThumbSize.setEnabled(vertical);
            mThumbLoading.setEnabled(vertical);
            return true;
        } else if (preference == mAppFilterTime) {
            String value = (String) newValue;
            int idx = mAppFilterTime.findIndexOfValue(value);
            mAppFilterTime.setSummary(mAppFilterTime.getEntries()[idx]);
            mAppFilterTime.setValueIndex(idx);
            return true;
        } else if (preference == mThumbSize) {
            String value = (String) newValue;
            int idx = mThumbSize.findIndexOfValue(value);
            mThumbSize.setSummary(mThumbSize.getEntries()[idx]);
            mThumbSize.setValueIndex(idx);
            return true;
        } else if (preference == mEnable) {
            boolean value = ((Boolean) newValue).booleanValue();
            startOmniSwitch(value);
            if (!value && mLauncherMode.isChecked()) {
                Toast.makeText(SettingsActivity.this, R.string.launcher_mode_enable_check, Toast.LENGTH_LONG).show();
            }
            return true;
        }
        return false;
    }

    @Override
    public void applyChanges(List<String> favoriteList){
        mPrefs.edit()
                .putString(PREF_FAVORITE_APPS,
                        Utils.flattenFavorites(favoriteList))
                .commit();
    }

    private void initButtons(){
        mButtonEntries = getResources().getStringArray(R.array.button_entries);
        mButtonImages = new Drawable[mButtonEntries.length];
        mButtonImages[0]=BitmapUtils.colorize(getResources(), Color.GRAY, getResources().getDrawable(R.drawable.kill_all));
        mButtonImages[1]=BitmapUtils.colorize(getResources(), Color.GRAY, getResources().getDrawable(R.drawable.kill_other));
        mButtonImages[2]=BitmapUtils.colorize(getResources(), Color.GRAY, getResources().getDrawable(R.drawable.ic_lastapp));
        mButtonImages[3]=BitmapUtils.colorize(getResources(), Color.GRAY, getResources().getDrawable(R.drawable.ic_sysbar_home));
        mButtonImages[4]=BitmapUtils.colorize(getResources(), Color.GRAY, getResources().getDrawable(R.drawable.ic_settings_small));
        mButtonImages[5]=BitmapUtils.colorize(getResources(), Color.GRAY, getResources().getDrawable(R.drawable.ic_apps));
        mButtonImages[6]=BitmapUtils.colorize(getResources(), Color.GRAY, getResources().getDrawable(R.drawable.ic_sysbar_back));
        mButtonImages[7]=BitmapUtils.colorize(getResources(), Color.GRAY, getResources().getDrawable(R.drawable.ic_pin));
        mButtonImages[8]=BitmapUtils.colorize(getResources(), Color.GRAY, getResources().getDrawable(R.drawable.ic_close));
        mButtonImages[9]=BitmapUtils.colorize(getResources(), Color.GRAY, getResources().getDrawable(R.drawable.ic_menu));

        mSpeedSwitchButtonEntries = getResources().getStringArray(R.array.speed_switch_button_entries);
        mSpeedSwitchButtonImages = new Drawable[mSpeedSwitchButtonEntries.length];
        mSpeedSwitchButtonImages[0]=BitmapUtils.colorize(getResources(), Color.GRAY, getResources().getDrawable(R.drawable.ic_sysbar_home));
        mSpeedSwitchButtonImages[1]=BitmapUtils.colorize(getResources(), Color.GRAY, getResources().getDrawable(R.drawable.ic_sysbar_back));
        mSpeedSwitchButtonImages[2]=BitmapUtils.colorize(getResources(), Color.GRAY, getResources().getDrawable(R.drawable.kill_current));
        mSpeedSwitchButtonImages[3]=BitmapUtils.colorize(getResources(), Color.GRAY, getResources().getDrawable(R.drawable.kill_all));
        mSpeedSwitchButtonImages[4]=BitmapUtils.colorize(getResources(), Color.GRAY, getResources().getDrawable(R.drawable.kill_other));
        mSpeedSwitchButtonImages[5]=BitmapUtils.colorize(getResources(), Color.GRAY, getResources().getDrawable(R.drawable.ic_pin));
        mSpeedSwitchButtonImages[6]=BitmapUtils.colorize(getResources(), Color.GRAY, getResources().getDrawable(R.drawable.ic_lastapp));
        mSpeedSwitchButtonImages[7]=BitmapUtils.colorize(getResources(), Color.GRAY, getResources().getDrawable(R.drawable.ic_menu));
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // dont restart activity on orientation changes
        if (mGestureView != null && mGestureView.isShowing()){
            mGestureView.handleRotation();
        }
    }

    public void updatePrefs(SharedPreferences prefs, String key) {
        if (!SwitchService.isRunning()){
            IconPackHelper.getInstance(SettingsActivity.this).updatePrefs(mPrefs, null);
        }
    }

    private void startOmniSwitch(boolean value) {
        Intent svc = new Intent(SettingsActivity.this, SwitchService.class);
        if (value) {
            if (SwitchService.isRunning()){
                stopService(svc);
            }
            startService(svc);
        } else {
            if (SwitchService.isRunning()){
                stopService(svc);
            }
        }
    }

    private void doShowFavoritesList(List<String> favoriteList) {
        FavoriteDialog dialog = new FavoriteDialog(this, this, favoriteList);
        dialog.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        menu.findItem(R.id.hide_app).setChecked(!isAppIconEnabled());
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.hide_app:
                boolean checked = item.isChecked();
                item.setChecked(!checked);
                setAppIconEnabled(checked);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void setAppIconEnabled(boolean enabled) {
        int newState;
        PackageManager pm = getPackageManager();
        if (enabled) {
            newState = PackageManager.COMPONENT_ENABLED_STATE_ENABLED;
        } else {
            newState = PackageManager.COMPONENT_ENABLED_STATE_DISABLED;
        }
        pm.setComponentEnabledSetting(new ComponentName(this, org.omnirom.omniswitch.LauncherActivity.class), newState, PackageManager.DONT_KILL_APP);
    }

    public boolean isAppIconEnabled() {
        PackageManager pm = getPackageManager();
        return (pm.getComponentEnabledSetting(new ComponentName(this, org.omnirom.omniswitch.LauncherActivity.class)) != PackageManager.COMPONENT_ENABLED_STATE_DISABLED);
    }
}
