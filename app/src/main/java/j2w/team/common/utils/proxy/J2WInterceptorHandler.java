package j2w.team.common.utils.proxy;

import android.os.Looper;

import java.lang.reflect.Method;
import java.util.concurrent.CountDownLatch;

import j2w.team.J2WHelper;
import j2w.team.biz.Interceptor;
import j2w.team.biz.J2WBiz;

/**
 * Created by sky on 15/2/18.动态代理-线程系统
 */
public class J2WInterceptorHandler<T> extends BaseHandler<T> {

	J2WBiz	j2WBiz;
	CountDownLatch countDownLatch;

	public J2WInterceptorHandler(T t, J2WBiz j2WBiz) {
		super(t);
		this.j2WBiz = j2WBiz;
	}

	@Override public Object invoke(Object proxy, final Method method, final Object[] args) throws Throwable {
		Object returnObject = null;
		// 获得注解数组
		Interceptor interceptor = method.getAnnotation(Interceptor.class);
		returnObject = method.invoke(t, args);// 执行
		if (interceptor != null && j2WBiz != null) {
			// 判断是否在主线程
			boolean isMainLooper = Looper.getMainLooper().getThread() != Thread.currentThread();
			if (isMainLooper) {
				this.countDownLatch = new CountDownLatch(1);

				J2WHelper.mainLooper().execute(new Runnable() {
					@Override
					public void run() {
						j2WBiz.interceptorImpl(t.getClass());
					}
				});
				countDownLatch.await();
			}else{
				j2WBiz.interceptorImpl(t.getClass());
			}
		}
		return returnObject;
	}
}
