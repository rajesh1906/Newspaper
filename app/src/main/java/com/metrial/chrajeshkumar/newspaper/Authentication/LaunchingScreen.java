package com.metrial.chrajeshkumar.newspaper.Authentication;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.github.rahatarmanahmed.cpv.CircularProgressView;
import com.metrial.chrajeshkumar.newspaper.DashBoardActivity;
import com.metrial.chrajeshkumar.newspaper.Database.Database;
import com.metrial.chrajeshkumar.newspaper.Helper.Appcontants;
import com.metrial.chrajeshkumar.newspaper.Helper.Edittext_focus;
import com.metrial.chrajeshkumar.newspaper.Helper.HandlingViews;
import com.metrial.chrajeshkumar.newspaper.R;
import com.metrial.chrajeshkumar.newspaper.Utils.CheckNetwork;
import com.metrial.chrajeshkumar.newspaper.Utils.ConStants;
import com.metrial.chrajeshkumar.newspaper.Utils.Control;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ChRajeshKumar on 9/19/2016.
 */
public class LaunchingScreen extends AppCompatActivity implements Edittext_focus, HandlingViews {
    @Bind(R.id.edttxt_name)
    EditText edittext_name;

    @Bind(R.id.edttxt_email)
    EditText edittext_email;

    @Bind(R.id.edttxt_password)
    EditText edttxt_password;

    @Bind(R.id.progress_view)
    CircularProgressView progressBar;

    @Bind(R.id.progress_view_sign)
    CircularProgressView progress_view_sign;

    @Bind(R.id.btn_ctg_continue)
    Button btn_ctg_continue;

    @Bind(R.id.rel_signin)
    RelativeLayout rel_signin;

    @Bind(R.id.rel_sigup)
    RelativeLayout rel_sigup;

    @Bind(R.id.edttxt_sigin_email)
    EditText edttxt_sigin_email;

    @Bind(R.id.edttxt_signin_pwd)
    EditText edttxt_signin_pwd;

    @Bind(R.id.btn_signin)
    Button btn_signin;

    private Database database;


