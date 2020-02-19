package bj.modules.bj_messageBox_objcets;


import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.DrawableRes;
import androidx.annotation.StringRes;

public class inputBox extends Dialog {
    TextView TXVMessage,TXVTitle;
    EditText EDTValue;
    Button BTNOK,BTNCancel;
    Context mContext;
    ImageView IMGLogo;
    int mInputType;
    public static Boolean DResult=false;
    public static String VResult="";
    public inputBox(Context context, int inputType) {
        super(context);
        mContext=context;
        mInputType=inputType;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        DResult=false;
        VResult="";

        setContentView(R.layout.g_input_box);
        TXVTitle=(TextView)findViewById(R.id.GIB_TXV_Title);
        TXVMessage=(TextView)findViewById(R.id.GIB_TXV_Message);
        EDTValue=(EditText) findViewById(R.id.GIB_EDT_Value);
        BTNCancel=(Button)findViewById(R.id.GIB_BTN_Cancel);
        BTNOK=(Button)findViewById(R.id.GIB_BTN_OK );
        IMGLogo=(ImageView) findViewById(R.id.GIB_Logo);
       EDTValue.setInputType(mInputType);

        //Log.e("GGN",EDTValue.getInputType()+"");
        BTNOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (EDTValue.getShowSoftInputOnFocus()) {
                    DResult=true;
                    VResult=EDTValue.getText().toString();
                    dismiss();
                }else {
                    Toast.makeText(mContext, R.string.message_Incorrect_Format, Toast.LENGTH_SHORT).show();
                }


            }
        });
        BTNCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DResult=false;
                VResult=EDTValue.getText().toString();
                dismiss();

            }
        });

        IMGLogo.setVisibility(View.GONE);


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
    public void SetValue( String DefaultValue) {

        EDTValue.setText(DefaultValue);
    }
    public void SetLogo( @DrawableRes int LogoResourcesID) {

        IMGLogo.setImageResource(LogoResourcesID);
        IMGLogo.setVisibility(View.VISIBLE);
    }


    public void setMessage(@StringRes int MessageID) {
        TXVMessage.setText(MessageID);
    }

    public void setValueHint( CharSequence Hint) {

        EDTValue.setHint(Hint);
    }


    public void setValueHint(@StringRes int HintID) {
        EDTValue.setHint(HintID);
    }

    public void setOnDialogResultListener( OnDialogResultListener listener) {
        super.setOnDismissListener(listener);

    }

    public static class    OnDialogResultListener implements OnDismissListener {

        public boolean OnResult(Boolean dialogResult,String ValueResult) {

            return false;
        }
        @Override
        public void onDismiss(DialogInterface dialog) {

            OnResult(DResult,VResult);
        }


    }


}

