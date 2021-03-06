package bj.modules;

import android.content.Context;
import android.os.Build;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AlertDialog;

import java.util.ArrayList;

import bj.modules.bj_messageBox_objcets.inputBox;
import bj.modules.bj_messageBox_objcets.messageBoxWithSelectableList;
import bj.modules.bj_messageBox_objcets.R;
import bj.modules.bj_messageBox_objcets.messageBox;

public class bj_messageBox {
	public static @interface BJMessagesButtonKind {
		public static final Integer Ok=1;
		public static final Integer Ok_Cancel=2;
		public static final Integer Yes_No=3;
		public static final Integer Yes_No_Cancel=4;
		public static @interface PressedButton{
			public static final int Button_Ok=1;
			public static final int Button_Cancel=2;
			public static  final int Button_No=3;
			public static final int Button_Yes=4;
		}
	}
	public static class BJAlertDialog extends AlertDialog {
		Context mContext;
		View TView;
		View IView;
		AdapterView.OnItemClickListener mOnItemClickListener;
		BJAlertDialogItem[] mItems;
		public BJAlertDialog(@NonNull Context context, @NonNull String DialogTitle, @DrawableRes int IconResID, @NonNull final BJAlertDialogItem[] items , @NonNull AdapterView.OnItemClickListener onItemClickListener) {
			super(context);
			mContext=context;
			TView= LayoutInflater.from(context).inflate(R.layout.dialog_item, null);
			IView=LayoutInflater.from(context).inflate(R.layout.dialog_alert_layout, null);
			((TextView) TView. findViewById(R.id.tv1)).setText(DialogTitle);
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
				((TextView) TView. findViewById(R.id.tv1)).setTextColor(context.getColor(R.color.colorPrimaryDark));
				TView.setBackgroundColor(context.getColor(R.color.white_overlay));
			}else {
				((TextView) TView. findViewById(R.id.tv1)).setTextColor(context.getResources().getColor(R.color.colorPrimaryDark));
				TView.setBackgroundColor(context.getResources().getColor(R.color.white_overlay));
			}

			((ImageView) TView. findViewById(R.id.iv1)).setImageResource(IconResID);
			setCustomTitle(TView);
			mItems=items;
			mOnItemClickListener=onItemClickListener;

			ListView listView = (ListView) IView.findViewById(R.id.AlertDialog_list_view);
			ArrayAdapter adapter = new ArrayAdapter<BJAlertDialogItem>(context, R.layout.dialog_item, R.id.tv1, items){
				@NonNull
				@Override
				public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
					View v = super.getView(position, convertView, parent);
					TextView tv = (TextView)v.findViewById(R.id.tv1);
					ImageView iv = (ImageView) v.findViewById(R.id.iv1);
					if (items[position].Text != null){
						tv.setText(items[position].Text);
						tv.setVisibility(View.VISIBLE);
					}else {
						tv.setText("321321");
						tv.setVisibility(View.GONE);
					}
					if (items[position].Icon==-1) {
						iv.setVisibility(View.GONE);
						tv.setGravity(Gravity.CENTER);
					}else {
						iv.setImageResource(items[position].Icon);
						iv.setVisibility(View.VISIBLE);
						tv.setGravity(Gravity.RIGHT);
					}

					return v;

				}
			};

