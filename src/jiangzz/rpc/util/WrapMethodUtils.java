package jiangzz.rpc.util;

import java.lang.reflect.Method;

import jiangzz.rpc.model.MethodInvokeMeta;

public class WrapMethodUtils {
	/**
	 * 获取 method的元数据信息
	 * @param interfaceClass
	 * @param method
	 * @param args
	 * @return
	 */
	public static MethodInvokeMeta readMethod(Class interfaceClass,Method method,Object[] args){
		MethodInvokeMeta mim=new MethodInvokeMeta();
		mim.setInterfaceClass(interfaceClass);
		mim.setArgs(args);
		mim.setName(method.getName());
		mim.setResponseType(method.getReturnType());
		Class<?>[] parameterTypes = method.getParameterTypes();
		mim.setParameterTypes(parameterTypes);
		return mim;
	}
}
