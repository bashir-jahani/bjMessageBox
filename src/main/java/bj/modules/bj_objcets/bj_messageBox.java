package bj.modules.bj_objcets;


import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.DrawableRes;
import androidx.annotation.StringRes;




public class bj_messageBox extends Dialog {
    TextView TXVMessage,TXVTitle;
    Button BTNOK,BTNCancel,BTNYes,BTNNo;
    ImageView IMGLogo;
    Context mContext;
    static int PRS_Button;

    Boolean mShowOK,  mShowCancel,  mShowYes,  mShowNO;
    public static Boolean result=false;
    public bj_messageBox(Context context, Boolean ShowCancel, Boolean ShowOK, Boolean ShowYes, Boolean ShowNO) {
        super(context);
        mContext=context;
        mShowOK=ShowOK;
        mShowCancel=ShowCancel;
        mShowYes=ShowYes;
        mShowNO=ShowNO;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        result=false;
        setContentView(R.layout.g_message_box);
        TXVTitle=(TextView)findViewById(R.id.GDA_TXV_Title);
        TXVMessage=(TextView)findViewById(R.id.GDA_TXV_Message);
        BTNCancel=(Button)findViewById(R.id.GDA_BTN_Cancel);
        BTNOK=(Button)findViewById(R.id.GDA_BTN_OK );
        BTNYes=(Button)findViewById(R.id.GDA_BTN_Yes);
        BTNNo=(Button)findViewById(R.id.GDA_BTN_NO);
        IMGLogo=(ImageView) findViewById(R.id.GDA_Logo);

        IMGLogo.setVisibility(View.GONE);

        if(mShowOK){
            BTNOK.setVisibility(View.VISIBLE);
            BTNOK.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    result=true;
                    PRS_Button= bj.modules.bj_messageBox.BJMessagesButtonKind.PressedButton.Button_Ok;
                    dismiss();

                }
            });
        }else {BTNOK.setVisibility(View.GONE);}
        if(mShowCancel){
            BTNCancel.setVisibility(View.VISIBLE);
            BTNCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    result=false;
                    PRS_Button= bj.modules.bj_messageBox.BJMessagesButtonKind.PressedButton.Button_Cancel;
                    dismiss();

                }
            });

        }else {BTNCancel.setVisibility(View.GONE);}
        if(mShowYes){
            BTNYes.setVisibility(View.VISIBLE);
            BTNYes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    result=true;
                    PRS_Button= bj.modules.bj_messageBox.BJMessagesButtonKind.PressedButton.Button_Yes;
                    dismiss();

                }
            });
        }else {BTNYes.setVisibility(View.GONE);}
        if(mShowNO){
            BTNNo.setVisibility(View.VISIBLE);
            BTNNo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    result=false;
                    PRS_Button= bj.modules.bj_messageBox.BJMessagesButtonKind.PressedButton.Button_No;
                    dismiss();

                }
            });

        }else {
            BTNNo.setVisibility(View.GONE);
        }



    }

    @Override
    public void setTitle( CharSequence title) {
        super.setTitle(title);
        TXVTitle.setText(title);
    }
    @Override
    public void setTitle(@StringRes int titleID) {
        super.setTitle(titleID);
        TXVTitle.setText(titleID);
    }

    public void setMessage( CharSequence Message) {

        TXVMessage.setText(Message);
    }

    public void SetLogo( @DrawableRes int LogoResourcesID) {
        Log.e("GGN",LogoResourcesID+"");
        IMGLogo.setImageResource(LogoResourcesID);
        IMGLogo.setVisibility(View.VISIBLE);
    }

    public void setMessage(@StringRes int MessageID) {
        TXVMessage.setText(MessageID);
    }

    public void setOnDialogResultListener( OnDialogResultListener listener) {
        super.setOnDismissListener(listener);

    }

    public static class    OnDialogResultListener implements OnDismissListener {

        public boolean OnResult(Boolean dialogResult) {

            return false;
        }
        public boolean OnResult(@bj.modules.bj_messageBox.BJMessagesButtonKind.PressedButton int  PressedButton) {

            return false;
        }
        @Override
        public void onDismiss(DialogInterface dialog) {

            OnResult(result);
            OnResult(PRS_Button);
        }


    }


}