    //other class variables
    Handler handler;
    int error;
    Animation anim_slide_in_left, side_out, fade_in;
    private final static int SIGNUP = 1, SIGNIN = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);
        ButterKnife.bind(this);
        database = new Database(this);

        anim_slide_in_left = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.anim_slide_in_left);
        side_out = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.anim_slide_out_left);
        fade_in = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.fade_in);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                rel_signin.setVisibility(View.VISIBLE);
                rel_signin.setAnimation(anim_slide_in_left);
            }
        }, 300);

        //apply text watcher
        edittext_name.addTextChangedListener(new CustomWatcher(edittext_name));
        edttxt_password.addTextChangedListener(new CustomWatcher(edttxt_password));
        edittext_email.addTextChangedListener(new CustomWatcher(edittext_email));
        edttxt_sigin_email.addTextChangedListener(new CustomWatcher(edttxt_sigin_email));
        edttxt_signin_pwd.addTextChangedListener(new CustomWatcher(edttxt_signin_pwd));


    }

    @OnClick(R.id.txt_sign_up_now)
    public void signUp_control() {
        rel_signin.setVisibility(View.GONE);
        rel_sigup.setVisibility(View.VISIBLE);
        rel_signin.setAnimation(side_out);

        rel_sigup.setAnimation(fade_in);
    }

    public int exttextLenght(EditText editText) {
        int lenght = editText.getText().toString().length();
        return lenght;
    }

    @OnClick(R.id.btn_signin)
    public void sinin() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progress_view_sign.setVisibility(View.VISIBLE);
                btn_signin.setVisibility(View.GONE);
            }
        });
        if (checkValidation(SIGNIN)) {
           if(database.getData(edttxt_sigin_email.getText().toString(),edttxt_signin_pwd.getText().toString(),this)) {
//               Toast.makeText(this,"valid",Toast.LENGTH_LONG).show();
               dashBoardControl();
           }else{
               progress_view_sign.setVisibility(View.GONE);
               btn_signin.setVisibility(View.VISIBLE);
               error = Database.error;
               Control.control_flow(ConStants.DIALOG_CONTROL, this);
//               Toast.makeText(this,"In-valid",Toast.LENGTH_LONG).show();
           }

        } else {
            progress_view_sign.setVisibility(View.GONE);
            btn_signin.setVisibility(View.VISIBLE);
        }
    }


    @OnClick(R.id.btn_ctg_continue)
    public void submission() {

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progressBar.setVisibility(View.VISIBLE);
                btn_ctg_continue.setVisibility(View.GONE);
            }
        });

        database.insertContact(edittext_name.getText().toString(), edttxt_password.getText().toString(), edittext_email.getText().toString());
        Log.e("inserted users is ", "<><><" + database.getAllUsers());
        if (checkValidation(SIGNUP)) {
            Control.control_flow(ConStants.KEYBOARD_CONTROL, this);
            progressBar.setVisibility(View.VISIBLE);


            dashBoardControl();

        } else {
            btn_ctg_continue.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);

        }
    }

    public boolean checkValidation(int from) {
        boolean validation = true;
        switch (from) {
            case SIGNIN:

                if (!isValidEmail(edttxt_sigin_email.getText().toString())) {
                    edttxt_sigin_email.requestFocus();
                    edttxt_sigin_email.setError(getString(R.string.error_email));
                    validation = false;
                } else if (exttextLenght(edttxt_signin_pwd) < 3) {
                    edttxt_signin_pwd.requestFocus();
                    edttxt_signin_pwd.setError(getString(R.string.error_password));
                    validation = false;
                }

                break;
            case SIGNUP:
                if (exttextLenght(edittext_name) < 3) {
                    validation = false;
                    edittext_name.requestFocus();
                    edittext_name.setError(getString(R.string.error_name));
                } else if (exttextLenght(edttxt_password) < 3) {
                    validation = false;
                    edttxt_password.requestFocus();
                    edttxt_password.setError(getString(R.string.error_password));
                } else if (exttextLenght(edittext_email) < 4) {
                    validation = false;
                    edittext_email.requestFocus();
                    edittext_email.setError(getString(R.string.error_email));
                }
                break;
        }


        return validation;

    }

    @Override
    public boolean edittext_focus() {

        if (checkValidation(SIGNUP)) return false;
        return true;
    }

    @Override
    public void implementation(String message, int position) {

    }

    @Override
    public void conncetion(String value,String title,String description) {

    }

    @Override
    public int getContantField() {
        return error;
    }


    public class CustomWatcher implements TextWatcher {

        private View view;


        public CustomWatcher(View view) {
            this.view = view;

        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        public void afterTextChanged(Editable editable) {

            switch (view.getId()) {


                case R.id.edttxt_name:
                    if (edittext_name.getText().toString().length() > 3) {
                        edittext_name.setError(null);
                    }
                    break;
                case R.id.edttxt_password:
                    if (edttxt_password.getText().toString().length() > 3) {
                        edttxt_password.setError(null);
                    }
                    break;
                case R.id.edttxt_email:
                    if (edittext_email.getText().toString().length() != 0) {
                        edittext_email.setError(null);
                    }
                    break;
                case R.id.edttxt_sigin_email:
                    if (isValidEmail(edttxt_sigin_email.getText().toString())) {
                        edttxt_sigin_email.setError(null);
                    }
                    break;
                case R.id.edttxt_signin_pwd:
                    if (exttextLenght(edttxt_signin_pwd) > 3) {
                        edttxt_signin_pwd.setError(null);
                    }
                    break;
            }
            Appcontants.setpadding((EditText) view, LaunchingScreen.this);
        }
    }

   public boolean isValidEmail(CharSequence target) {

           return target == null?false: android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
          }

    public void dashBoardControl()
    {
        new Thread() {
            public void run() {
                try {
                    sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                try {
                    Message msg = handler.obtainMessage();
                    handler.sendMessage(msg);
                } catch (NullPointerException ex) {
                    Log.e("Handler Exception :", ex.toString());

                }
            }

            ;
        }.start();

        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                // TODO Auto-generated method stub
                super.handleMessage(msg);

                if (CheckNetwork.isOnline(LaunchingScreen.this)) {


                    progressBar.setVisibility(View.GONE);
                    Intent intent = new Intent(LaunchingScreen.this, DashBoardActivity.class);
                    startActivity(intent);
                    finish();
                    overridePendingTransition(R.anim.left_slide_in, R.anim.left_slide_out);
                } else {
                    Log.e("coming to else condition", "<><>");
                    error = ConStants.NETWORK_CONNECTION_ERROR;
                    Control.control_flow(ConStants.DIALOG_CONTROL, LaunchingScreen.this);
//                       alertDialogForNetwork("Check Your Internet Connection");
                }
            }
        };
    }

}
