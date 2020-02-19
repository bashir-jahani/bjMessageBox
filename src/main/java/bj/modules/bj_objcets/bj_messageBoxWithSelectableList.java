package bj.modules.bj_objcets;


import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;

import java.util.ArrayList;

import bj.modules.bj_messageBox_classes;
import bj.modules.bj_messageBox;


public class bj_messageBoxWithSelectableList extends Dialog {
    TextView TXVMessage,TXVTitle;
    ListView LVMyList;
    Button BTNOK,BTNCancel;
    ImageView IMGLogo;
    Context mContext;

    static int PRS_Button;

    static ArrayList<bj_messageBox_classes.BJSimpleListViewInfo> SelectedItems=new ArrayList<bj_messageBox_classes.BJSimpleListViewInfo>();
    Boolean EnableMultiSellect;
    public static Boolean result=false;
    public bj_messageBoxWithSelectableList(Context context, Boolean EnableMultiSellect) {
        super(context);
        mContext=context;
        this.EnableMultiSellect=EnableMultiSellect;


    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        result=false;
        setContentView(R.layout.g_message_box_with_selectable_list);
        TXVTitle=(TextView)findViewById(R.id.GDA_TXV_Title);
        TXVMessage=(TextView)findViewById(R.id.GDA_TXV_Message);
        BTNCancel=(Button)findViewById(R.id.GDA_BTN_Cancel);
        BTNOK=(Button)findViewById(R.id.GDA_BTN_OK );
        LVMyList=(ListView) findViewById(R.id.GDA_LV_List);

        IMGLogo=(ImageView) findViewById(R.id.GDA_Logo);

        IMGLogo.setVisibility(View.GONE);

        BTNOK.setVisibility(View.VISIBLE);
        BTNOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                result=true;
                PRS_Button= bj_messageBox.BJMessagesButtonKind.PressedButton.Button_Ok;
                dismiss();

            }
        });
        BTNCancel.setVisibility(View.VISIBLE);
        BTNCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                result=false;
                PRS_Button= bj_messageBox.BJMessagesButtonKind.PressedButton.Button_Cancel;
                dismiss();

            }
        });
       ;


    }


    ArrayList<bj_messageBox_classes.BJSimpleListViewInfo> MessageBoxListViewItems = new ArrayList<bj_messageBox_classes.BJSimpleListViewInfo>();
    public void SetMessageBoxListViewItems( ArrayList<bj_messageBox_classes.BJSimpleListViewInfo> Items) {
        MessageBoxListViewItems=Items;
        LoadMessageBoxListViewItems();
    }

    private void LoadMessageBoxListViewItems(){


         ArrayAdapter adapter = new ArrayAdapter (mContext, R.layout.g_listview_item,  MessageBoxListViewItems) {
            @NonNull
            @Override
            public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view;
                view=((Activity) mContext).getLayoutInflater().inflate(R.layout.g_listview_item,null);
                LinearLayout linearLayout=(LinearLayout)view.findViewById(R.id.GLV_LL_Content);
                TextView textView1 = (TextView) view.findViewById(R.id.GLV_TXV_Title);
                TextView textView2 = (TextView) view.findViewById(R.id.GLV_TXV_SubTitle);
                ImageView imageView=(ImageView) view.findViewById(R.id.GLV_IMG_Tick);

                textView1.setText(MessageBoxListViewItems.get(position).Title);
                if(MessageBoxListViewItems.get(position).Sellected) {
                    imageView.setVisibility(View.VISIBLE);
                    linearLayout.setBackground(mContext.getDrawable(R.drawable.border_corner5));
                }else {
                    imageView.setVisibility(View.INVISIBLE);
                }

                Log.d("asd",position+": "+MessageBoxListViewItems.get(position).SubTitle+"/"+MessageBoxListViewItems.get(position).Sellected);
                textView2.setText(MessageBoxListViewItems.get(position).SubTitle);
                if (MessageBoxListViewItems.get(position).Sellected){
                    SelectedItems.add(MessageBoxListViewItems.get(position));

                }
                View.OnClickListener onClickListener=new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        MessageBoxListViewItems.get(position).Sellected = true;
                        if (!EnableMultiSellect) {
                            for (int i = 0; i < MessageBoxListViewItems.size(); i++) {
                                if (i != position) {
                                    MessageBoxListViewItems.get(i).Sellected = false;
                                }
                            }
                        }
                        SelectedItems = new ArrayList<bj_messageBox_classes.BJSimpleListViewInfo>();
                        ((ArrayAdapter) LVMyList.getAdapter()).notifyDataSetChanged();
                        Log.d("asd","Notify");
                    }
                };
                imageView.setOnClickListener(onClickListener);
                textView1.setOnClickListener(onClickListener);
                textView2.setOnClickListener(onClickListener);

                return view;
            }
        };

        LVMyList.setAdapter(adapter);



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

    public void setOnDialogResultListener( OnDialogWithSelectableListResultListener listener) {
        super.setOnDismissListener(listener);

    }

    public static class    OnDialogWithSelectableListResultListener implements OnDismissListener {



        public boolean OnResult(Boolean dialogResult, ArrayList<bj_messageBox_classes.BJSimpleListViewInfo> SelectedItems) {

            return dialogResult;
        }

        @Override
        public void onDismiss(DialogInterface dialog) {

            OnResult(result,SelectedItems);

        }


    }


}

