package j2w.team.modules.toast;

import android.content.Context;
import android.os.Looper;
import android.widget.Toast;

import j2w.team.J2WApplication;
import j2w.team.J2WHelper;

/**
 * Created by wungko on 15/3/17. 弱交互Tost 消息弹窗 Update by skyJC on 15/8/05
 */
public class J2WToast {

	private J2WToast() {}

	Context	context;

	public J2WToast(Context context) {
		this.context = context;
	}

	private Toast	mToast	= null;

	/**
	 * 简单Toast 消息弹出
	 * 
	 * @param msg
	 */
	public void show(final String msg) {
		// 判断是否在主线程
		boolean isMainLooper = Looper.getMainLooper().getThread() != Thread.currentThread();

		if (isMainLooper) {
			J2WHelper.mainLooper().execute(new Runnable() {

				@Override public void run() {
					showToast(msg, Toast.LENGTH_SHORT);
				}
			});
		} else {
			showToast(msg, Toast.LENGTH_SHORT);
		}
	}

	/**
	 * 简单Toast 消息弹出
	 * 
	 * @param msg
	 */
	public void show(final int msg) {
		// 判断是否在主线程
		boolean isMainLooper = Looper.getMainLooper().getThread() != Thread.currentThread();

		if (isMainLooper) {
			J2WHelper.mainLooper().execute(new Runnable() {

				@Override public void run() {
					showToast(J2WHelper.getInstance().getString(msg), Toast.LENGTH_SHORT);
				}
			});
		} else {
			showToast(J2WHelper.getInstance().getString(msg), Toast.LENGTH_SHORT);
		}
	}

	/**
	 * 弹出提示
	 * 
	 * @param text
	 * @param duration
	 */
	protected void showToast(String text, int duration) {
		if (mToast == null) {
			mToast = Toast.makeText(context, text, duration);
		} else {
			mToast.setText(text);
			mToast.setDuration(duration);
		}

		mToast.show();
	}
}