			listView.setOnItemClickListener(mOnItemClickListener);
			listView.setAdapter(adapter);
			setView(IView);
		}
		public static class BJAlertDialogItem {

			public final String Text;
			public final int Icon;
			public final int ID;
			public BJAlertDialogItem(@NonNull int id, @NonNull String text, @DrawableRes int IconResID) {
				this.Text = text;
				this.Icon = IconResID;
				ID=id;
			}
			public BJAlertDialogItem(@NonNull int id, @NonNull @DrawableRes int IconResID) {
				this.Text = null;
				this.Icon = IconResID;
				ID=id;
			}
			public BJAlertDialogItem(@NonNull int id, @NonNull String text) {
				this.Text = text;
				this.Icon = -1;
				ID=id;
			}
			@Override
			public String toString() {
				return Text;
			}
		}


		public static void messageBox(String Title, String Message, @BJMessagesButtonKind Integer ButtonsShowKind , Context   context, messageBox.OnDialogResultListener onDialogResultListener ){
			messageBox Mdialog;
			switch (ButtonsShowKind){
				case 1:
					Mdialog= new messageBox(context,false,true,false,false);
					Mdialog.setOnDialogResultListener(onDialogResultListener);



					Mdialog.show();
					Mdialog.setTitle(Title);
					Mdialog.setMessage(Message);
					return;
				case 2:
					Mdialog= new messageBox(context,true,true,false,false);
					Mdialog.setOnDialogResultListener(onDialogResultListener);



					Mdialog.show();
					Mdialog.setTitle(Title);
					Mdialog.setMessage(Message);
					return;
				case 3:
					Mdialog= new messageBox(context,false,false,true,true);
					Mdialog.setOnDialogResultListener(onDialogResultListener);



					Mdialog.show();
					Mdialog.setTitle(Title);
					Mdialog.setMessage(Message);
					return;
				case 4:
					Mdialog= new messageBox(context,true,false,true,true);
					Mdialog.setOnDialogResultListener(onDialogResultListener);



					Mdialog.show();
					Mdialog.setTitle(Title);
					Mdialog.setMessage(Message);
					return;
				default:
					Mdialog= new messageBox(context,false,true,false,false);
					Mdialog.setOnDialogResultListener(onDialogResultListener);



					Mdialog.show();
					Mdialog.setTitle(Title);
					Mdialog.setMessage(Message);
			}



		}
		public static void messageBoxAsError(String ErrMessage,Context context){
			messageBox(context.getString(R.string.Title_Error),ErrMessage, BJMessagesButtonKind.Ok,context,null);
		}
		public static void messageBoxAsAttention(String AttentionMessage,Context context){
			messageBox(context.getString(R.string.Title_Attention),AttentionMessage, BJMessagesButtonKind.Ok,context,null);
		}
		public static void messageBoxWithSelectableListView(String Title, String Message, @DrawableRes int LogoResourcesID, ArrayList<bj_messageBox_classes.BJSimpleListViewInfo> Items, Boolean EnableMultiSellect, Context context, messageBoxWithSelectableList.OnDialogWithSelectableListResultListener onDialogResultListener){
			messageBoxWithSelectableList Mdialog;
			Mdialog= new messageBoxWithSelectableList(context,EnableMultiSellect);
			Mdialog.setOnDialogResultListener(onDialogResultListener);



			Mdialog.show();
			Mdialog.setTitle(Title);
			Mdialog.setMessage(Message);
			Mdialog.SetLogo(LogoResourcesID);
			Mdialog.SetMessageBoxListViewItems(Items);
		}
		public static void messageBoxWithSelectableListView(String Title, String Message, ArrayList<bj_messageBox_classes.BJSimpleListViewInfo> Items, Boolean EnableMultiSellect, Context context, messageBoxWithSelectableList.OnDialogWithSelectableListResultListener onDialogResultListener){
			messageBoxWithSelectableList Mdialog;
			Mdialog= new messageBoxWithSelectableList(context,EnableMultiSellect);
			Mdialog.setOnDialogResultListener(onDialogResultListener);



			Mdialog.show();
			Mdialog.setTitle(Title);
			Mdialog.setMessage(Message);
			Mdialog.SetMessageBoxListViewItems(Items);
		}
		public static void messageBox(@StringRes int TitleId, @StringRes int MessageId, @BJMessagesButtonKind Integer ButtonShowKind, Context context, messageBox.OnDialogResultListener onDialogResultListener ){
			messageBox Mdialog;
			switch (ButtonShowKind){
				case 1:
					Mdialog= new messageBox(context,false,true,false,false);
					Mdialog.setOnDismissListener(onDialogResultListener);



					Mdialog.show();
					Mdialog.setTitle(TitleId);
					Mdialog.setMessage(MessageId);
					return;
				case 2:
					Mdialog= new messageBox(context,true,true,false,false);
					Mdialog.setOnDismissListener(onDialogResultListener);



					Mdialog.show();
					Mdialog.setTitle(TitleId);
					Mdialog.setMessage(MessageId);
					return;
				case 3:
					Mdialog= new messageBox(context,false,false,true,true);
					Mdialog.setOnDismissListener(onDialogResultListener);



					Mdialog.show();
					Mdialog.setTitle(TitleId);
					Mdialog.setMessage(MessageId);
					return;
				case 4:
					Mdialog= new messageBox(context,true,false,true,true);
					Mdialog.setOnDismissListener(onDialogResultListener);



					Mdialog.show();
					Mdialog.setTitle(TitleId);
					Mdialog.setMessage(MessageId);
					return;
				default:
					Mdialog= new messageBox(context,false,true,false,false);
					Mdialog.setOnDismissListener(onDialogResultListener);



					Mdialog.show();
					Mdialog.setTitle(TitleId);
					Mdialog.setMessage(MessageId);
			}


		}

		public static void messageBox(String Title, String Message, @BJMessagesButtonKind Integer ButtonsShowKind , Context context, @DrawableRes int LogoResourcesID, messageBox.OnDialogResultListener onDialogResultListener ){
			messageBox Mdialog;
			switch (ButtonsShowKind){
				case 1:
					Mdialog= new messageBox(context,false,true,false,false);
					Mdialog.setOnDialogResultListener(onDialogResultListener);



					Mdialog.show();
					Mdialog.setTitle(Title);
					Mdialog.setMessage(Message);
					Mdialog.SetLogo(LogoResourcesID);
					return;
				case 2:
					Mdialog= new messageBox(context,true,true,false,false);
					Mdialog.setOnDialogResultListener(onDialogResultListener);



					Mdialog.show();
					Mdialog.setTitle(Title);
					Mdialog.setMessage(Message);
					Mdialog.SetLogo(LogoResourcesID);
					return;
				case 3:
					Mdialog= new messageBox(context,false,false,true,true);
					Mdialog.setOnDialogResultListener(onDialogResultListener);



					Mdialog.show();
					Mdialog.setTitle(Title);
					Mdialog.setMessage(Message);
					Mdialog.SetLogo(LogoResourcesID);
					return;
				case 4:
					Mdialog= new messageBox(context,true,false,true,true);
					Mdialog.setOnDialogResultListener(onDialogResultListener);



					Mdialog.show();
					Mdialog.setTitle(Title);
					Mdialog.setMessage(Message);
					Mdialog.SetLogo(LogoResourcesID);
					return;
				default:
					Mdialog= new messageBox(context,false,true,false,false);
					Mdialog.setOnDialogResultListener(onDialogResultListener);



					Mdialog.show();
					Mdialog.setTitle(Title);
					Mdialog.setMessage(Message);
					Mdialog.SetLogo(LogoResourcesID);
			}



		}
		public static void messageBox(@StringRes int TitleId, @StringRes int MessageId, @BJMessagesButtonKind Integer ButtonShowKind, @DrawableRes int LogoResourcesID, Context context, messageBox.OnDialogResultListener onDialogResultListener ){
			messageBox Mdialog;
			switch (ButtonShowKind){
				case 1:
					Mdialog= new messageBox(context,false,true,false,false);
					Mdialog.setOnDismissListener(onDialogResultListener);



					Mdialog.show();
					Mdialog.setTitle(TitleId);
					Mdialog.setMessage(MessageId);
					Mdialog.SetLogo(LogoResourcesID);
					return;
				case 2:
					Mdialog= new messageBox(context,true,true,false,false);
					Mdialog.setOnDismissListener(onDialogResultListener);



					Mdialog.show();
					Mdialog.setTitle(TitleId);
					Mdialog.setMessage(MessageId);
					Mdialog.SetLogo(LogoResourcesID);
					return;
				case 3:
					Mdialog= new messageBox(context,false,false,true,true);
					Mdialog.setOnDismissListener(onDialogResultListener);



					Mdialog.show();
					Mdialog.setTitle(TitleId);
					Mdialog.setMessage(MessageId);
					Mdialog.SetLogo(LogoResourcesID);
					return;
				case 4:
					Mdialog= new messageBox(context,true,false,true,true);
					Mdialog.setOnDismissListener(onDialogResultListener);



					Mdialog.show();
					Mdialog.setTitle(TitleId);
					Mdialog.setMessage(MessageId);
					Mdialog.SetLogo(LogoResourcesID);
					return;
				default:
					Mdialog= new messageBox(context,false,true,false,false);
					Mdialog.setOnDismissListener(onDialogResultListener);



					Mdialog.show();
					Mdialog.setTitle(TitleId);
					Mdialog.setMessage(MessageId);
					Mdialog.SetLogo(LogoResourcesID);
			}


		}

		public static void inputBox(String Title, String Message, String ValueHint,String DefaultValue , int inputType, Context context, inputBox.OnDialogResultListener onDialogResultListener  ){
			inputBox Mdialog;
			Mdialog= new inputBox(context,inputType);
			Mdialog.setOnDialogResultListener(onDialogResultListener);



			Mdialog.show();
			Mdialog.setTitle(Title);
			Mdialog.setMessage(Message);
			Mdialog.setValueHint(ValueHint);
			Mdialog.SetValue(DefaultValue);
			return;

		}
		public static void inputBox(@StringRes int TitleId, @StringRes int MessageId, @StringRes int ValueHintId,String DefaultValue, int inputType,  Context context, inputBox.OnDialogResultListener onDialogResultListener ){
			inputBox Mdialog;
			Mdialog= new inputBox(context,inputType);
			Mdialog.setOnDialogResultListener(onDialogResultListener);



			Mdialog.show();
			Mdialog.setTitle(TitleId);
			Mdialog.setMessage(MessageId);
			Mdialog.setValueHint(ValueHintId);
			Mdialog.SetValue(DefaultValue);
			return;



		}
		public static void inputBox(String Title, String Message, String ValueHint,String DefaultValue , int inputType,@DrawableRes int LogoResourcesID, Context context, inputBox.OnDialogResultListener onDialogResultListener  ){
			inputBox Mdialog;
			Mdialog= new inputBox(context,inputType);
			Mdialog.setOnDialogResultListener(onDialogResultListener);



			Mdialog.show();
			Mdialog.setTitle(Title);
			Mdialog.setMessage(Message);
			Mdialog.setValueHint(ValueHint);
			Mdialog.SetValue(DefaultValue);
			Mdialog.SetLogo(LogoResourcesID);
			return;

		}
		public static void inputBox(@StringRes int TitleId, @StringRes int MessageId, @StringRes int ValueHintId, String DefaultValue, int inputType, @DrawableRes int LogoResourcesID, Context context, inputBox.OnDialogResultListener onDialogResultListener ){
			inputBox Mdialog;
			Mdialog= new inputBox(context,inputType);
			Mdialog.setOnDialogResultListener(onDialogResultListener);



			Mdialog.show();
			Mdialog.setTitle(TitleId);
			Mdialog.setMessage(MessageId);
			Mdialog.setValueHint(ValueHintId);
			Mdialog.SetValue(DefaultValue);
			Mdialog.SetLogo(LogoResourcesID);
			return;



		}

	}

	public static void MessageBox(String Title, String Message, @BJMessagesButtonKind Integer ButtonsShowKind , Context   context, messageBox.OnDialogResultListener onDialogResultListener ){
		messageBox Mdialog;
		switch (ButtonsShowKind){
			case 1:
				Mdialog= new messageBox(context,false,true,false,false);
				Mdialog.setOnDialogResultListener(onDialogResultListener);



				Mdialog.show();
				Mdialog.setTitle(Title);
				Mdialog.setMessage(Message);
				return;
			case 2:
				Mdialog= new messageBox(context,true,true,false,false);
				Mdialog.setOnDialogResultListener(onDialogResultListener);



				Mdialog.show();
				Mdialog.setTitle(Title);
				Mdialog.setMessage(Message);
				return;
			case 3:
				Mdialog= new messageBox(context,false,false,true,true);
				Mdialog.setOnDialogResultListener(onDialogResultListener);



				Mdialog.show();
				Mdialog.setTitle(Title);
				Mdialog.setMessage(Message);
				return;
			case 4:
				Mdialog= new messageBox(context,true,false,true,true);
				Mdialog.setOnDialogResultListener(onDialogResultListener);



				Mdialog.show();
				Mdialog.setTitle(Title);
				Mdialog.setMessage(Message);
				return;
			default:
				Mdialog= new messageBox(context,false,true,false,false);
				Mdialog.setOnDialogResultListener(onDialogResultListener);



				Mdialog.show();
				Mdialog.setTitle(Title);
				Mdialog.setMessage(Message);
		}



	}
	public static void MessageBoxAsError(String ErrMessage,Context context){
		MessageBox(context.getString(R.string.Title_Error),ErrMessage, BJMessagesButtonKind.Ok,context,null);
	}
	public static void MessageBoxAsAttention(String AttentionMessage,Context context){
		MessageBox(context.getString(R.string.Title_Attention),AttentionMessage, BJMessagesButtonKind.Ok,context,null);
	}

	public static void MessageBoxAsError(String ErrMessage,Context context, messageBox.OnDialogResultListener onDialogResultListener){
		MessageBox(context.getString(R.string.Title_Error),ErrMessage, BJMessagesButtonKind.Ok,context,onDialogResultListener);
	}
	public static void MessageBoxAsAttention(String AttentionMessage,Context context, messageBox.OnDialogResultListener onDialogResultListener){
		MessageBox(context.getString(R.string.Title_Attention),AttentionMessage, BJMessagesButtonKind.Ok,context,onDialogResultListener);
	}

	public static void MessageBoxWithSelectableListView(String Title, String Message, @DrawableRes int LogoResourcesID, ArrayList<bj_messageBox_classes.BJSimpleListViewInfo> Items, Boolean EnableMultiSellect, Context context, messageBoxWithSelectableList.OnDialogWithSelectableListResultListener onDialogResultListener){
		messageBoxWithSelectableList Mdialog;
		Mdialog= new messageBoxWithSelectableList(context,EnableMultiSellect);
		Mdialog.setOnDialogResultListener(onDialogResultListener);



		Mdialog.show();
		Mdialog.setTitle(Title);
		Mdialog.setMessage(Message);
		Mdialog.SetLogo(LogoResourcesID);
		Mdialog.SetMessageBoxListViewItems(Items);
	}
	public static void MessageBoxWithSelectableListView(String Title, String Message, ArrayList<bj_messageBox_classes.BJSimpleListViewInfo> Items, Boolean EnableMultiSellect, Context context, messageBoxWithSelectableList.OnDialogWithSelectableListResultListener onDialogResultListener){
		messageBoxWithSelectableList Mdialog;
		Mdialog= new messageBoxWithSelectableList(context,EnableMultiSellect);
		Mdialog.setOnDialogResultListener(onDialogResultListener);



		Mdialog.show();
		Mdialog.setTitle(Title);
		Mdialog.setMessage(Message);
		Mdialog.SetMessageBoxListViewItems(Items);
	}
	public static void MessageBox(@StringRes int TitleId, @StringRes int MessageId, @BJMessagesButtonKind Integer ButtonShowKind, Context context, messageBox.OnDialogResultListener onDialogResultListener ){
		messageBox Mdialog;
		switch (ButtonShowKind){
			case 1:
				Mdialog= new messageBox(context,false,true,false,false);
				Mdialog.setOnDismissListener(onDialogResultListener);



				Mdialog.show();
				Mdialog.setTitle(TitleId);
				Mdialog.setMessage(MessageId);
				return;
			case 2:
				Mdialog= new messageBox(context,true,true,false,false);
				Mdialog.setOnDismissListener(onDialogResultListener);



				Mdialog.show();
				Mdialog.setTitle(TitleId);
				Mdialog.setMessage(MessageId);
				return;
			case 3:
				Mdialog= new messageBox(context,false,false,true,true);
				Mdialog.setOnDismissListener(onDialogResultListener);



				Mdialog.show();
				Mdialog.setTitle(TitleId);
				Mdialog.setMessage(MessageId);
				return;
			case 4:
				Mdialog= new messageBox(context,true,false,true,true);
				Mdialog.setOnDismissListener(onDialogResultListener);



				Mdialog.show();
				Mdialog.setTitle(TitleId);
				Mdialog.setMessage(MessageId);
				return;
			default:
				Mdialog= new messageBox(context,false,true,false,false);
				Mdialog.setOnDismissListener(onDialogResultListener);



				Mdialog.show();
				Mdialog.setTitle(TitleId);
				Mdialog.setMessage(MessageId);
		}


	}

	public static void MessageBox(String Title, String Message, @BJMessagesButtonKind Integer ButtonsShowKind , Context context, @DrawableRes int LogoResourcesID, messageBox.OnDialogResultListener onDialogResultListener ){
		messageBox Mdialog;
		switch (ButtonsShowKind){
			case 1:
				Mdialog= new messageBox(context,false,true,false,false);
				Mdialog.setOnDialogResultListener(onDialogResultListener);



				Mdialog.show();
				Mdialog.setTitle(Title);
				Mdialog.setMessage(Message);
				Mdialog.SetLogo(LogoResourcesID);
				return;
			case 2:
				Mdialog= new messageBox(context,true,true,false,false);
				Mdialog.setOnDialogResultListener(onDialogResultListener);



				Mdialog.show();
				Mdialog.setTitle(Title);
				Mdialog.setMessage(Message);
				Mdialog.SetLogo(LogoResourcesID);
				return;
			case 3:
				Mdialog= new messageBox(context,false,false,true,true);
				Mdialog.setOnDialogResultListener(onDialogResultListener);



				Mdialog.show();
				Mdialog.setTitle(Title);
				Mdialog.setMessage(Message);
				Mdialog.SetLogo(LogoResourcesID);
				return;
			case 4:
				Mdialog= new messageBox(context,true,false,true,true);
				Mdialog.setOnDialogResultListener(onDialogResultListener);



				Mdialog.show();
				Mdialog.setTitle(Title);
				Mdialog.setMessage(Message);
				Mdialog.SetLogo(LogoResourcesID);
				return;
			default:
				Mdialog= new messageBox(context,false,true,false,false);
				Mdialog.setOnDialogResultListener(onDialogResultListener);



				Mdialog.show();
				Mdialog.setTitle(Title);
				Mdialog.setMessage(Message);
				Mdialog.SetLogo(LogoResourcesID);
		}



	}
	public static void MessageBox(@StringRes int TitleId, @StringRes int MessageId, @BJMessagesButtonKind Integer ButtonShowKind, @DrawableRes int LogoResourcesID, Context context, messageBox.OnDialogResultListener onDialogResultListener ){
		messageBox Mdialog;
		switch (ButtonShowKind){
			case 1:
				Mdialog= new messageBox(context,false,true,false,false);
				Mdialog.setOnDismissListener(onDialogResultListener);



				Mdialog.show();
				Mdialog.setTitle(TitleId);
				Mdialog.setMessage(MessageId);
				Mdialog.SetLogo(LogoResourcesID);
				return;
			case 2:
				Mdialog= new messageBox(context,true,true,false,false);
				Mdialog.setOnDismissListener(onDialogResultListener);



				Mdialog.show();
				Mdialog.setTitle(TitleId);
				Mdialog.setMessage(MessageId);
				Mdialog.SetLogo(LogoResourcesID);
				return;
			case 3:
				Mdialog= new messageBox(context,false,false,true,true);
				Mdialog.setOnDismissListener(onDialogResultListener);



				Mdialog.show();
				Mdialog.setTitle(TitleId);
				Mdialog.setMessage(MessageId);
				Mdialog.SetLogo(LogoResourcesID);
				return;
			case 4:
				Mdialog= new messageBox(context,true,false,true,true);
				Mdialog.setOnDismissListener(onDialogResultListener);



				Mdialog.show();
				Mdialog.setTitle(TitleId);
				Mdialog.setMessage(MessageId);
				Mdialog.SetLogo(LogoResourcesID);
				return;
			default:
				Mdialog= new messageBox(context,false,true,false,false);
				Mdialog.setOnDismissListener(onDialogResultListener);



				Mdialog.show();
				Mdialog.setTitle(TitleId);
				Mdialog.setMessage(MessageId);
				Mdialog.SetLogo(LogoResourcesID);
		}


	}

	public static inputBox InputBox(String Title, String Message, String ValueHint,String DefaultValue , int inputType, Context context, inputBox.OnDialogResultListener onDialogResultListener  ){
		inputBox Mdialog;
		Mdialog= new inputBox(context,inputType);
		Mdialog.setOnDialogResultListener(onDialogResultListener);



		Mdialog.show();
		Mdialog.setTitle(Title);
		Mdialog.setMessage(Message);
		Mdialog.setValueHint(ValueHint);
		Mdialog.SetValue(DefaultValue);
		Window window = Mdialog.getWindow();
		window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
		return Mdialog;

	}
	public static inputBox InputBox(@StringRes int TitleId, @StringRes int MessageId, @StringRes int ValueHintId,String DefaultValue, int inputType,  Context context, inputBox.OnDialogResultListener onDialogResultListener ){
		inputBox Mdialog;
		Mdialog= new inputBox(context,inputType);
		Mdialog.setOnDialogResultListener(onDialogResultListener);



		Mdialog.show();
		Mdialog.setTitle(TitleId);
		Mdialog.setMessage(MessageId);
		Mdialog.setValueHint(ValueHintId);
		Mdialog.SetValue(DefaultValue);
		Window window = Mdialog.getWindow();
		window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
		return Mdialog;



	}
	public static inputBox InputBox(String Title, String Message, String ValueHint,String DefaultValue , int inputType,@DrawableRes int LogoResourcesID, Context context, inputBox.OnDialogResultListener onDialogResultListener  ){
		inputBox Mdialog;
		Mdialog= new inputBox(context,inputType);
		Mdialog.setOnDialogResultListener(onDialogResultListener);



		Mdialog.show();
		Mdialog.setTitle(Title);
		Mdialog.setMessage(Message);
		Mdialog.setValueHint(ValueHint);
		Mdialog.SetValue(DefaultValue);
		Mdialog.SetLogo(LogoResourcesID);
		Window window = Mdialog.getWindow();
		window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
		return Mdialog;

	}
	public static inputBox InputBox(@StringRes int TitleId, @StringRes int MessageId, @StringRes int ValueHintId, String DefaultValue, int inputType, @DrawableRes int LogoResourcesID, Context context, inputBox.OnDialogResultListener onDialogResultListener ){
		inputBox Mdialog;
		Mdialog= new inputBox(context,inputType);
		Mdialog.setOnDialogResultListener(onDialogResultListener);



		Mdialog.show();
		Mdialog.setTitle(TitleId);
		Mdialog.setMessage(MessageId);
		Mdialog.setValueHint(ValueHintId);
		Mdialog.SetValue(DefaultValue);
		Mdialog.SetLogo(LogoResourcesID);
		Window window = Mdialog.getWindow();
		window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
		return Mdialog;



	}

}
