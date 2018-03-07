package com.cuipengyu.msearchview;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements TextWatcher, TextView.OnEditorActionListener, View.OnClickListener {
    private ImageView iv_exma_clear;
    private RelativeLayout rl_exma_searchview_pressed, rl_exma_searchview_normal;//两种显示和隐藏布局
    private TextView searchview_text_pressed;//确认或者取消
    private EditText et_exma_searchview;
    private ImageView meun_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rl_exma_searchview_pressed = (RelativeLayout) findViewById(R.id.rl_exma_searchview_pressed);
        rl_exma_searchview_normal = (RelativeLayout) findViewById(R.id.rl_exma_searchview_normal);
        et_exma_searchview = (EditText) findViewById(R.id.et_exma_searchview);
        searchview_text_pressed = (TextView) findViewById(R.id.searchview_text_pressed);
        iv_exma_clear = (ImageView) findViewById(R.id.iv_exma_clear);
        meun_list = (ImageView) findViewById(R.id.meun_list);

        iv_exma_clear.setOnClickListener(this);
        et_exma_searchview.addTextChangedListener(this);
        et_exma_searchview.setOnClickListener(this);
        et_exma_searchview.setOnEditorActionListener(this);
        searchview_text_pressed.setOnClickListener(this);
        rl_exma_searchview_normal.setOnClickListener(this);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        Log.e("beforeTextChanged", s.toString());

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        Log.e("onTextChanged", s.toString());
        Toast.makeText(this, "实时更新操作---"+s.toString(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void afterTextChanged(Editable s) {
        /**获取输入文字**/
        String input = et_exma_searchview.getText().toString().trim();
        if (input.isEmpty()) {
            iv_exma_clear.setVisibility(View.GONE);
            et_exma_searchview.setHint("请输入试卷名称");
        } else {
            Log.e("afterTextChanged", s.toString());
            iv_exma_clear.setVisibility(View.VISIBLE);
            et_exma_searchview.setHint("");
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_exma_searchview_normal:
                meun_list.setVisibility(View.GONE);
                rl_exma_searchview_normal.setVisibility(View.GONE);
                rl_exma_searchview_pressed.setVisibility(View.VISIBLE);
                searchview_text_pressed.setVisibility(View.VISIBLE);
                setFocusable();
                break;
            case R.id.searchview_text_pressed:
                et_exma_searchview.setText("");
                clearFocus();
                meun_list.setVisibility(View.VISIBLE);
                searchview_text_pressed.setVisibility(View.GONE);
                rl_exma_searchview_normal.setVisibility(View.VISIBLE);
                rl_exma_searchview_pressed.setVisibility(View.GONE);
                break;
            case R.id.iv_exma_clear:
                et_exma_searchview.setText("");
                break;
        }
    }

    private void setFocusable() {
        et_exma_searchview.setFocusable(true);
        et_exma_searchview.setFocusableInTouchMode(true);
        et_exma_searchview.requestFocus();
        InputMethodManager inputManager = (InputMethodManager) et_exma_searchview.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.showSoftInput(et_exma_searchview, 0);
    }

    private void clearFocus() {
        et_exma_searchview.clearFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(et_exma_searchview.getWindowToken(), 0);
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        switch (actionId) {
            case EditorInfo.IME_ACTION_SEARCH:
                Toast.makeText(this, "更新操作", Toast.LENGTH_SHORT).show();
                break;
        }
        return false;
    }
}
