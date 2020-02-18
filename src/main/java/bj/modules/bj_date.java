package bj.modules;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Handler;
import android.text.InputType;
import android.util.Log;
import android.widget.DatePicker;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import bj.modules.bjdate.R;

public class bj_date {
	public static String dateToString(Date date, Boolean WithTime){
		String dateTime;
		dateTime = "";


		DateFormat dfy;
		dfy = new SimpleDateFormat("yyyy");
		DateFormat dfm = new SimpleDateFormat("MM");
		DateFormat dfd = new SimpleDateFormat("dd");

		DateFormat tfh = new SimpleDateFormat("HH");
		DateFormat tfm = new SimpleDateFormat("mm");
		DateFormat tfs = new SimpleDateFormat("ss");

		String YY = dfy.format(Calendar.getInstance().getTime());
		String MM = dfm.format(Calendar.getInstance().getTime());
		String DD = dfd.format(Calendar.getInstance().getTime());

		String hh = tfh.format(Calendar.getInstance().getTime());
		String mm = tfm.format(Calendar.getInstance().getTime());
		String ss = tfs.format(Calendar.getInstance().getTime());

		if (WithTime) {
			dateTime=YY + "/" + MM +"/"+ DD + " " + hh + ":" + mm + ":" + ss;
		}else {
			dateTime=YY + "/" + MM +"/"+ DD ;
		}
		return dateTime;
	}
	public static Date stringToDate(String dateSTR,Boolean WithTime){

		Date date = null;
		SimpleDateFormat format;
		if (WithTime) {

			format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

		}else {

			format = new SimpleDateFormat("yyyy/MM/dd");

		}

		Log.e("GGN","dateSTR: " + dateSTR);
		try {
			date = format.parse(dateSTR);
			Log.e("GGN","1:"+date.toString());
		} catch (Exception e) {
			// Second Try
			if (WithTime) {

				format = new SimpleDateFormat("yyyy/dd/MM HH:mm:ss");

			}else {

				format = new SimpleDateFormat("yyyy/dd/MM");

			}
			try {
				date = format.parse(dateSTR);
				Log.e("GGN","2:"+date.toString());
			}catch (Exception e1){

			}

		}
		return date;
	}
	public static void datePickerProces(final TextView textView, final Boolean WithTime){
		final int mYear, mMonth, mDay, mHour, mMinute,mSecond;
		Date date;

		date=stringToDate(textView.getText().toString(),WithTime);

		if (date==null){
			date=Calendar.getInstance().getTime();

		}
		Log.e("GGN","3:"+date.toString());
		//***********
		DateFormat dfy = new SimpleDateFormat("yyyy");
		DateFormat dfm = new SimpleDateFormat("MM");
		DateFormat dfd = new SimpleDateFormat("dd");

		DateFormat tfh = new SimpleDateFormat("HH");
		DateFormat tfm = new SimpleDateFormat("mm");
		DateFormat tfs = new SimpleDateFormat("ss");

		String YY = dfy.format(date);
		String MM = dfm.format(date);
		String DD = dfd.format(date);

		String hh = tfh.format(date);
		String mm = tfm.format(date);
		String ss = tfs.format(date);
		//***************
		mYear=Integer.decode(YY);
		mMonth=Integer.decode(MM);
		mDay=Integer.decode(DD);
		mHour=Integer.decode(hh);
		mMinute=Integer.decode(mm);
		mSecond=Integer.decode(ss);

		if (WithTime) {
			textView.setInputType(InputType.TYPE_CLASS_DATETIME);
		}else {
			textView.setInputType(InputType.TYPE_DATETIME_VARIATION_DATE);
		}
		Log.e("GGN",mYear + "/" + mMonth + "/" + mDay + " " + mHour + ":" + mMinute + ":" + mSecond);
		Dialog DPD = new DatePickerDialog(textView.getContext(), new DatePickerDialog.OnDateSetListener() {
			@Override
			public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {


				if (WithTime) {
					//pick up Time

					textView.setText(year + "/" + month + "/"+dayOfMonth +" " + mHour + ":" + mMinute + ":" + mSecond);
				}else {

					textView.setText(year + "/" + month + "/"+dayOfMonth);
				}
			}
		},mYear, mMonth ,mDay);
		DPD.show();

	}

