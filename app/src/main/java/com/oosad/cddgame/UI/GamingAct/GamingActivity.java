package com.oosad.cddgame.UI.GamingAct;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.oosad.cddgame.R;
import com.oosad.cddgame.UI.GamingAct.presenter.GamingPresenterCompl;
import com.oosad.cddgame.UI.GamingAct.presenter.IGamingPresenter;
import com.oosad.cddgame.UI.GamingAct.util.CardUtil;
import com.oosad.cddgame.UI.GamingAct.view.CardLayout;
import com.oosad.cddgame.UI.GamingAct.view.CascadeLayout;
import com.oosad.cddgame.UI.GamingAct.view.IGamingView;

public class GamingActivity extends AppCompatActivity implements IGamingView, View.OnClickListener {

    IGamingPresenter m_gamingPresenter;
    TextView m_UserNameDownTextView;
    TextView m_UserNameLeftTextView;
    TextView m_UserNameRightTextView;
    TextView m_UserNameUpTextView;
    Button m_PauseGameButton;
    Button m_ExitGameButton;
    Button m_PushOrDistributeCardButton;
    CascadeLayout m_CardSetLayout;
    CascadeLayout m_ShowCardSetDownLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gaming);
        getSupportActionBar().hide();

        m_gamingPresenter = new GamingPresenterCompl(this);

        setupView();
        m_gamingPresenter.Handle_SetupBundle(getIntent());
    }

    private void setupView() {
        m_UserNameDownTextView = findViewById(R.id.id_GamingAct_UserNameDownTextView);
        m_UserNameLeftTextView = findViewById(R.id.id_GamingAct_UserNameLeftTextView);
        m_UserNameRightTextView = findViewById(R.id.id_GamingAct_UserNameRightTextView);
        m_UserNameUpTextView = findViewById(R.id.id_GamingAct_UserNameUpTextView);

        m_PauseGameButton = findViewById(R.id.id_GamingAct_PauseGameButton);
        m_ExitGameButton = findViewById(R.id.id_GamingAct_ExitGameButton);
        m_PushOrDistributeCardButton = findViewById(R.id.id_GamingAct_PushOrDistributeCardButton);

        m_PauseGameButton.setOnClickListener(this);
        m_ExitGameButton.setOnClickListener(this);
        m_PushOrDistributeCardButton.setOnClickListener(this);
        m_PushOrDistributeCardButton.setText(R.string.str_GamingAct_DistributeCardButton);

        m_CardSetLayout = findViewById(R.id.id_GamingAct_CardSetCascadeLayout);
        m_ShowCardSetDownLayout = findViewById(R.id.id_GamingAct_ShowCardSetDownCascadeLayout);
    }

    private void ShowLogE(String FunctionName, String data) {
        String TAG = "CDDGame";
        String CN = "GamingActivity";
        String msg = CN + "###" + FunctionName + "(): " + data;
        Log.e(TAG, msg);
    }

    @Override
    public GamingActivity getThisPtr() {
        return this;
    }

    /**
     * 设置用户界面
     * @param UserName
     */
    @Override
    public void onSetupUI(String UserName) {
        this.m_UserNameDownTextView.setText(UserName);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.id_GamingAct_PauseGameButton: // 暂停游戏
                PauseGameButton_Click();
            break;
            case R.id.id_GamingAct_ExitGameButton: // 退出游戏
                ExitGameButton_Click();
            break;
            case R.id.id_GamingAct_PushOrDistributeCardButton: // 出发牌
                PushOrDistributeCardButton_Click();
            break;
        }
    }

    /**
     * 单击退出游戏按钮，并判断
     */
    private void ExitGameButton_Click() {

        new AlertDialog.Builder(this)
                .setTitle(R.string.alert_title)
                .setMessage(R.string.str_GamingAct_ShowConfirmExitGameAlertMessage)
                .setPositiveButton(getString(R.string.str_GamingAct_ShowConfirmExitGameAlertPosButtonForExit), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        onBackToMainActivity();
                    }
                })
                .setNegativeButton(getString(R.string.str_GamingAct_ShowConfirmExitGameAlertNegButtonForCancel), null)
                .show();
    }

    /**
     * 单击暂停游戏按钮
     */
    private void PauseGameButton_Click() {
        m_gamingPresenter.Handle_PauseGameButton_Click();
    }

    /**
     * 分牌 出牌 重要
     */
    private void PushOrDistributeCardButton_Click() {
        if (getString(R.string.str_GamingAct_DistributeCardButton).equals(m_PushOrDistributeCardButton.getText().toString())) {
            m_gamingPresenter.Handle_DistributeCard();
            m_PushOrDistributeCardButton.setText(R.string.str_GamingAct_PushCardButton);
        }
        else {
            m_gamingPresenter.Handle_PushCard(m_CardSetLayout.getAllCards());
        }
    }

    /**
     * 用户退出游戏并经确认，返回主界面
     */
    @Override
    public void onBackToMainActivity() {
        finish();
    }

    /**
     * 添加到 主CardSet 内，扑克牌可选
     * @param cardLayout
     */
    @Override
    public void onAddCardLayout(View cardLayout) {
        m_CardSetLayout.addView(cardLayout);
    }

    /**
     * 刷新 CardSet 布局
     */
    @Override
    public void onRefreshCardLayout() {
        m_CardSetLayout.refreshLayout();
    }

    @Override
    public void onShowWinAlert() {
        new AlertDialog.Builder(this)
                .setTitle(R.string.alert_title)
                .setMessage("赢了")
                .setPositiveButton("确定", null)
                .show();
    }

    /**
     * 出牌，从 主CardSet 内删除，并添加到 出牌处，不可选
     * @param cardLayouts
     */
    @Override
    public void onShowCardSet(CardLayout[] cardLayouts) {
        // 有出牌
        if (cardLayouts.length != 0) {
            CardLayout[] showcardLayouts = new CardLayout[cardLayouts.length];
            int idx = 0;
            for (CardLayout c : cardLayouts) {
                if (c != null) {
                    m_CardSetLayout.removeView(c); // 出牌，从自己拥有的牌删除
                    onRefreshCardLayout();
                    c = CardUtil.getCardLayoutFromCard(this, c.getCard(), true); // 从拥有的牌转化成展示的牌
                    showcardLayouts[idx++] = c; // 记录进 showcardLayouts
                }
            }
            // 添加 ShowCardSetDownLayout
            m_ShowCardSetDownLayout.removeAllViews();
            for (CardLayout c : showcardLayouts) {
                if (c != null)
                    m_ShowCardSetDownLayout.addView(c);
            }
        }
        // 拥有的牌为空
        if (m_CardSetLayout.getAllCards().length == 0)
            onShowWinAlert();
    }
}
