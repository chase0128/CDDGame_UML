package com.oosad.cddgame.UI.MainAct.presenter;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.oosad.cddgame.Data.Setting;
import com.oosad.cddgame.UI.GamingAct.GamingActivity;
import com.oosad.cddgame.UI.GamingAct.presenter.GamingPresenterCompl;
import com.oosad.cddgame.UI.MainAct.view.IMainView;
import com.oosad.cddgame.UI.SettingAct.SettingActivity;
import com.oosad.cddgame.UI.SettingAct.presenter.SettingPresenterCompl;

import java.util.Set;

public class MainPresenterCompl implements IMainPresenter {

    IMainView m_mainView;

    public MainPresenterCompl(IMainView iMainView) {
        this.m_mainView = iMainView;
    }

    private void ShowLogE(String FunctionName, String data) {
        String TAG = "CDDGame";
        String CN = "MainPresenterCompl";
        String msg = CN + "###" + FunctionName + "(): " + data;
        Log.e(TAG, msg);
    }

    /**
     * 开始游戏，判断是否设置了用户，并启动 GamingActivity
     */
    @Override
    public void Handle_StartGameButton_Click() {
        if (!hasUser()) {
            m_mainView.ShowNoneUserAlert();
            return;
        }
        ShowLogE("Handle_StartGameButton_Click", "CurrUserName: " + Setting.getInstance().getCurrUser().getName());

        Intent GamingIntent = new Intent(m_mainView.getThisPtr(), GamingActivity.class);
        Bundle GamingBundle = new Bundle();

        Setting setting = Setting.getInstance();
        GamingBundle.putSerializable(GamingPresenterCompl.INT_SETTING_INFO, setting);

        GamingIntent.putExtra(GamingPresenterCompl.INT_BUNDLE_INFO, GamingBundle);
        m_mainView.getThisPtr().startActivity(GamingIntent);
    }

    /**
     * 设置，启动 SettingActivity
     */
    @Override
    public void Handle_SettingButton_Click() {
        Intent SettingIntent = new Intent(m_mainView.getThisPtr(), SettingActivity.class);
        Bundle SettingBundle = new Bundle();

        Setting setting = Setting.getInstance();
        SettingBundle.putSerializable(SettingPresenterCompl.INT_SETTING_INFO, setting);

        SettingIntent.putExtra(SettingPresenterCompl.INT_BUNDLE_INFO, SettingBundle);
        m_mainView.getThisPtr().startActivity(SettingIntent);
    }

    /**
     * 判断是否设置了用户，Handle_StartGameButton_Click() 用
     * @return
     */
    private boolean hasUser() {
        return !Setting.getInstance().getCurrUser().getName().isEmpty();
    }
}