	public static void set_Date_For_TXV(final TextView textView){
		final Handler handler=new Handler();
		Thread thread=new Thread(new Runnable() {
			public Boolean activ=true;
			@Override
			public void run() {

				while (activ){
					try {

						handler.post(new Runnable() {
							@Override
							public void run() {
								textView.setText(bj_date.jalali_Date());
								if (!textView.isAttachedToWindow()){
									activ=false;
								}
							}
						});
						Thread.sleep(60000);


					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		});
		thread.start();
	}
	public static void set_Clock_For_TXV(final TextView textView){



		final Handler handler=new Handler();
		Thread thread=new Thread(new Runnable() {
			public Boolean activ=true;
			@Override
			public void run() {


				while (activ){
					try {

						handler.post(new Runnable() {
							@Override
							public void run() {
								textView.setText(bj_date.jalali_Time());
								if (!textView.isAttachedToWindow()){
									activ=false;
								}
							}
						});

						Thread.sleep(1000);


					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		});
		thread.start();
	}
	public static Thread set_Timer_For_TXV(final TextView textView,final  Date From,final Boolean Invers){
		final Handler handler=new Handler();
		final long[][] T = new long[1][1];
		final long[][] diff = new long[1][1];
		final long[][] H = new long[1][1];
		final long[][] M = new long[1][1];
		final long[][] S = new long[1][1];


		Thread thread=new Thread(new Runnable() {
			public Boolean activ=true;
			@Override
			public void run() {
				while (!Thread.currentThread().isInterrupted() & activ){
					if (Invers) {
						diff[0] = new long[]{From.getTime() - Calendar.getInstance().getTime().getTime()};

					}else {
						diff[0] = new long[]{Calendar.getInstance().getTime().getTime() - From.getTime()};

					}
					T[0] = new long[]{TimeUnit.MILLISECONDS.toSeconds(diff[0][0])};
					H[0] = new long[]{T[0][0] / 3600};
					M[0] = new long[]{(T[0][0] % 3600) / 60};
					S[0] = new long[]{(T[0][0] % 3600) % 60};
					handler.post(new Runnable() {
						@Override
						public void run() {
							textView.setText(H[0] + ":" + M[0] + ":" + S[0]);
							if (!textView.isAttachedToWindow()){
								activ=false;
							}
						}
					});
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		});
		return thread;
	}
	public static Thread set_Timer_CountDown_For_TXV(final TextView textView,final long CountDownNum, final Boolean BySecond){
		final Handler handler=new Handler();
		Thread thread=new Thread(new Runnable() {
			public Boolean activ=true;
			@Override
			public void run() {
				for (long i = CountDownNum; i>=0; i-=1){
					if (activ=false){
						break;
					}
					final long finalI = i;
					handler.post(new Runnable() {
						@Override
						public void run() {
							textView.setText(finalI +"");
							if (!textView.isAttachedToWindow()){
								activ=false;
							}
						}
					});
					if (BySecond) {
						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}else {
						try {
							Thread.sleep(60000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
			}
		});
		return thread;
	}
	public static Thread set_Timer_CountDown_For_TXV(final TextView textView,final Integer CountDownNum,final Date CountDownFrom, final Boolean BySecond){
		final long[] T = {betweenTwoTime_Min(CountDownFrom, Calendar.getInstance().getTime())};
		final long[] m = {CountDownNum - T[0]};



		final Handler handler=new Handler();

		Thread thread=new Thread(new Runnable() {
			public Boolean activ=true;


			@Override
			public void run() {

				while (!Thread.currentThread().isInterrupted() & activ){
					handler.post(new Runnable() {
						@Override
						public void run() {
							if (BySecond){
								T[0]=T[0]*60;
								m[0]=m[0]*60;
							}
							if (BySecond) {
								textView.setText(m[0] + " " + textView.getContext().getResources().getString(R.string.Time_title_sec));
							}else {
								textView.setText(m[0] + " " + textView.getContext().getResources().getString(R.string.Time_title_min));
							}
							if(m[0]>0) {
								textView.setTextColor(textView.getContext().getResources().getColor(R.color.colorAccent));
							}else {
								textView.setTextColor(textView.getContext().getResources().getColor(R.color.colorPrimary));
							}

							if (!textView.isAttachedToWindow()){
								activ=false;
							}

						}
					});
					if (BySecond) {
						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}else {
						try {
							Thread.sleep(60000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
					final long[] T = {betweenTwoTime_Min(CountDownFrom, Calendar.getInstance().getTime())};
					m[0] =CountDownNum- T[0];
				}
			}
		});

		return thread;

	}
	public static Thread set_Timer_CountDown_For_TXV(final TextView textView, final Integer CountDownNum, final String CountDownFromJalaliDate, final String CountDownFromJalaliTime, final Boolean BySecond){
		final long[] T = {betweenTwoTime_Min(CountDownFromJalaliDate, CountDownFromJalaliTime, jalali_Date(), jalali_Time())};
		final long[] m = {CountDownNum - T[0]};



		final Handler handler=new Handler();

		Thread thread=new Thread(new Runnable() {
			public Boolean activ=true;


			@Override
			public void run() {

				while (!Thread.currentThread().isInterrupted() & activ){
					handler.post(new Runnable() {
						@Override
						public void run() {
							if (BySecond){
								T[0]=T[0]*60;
								m[0]=m[0]*60;
							}
							if (BySecond) {
								textView.setText(m[0] + " " + textView.getContext().getResources().getString(R.string.Time_title_sec));
							}else {
								textView.setText(m[0] + " " + textView.getContext().getResources().getString(R.string.Time_title_min));
							}
							if(m[0]>0) {
								textView.setTextColor(textView.getContext().getResources().getColor(R.color.colorAccent));
							}else {
								textView.setTextColor(textView.getContext().getResources().getColor(R.color.colorPrimary));
							}

							if (!textView.isAttachedToWindow()){
								activ=false;
							}

						}
					});
					if (BySecond) {
						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}else {
						try {
							Thread.sleep(60000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
					T[0] =betweenTwoTime_Min(CountDownFromJalaliDate,CountDownFromJalaliTime,jalali_Date(),jalali_Time());
					m[0] =CountDownNum- T[0];
				}
			}
		});

		return thread;
	}

	public static int[] gregorian_to_Jalali(int gy, int gm, int gd){
		int[] g_d_m = {0,31,59,90,120,151,181,212,243,273,304,334};
		int jy;
		if(gy>1600){
			jy=979;
			gy-=1600;
		}else{
			jy=0;
			gy-=621;
		}
		int gy2 = (gm > 2)?(gy + 1):gy;
		int days = (365 * gy) + ((int)((gy2 + 3) / 4)) - ((int)((gy2 + 99) / 100)) + ((int)((gy2 + 399) / 400)) - 80 + gd + g_d_m[gm - 1];
		jy += 33 * ((int)(days / 12053));
		days %= 12053;
		jy += 4 * ((int)(days / 1461));
		days %= 1461;
		if(days > 365){
			jy+=(int)((days-1)/365);
			days=(days-1)%365;
		}
		int jm = (days < 186)?1 + (int)(days / 31):7 + (int)((days - 186) / 30);
		int jd = 1 + ((days < 186)?(days % 31):((days - 186) % 30));
		int[] out = {jy,jm,jd};
		return out;
	}
	public static String gregorian_to_Jalali_String(int gy, int gm, int gd){
		int[] g_d_m = {0,31,59,90,120,151,181,212,243,273,304,334};
		int jy;
		if(gy>1600){
			jy=979;
			gy-=1600;
		}else{
			jy=0;
			gy-=621;
		}
		int gy2 = (gm > 2)?(gy + 1):gy;
		int days = (365 * gy) + ((int)((gy2 + 3) / 4)) - ((int)((gy2 + 99) / 100)) + ((int)((gy2 + 399) / 400)) - 80 + gd + g_d_m[gm - 1];
		jy += 33 * ((int)(days / 12053));
		days %= 12053;
		jy += 4 * ((int)(days / 1461));
		days %= 1461;
		if(days > 365){
			jy+=(int)((days-1)/365);
			days=(days-1)%365;
		}
		int jm = (days < 186)?1 + (int)(days / 31):7 + (int)((days - 186) / 30);
		int jd = 1 + ((days < 186)?(days % 31):((days - 186) % 30));
		String Sjm,Sjd;
		if(jm<10) {
			Sjm="0"+jm;
		}else {
			Sjm=""+jm;
		}
		if(jd<10) {
			Sjd="0"+jd;
		}else {
			Sjd=""+jd;
		}

		return jy + "/" + Sjm+ "/" + Sjd;
	}



	public static int[] jalali_to_Gregorian(int jy, int jm, int jd){
		int gy;
		if(jy>979){
			gy=1600;
			jy-=979;
		}else{
			gy=621;
		}
		int days = (365 * jy) + (((int)(jy / 33)) * 8) + ((int)(((jy % 33) + 3) / 4)) + 78 + jd + ((jm < 7)?(jm - 1) * 31:((jm - 7) * 30) + 186);
		gy += 400 * ((int)(days / 146097));
		days %= 146097;
		if(days > 36524){
			gy += 100 * ((int)(--days / 36524));
			days %= 36524;
			if (days >= 365)days++;
		}
		gy += 4 * ((int)(days / 1461));
		days %= 1461;
		if(days > 365){
			gy += (int)((days - 1) / 365);
			days = (days - 1) % 365;
		}
		int gd = days + 1;
		int[] sal_a = {0,31,((gy % 4 == 0 && gy % 100 != 0) || (gy % 400 == 0))?29:28,31,30,31,30,31,31,30,31,30,31};
		int gm;
		for(gm = 0;gm < 13;gm++){
			int v = sal_a[gm];
			if(gd <= v)break;
			gd -= v;
		}
		int[] out = {gy,gm,gd};
		return out;
	}
	public static int[] jalali_to_Gregorian(String JalaliDate){

		int jy,  jm,  jd;

		jy= Integer.decode( JalaliDate.substring(0,4));
		try {
			jm= Integer.decode(JalaliDate.substring(5,7));
		}catch (Exception e){
			jm= Integer.decode(JalaliDate.substring(6,7));
		}
		try {
			jd= Integer.decode(JalaliDate.substring(8,10));
		}catch (Exception e){
			jd= Integer.decode(JalaliDate.substring(9,10));
		}

		int gy;
		if(jy>979){
			gy=1600;
			jy-=979;
		}else{
			gy=621;
		}
		int days = (365 * jy) + (((int)(jy / 33)) * 8) + ((int)(((jy % 33) + 3) / 4)) + 78 + jd + ((jm < 7)?(jm - 1) * 31:((jm - 7) * 30) + 186);
		gy += 400 * ((int)(days / 146097));
		days %= 146097;
		if(days > 36524){
			gy += 100 * ((int)(--days / 36524));
			days %= 36524;
			if (days >= 365)days++;
		}
		gy += 4 * ((int)(days / 1461));
		days %= 1461;
		if(days > 365){
			gy += (int)((days - 1) / 365);
			days = (days - 1) % 365;
		}
		int gd = days + 1;
		int[] sal_a = {0,31,((gy % 4 == 0 && gy % 100 != 0) || (gy % 400 == 0))?29:28,31,30,31,30,31,31,30,31,30,31};
		int gm;
		for(gm = 0;gm < 13;gm++){
			int v = sal_a[gm];
			if(gd <= v)break;
			gd -= v;
		}
		int[] out = {gy,gm,gd};
		return out;
	}
	public static String jalali_to_Gregorian_String(int jy, int jm, int jd){
		int gy;
		if(jy>979){
			gy=1600;
			jy-=979;
		}else{
			gy=621;
		}
		int days = (365 * jy) + (((int)(jy / 33)) * 8) + ((int)(((jy % 33) + 3) / 4)) + 78 + jd + ((jm < 7)?(jm - 1) * 31:((jm - 7) * 30) + 186);
		gy += 400 * ((int)(days / 146097));
		days %= 146097;
		if(days > 36524){
			gy += 100 * ((int)(--days / 36524));
			days %= 36524;
			if (days >= 365)days++;
		}
		gy += 4 * ((int)(days / 1461));
		days %= 1461;
		if(days > 365){
			gy += (int)((days - 1) / 365);
			days = (days - 1) % 365;
		}
		int gd = days + 1;
		int[] sal_a = {0,31,((gy % 4 == 0 && gy % 100 != 0) || (gy % 400 == 0))?29:28,31,30,31,30,31,31,30,31,30,31};
		int gm;
		for(gm = 0;gm < 13;gm++){
			int v = sal_a[gm];
			if(gd <= v)break;
			gd -= v;
		}
		int[] out = {gy,gm,gd};

		String Sgm,Sgd;
		if(gm<10) {
			Sgm="0"+gm;
		}else {
			Sgm=""+gm;
		}
		if(gd<10) {
			Sgd="0"+gd;
		}else {
			Sgd=""+gd;
		}

		return gy + "/" + Sgm+ "/" + Sgd;
	}

	public  static String jalali_Now(){



		return jalali_Date() + " " + jalali_Time();

	}
	public  static String jalali_Date(){
		DateFormat dfy = new SimpleDateFormat("yyyy");
		DateFormat dfm = new SimpleDateFormat("M");
		DateFormat dfd = new SimpleDateFormat("d");
		String YY = dfy.format(Calendar.getInstance().getTime());
		String MM = dfm.format(Calendar.getInstance().getTime());
		String DD = dfd.format(Calendar.getInstance().getTime());



		return gregorian_to_Jalali_String(Integer.decode(YY),Integer.decode(MM),Integer.decode(DD));


	}
	public  static String jalali_Date_PlusDays(Integer Days){
		DateFormat dfy = new SimpleDateFormat("yyyy");
		DateFormat dfm = new SimpleDateFormat("M");
		DateFormat dfd = new SimpleDateFormat("d");
		Date d=Calendar.getInstance().getTime();
		d.setDate(d.getDate()+Days);
		String YY = dfy.format(d);
		String MM = dfm.format(d);
		String DD = dfd.format(d);



		return gregorian_to_Jalali_String(Integer.decode(YY),Integer.decode(MM),Integer.decode(DD));
	}
	public  static String jalali_Time(){
		DateFormat dfy = new SimpleDateFormat("HH:mm:ss");

		return dfy.format(Calendar.getInstance().getTime());


	}
	public  static String jalali_Time_PlusSeconds(Integer Seconds){
		DateFormat dfy = new SimpleDateFormat("HH:mm:ss");
		Date d=Calendar.getInstance().getTime();
		d.setSeconds(d.getSeconds()+Seconds);
		return dfy.format(d);


	}

	public static int[] time_to_Array(String mTime){

		int jH,  jM,  jS;


		try {
			jH= Integer.decode( mTime.substring(0,2));
		}catch (Exception e){
			jH= Integer.decode( mTime.substring(1,2));
		}
		try {
			jM= Integer.decode(mTime.substring(3,5));
		}catch (Exception e){
			jM= Integer.decode(mTime.substring(4,5));
		}
		try {
			jS= Integer.decode(mTime.substring(6,8));
		}catch (Exception e){
			jS= Integer.decode(mTime.substring(7,8));
		}


		int[] out = {jH,jM,jS};
		return out;
	}

	public static long betweenTwoTime_Min(Date FromDateTime, Date ToDateTime){
		long diff = ToDateTime.getTime() - FromDateTime.getTime();
		return TimeUnit.MILLISECONDS.toMinutes(diff);
	}
	public static long betweenTwoTime_Min(String FromJalaliDate,String FromJalaliTime,String ToJalaliDate,String ToJalaliTime){
		Date d1=null;
		Date d2=null;
		int[] GD1=jalali_to_Gregorian(FromJalaliDate);
		int[] GD2=jalali_to_Gregorian(ToJalaliDate);
		int[] GT1=time_to_Array(FromJalaliTime);
		int[] GT2=time_to_Array(ToJalaliTime);

		d1=new Date(GD1[0],GD1[1],GD1[2],GT1[0],GT1[1],GT1[2]);
		d2=new Date(GD2[0],GD2[1],GD2[2],GT2[0],GT2[1],GT2[2]);

		long diff = d2.getTime() - d1.getTime();
		return TimeUnit.MILLISECONDS.toMinutes(diff);
	}

}
