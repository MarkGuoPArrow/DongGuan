package markettracker.util;

import orient.champion.business.R;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;
import markettracker.util.Constants.AlertType;

public class ShowInfoBuilder implements OnClickListener {

	private Button set, edit, cancel;
	private TextView textView;
	private Dialog alert;

	private View view;
	private Animation mButtomInAnimation, mButtomOutAnimation;

	private int type = 0;

	public ShowInfoBuilder(Context context, OnClickListener l, String promName) {
		init(context, l, promName, false, false);
	}

	public ShowInfoBuilder(Context context, OnClickListener l, String promName,
			int type, boolean isSystem, boolean isFeedBack) {
		this.type = type;
		init(context, l, promName, isSystem, isFeedBack);
	}

	public void dismiss() {
		if (alert != null) {
			if (view != null) {
				view.clearAnimation();
				view.startAnimation(mButtomOutAnimation);
			}

			// alert.dismiss();
			// alert = null;
			// builder=null;
		}
	}

	private void init(final Context context, OnClickListener l, String promName, boolean isSystem, boolean isFeedBack) {
		mButtomInAnimation = AnimationUtils.loadAnimation(context,
				R.anim.slide_buttom_in);
		mButtomOutAnimation = AnimationUtils.loadAnimation(context,
				R.anim.slide_buttom_out);

		mButtomOutAnimation
				.setAnimationListener(new Animation.AnimationListener() {

					public void onAnimationStart(Animation animation) {
					}

					public void onAnimationRepeat(Animation animation) {
					}

					public void onAnimationEnd(Animation animation) {
						if (alert != null) {
							alert.dismiss();
							alert = null;
						}
					}
				});

		view = LayoutInflater.from(context).inflate(R.layout.showinfo, null);

		set = (Button) view.findViewById(R.id.set);
		if(isSystem){	//系统
			set.setVisibility(View.GONE);
		}else{
			set.setVisibility(View.VISIBLE);
		}
		
		if(isFeedBack){	//已反馈
			set.setOnClickListener(new OnClickListener() {
				
				public void onClick(View arg0) {
					Tool.showToastMsg(context, "已反馈的单店活动不能再修改", AlertType.DEFAULT);
				}
			});
		}else{
			set.setOnClickListener(l);
		}

		cancel = (Button) view.findViewById(R.id.cancel);
		cancel.setOnClickListener(this);

		edit = (Button) view.findViewById(R.id.edit);
		edit.setOnClickListener(l);

		if (this.type == 1) {
			set.setText("修改");
			edit.setText("反馈");
		}

		textView = (TextView) view.findViewById(R.id.text);
		textView.setText(promName);

		alert = new Dialog(context, R.style.showinfo_dialog);
		alert.setContentView(view);
		alert.show();
		alert.setCancelable(false);

		alert.getWindow().setLayout(LayoutParams.FILL_PARENT,
				LayoutParams.WRAP_CONTENT);
		view.clearAnimation();
		view.startAnimation(mButtomInAnimation);

	}

	public void onClick(View v) {
		System.out.println(v.getId());
		switch (v.getId()) {
		case R.id.set:
			// resetPwd();
			break;

		case R.id.cancel:
			// resetPwd();
			dismiss();
			break;

		default:
			break;
		}
	}

}
