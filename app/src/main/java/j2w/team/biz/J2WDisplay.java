package j2w.team.biz;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.Toolbar;

import j2w.team.common.log.L;
import j2w.team.common.utils.J2WCheckUtils;
import j2w.team.view.J2WActivity;
import j2w.team.view.J2WDialogFragment;
import j2w.team.view.J2WFragment;
import j2w.team.view.J2WView;

/**
 * @创建人 sky
 * @创建时间 15/7/11 下午2:39
 * @类描述 统一控制TitleBar、Drawer以及所有Activity和Fragment跳转
 */
public class J2WDisplay implements J2WIDisplay {

	private J2WView	j2WView;

	@Override public Context context() {
		return j2WView.context();
	}

	@Override public void initDisplay(J2WActivity j2WActivity) {
		j2WView = new J2WView();
		j2WView.initUI(j2WActivity);
	}

	@Override public void initDisplay(J2WFragment fragment) {
		j2WView = new J2WView();
		j2WView.initUI(fragment);
	}

	@Override public void initDisplay(J2WDialogFragment fragment) {
		j2WView = new J2WView();
		j2WView.initUI(fragment);
	}

	@Override public void initDisplay(Context context) {
		j2WView = new J2WView();
		j2WView.initUI(context);
	}

	@Override public FragmentManager manager() {
		return activity().getSupportFragmentManager();
	}

	@Override public void intentFromFragment(Class clazz, Fragment fragment, int requestCode) {
		Intent intent = new Intent();
		intent.setClass(activity(), clazz);
		intentFromFragment(intent, fragment, requestCode);
	}

	@Override public void intentFromFragment(Intent intent, Fragment fragment, int requestCode) {
		L.tag("J2WDisplay");
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("从 ");
		stringBuilder.append(activity().getClass().getSimpleName());
		stringBuilder.append(" 跳转到 ");
		stringBuilder.append(intent.getComponent().getClassName());
		stringBuilder.append(" Tag :");
		stringBuilder.append(fragment.getClass().getSimpleName());

		L.i(stringBuilder.toString());
		activity().startActivityFromFragment(fragment, intent, requestCode);
	}

	protected Toolbar toolbar(int... types) {
		Toolbar toolbar = j2WView.toolbar(types);
		J2WCheckUtils.checkNotNull(toolbar, "标题栏没有打开，无法调用");
		return toolbar;
	}

	protected J2WActivity activity() {
		J2WCheckUtils.checkNotNull(j2WView, "Activity没有初始化");
		return j2WView.activity();
	}

	protected J2WFragment fragment(){
		J2WCheckUtils.checkNotNull(j2WView, "Activity没有初始化");
		return j2WView.fragment();
	}

	protected void intent(Class clazz) {
		intent(clazz, null);
	}

	protected void intent(Class clazz, Bundle bundle) {
		Intent intent = new Intent();
		intent.setClass(activity(), clazz);
		intent(intent, bundle);
	}

	protected void intent(Intent intent) {
		intent(intent, null);
	}

	protected void intent(Intent intent, Bundle options) {
		intentForResult(intent, options, -1);
	}

	protected void intentForResult(Class clazz, int requestCode) {
		intentForResult(clazz, null, requestCode);
	}

	protected void intentForResult(Class clazz, Bundle bundle, int requestCode) {
		Intent intent = new Intent();
		intent.setClass(activity(), clazz);
		intentForResult(intent, bundle, requestCode);
	}

	protected void intentForResult(Intent intent, int requestCod) {
		intentForResult(intent, null, requestCod);
	}

	@TargetApi(Build.VERSION_CODES.JELLY_BEAN) protected void intentForResult(Intent intent, Bundle options, int requestCode) {
		J2WCheckUtils.checkNotNull(intent, "intent不能为空～");
		L.tag("J2WDisplay");
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("从 ");
		stringBuilder.append(activity().getClass().getSimpleName());
		stringBuilder.append(" 跳转到 ");
		stringBuilder.append(intent.getComponent().getClassName());
		L.i(stringBuilder.toString());
		if (options != null) {
			intent.putExtras(options);
		}
		activity().startActivityForResult(intent, requestCode);
	}

	@Override public void detach() {
		if (j2WView != null) {
			j2WView.detach();
			j2WView = null;
		}
	}
}