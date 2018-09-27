package com.zjhuang.utils;

import com.zjhuang.springmvc.controller.IndexController;
import javassist.ClassPool;
import javassist.CtMethod;
import javassist.Modifier;
import javassist.NotFoundException;
import javassist.bytecode.LocalVariableAttribute;

import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * Javassist字节码处理工具
 *
 * @author zjhuang
 * @create 2018/9/27 12:08
 **/
public class JavassistUtils {

    public static void main(String[] args) throws Exception {
        Class<?> clazz = IndexController.class;
        Method method = clazz.getDeclaredMethod("index", String.class);
        System.out.println(Arrays.toString(getMethodParamNames(method)));
    }

    public static String[] getMethodParamNames(Method method) throws NotFoundException {
        if (method.getParameterCount() == 0) {
            return new String[0];
        }
        Class<?> clazz = method.getDeclaringClass();
        ClassPool pool = ClassPool.getDefault();
        CtMethod ctMethod = pool.getMethod(clazz.getName(), method.getName());
        LocalVariableAttribute attribute = (LocalVariableAttribute) ctMethod
                .getMethodInfo()
                .getCodeAttribute()
                .getAttribute(LocalVariableAttribute.tag);
        int pos = Modifier.isStatic(ctMethod.getModifiers()) ? 0 : 1;
        String[] paramNames = new String[ctMethod.getParameterTypes().length];
        for (int i = 0; i < ctMethod.getParameterTypes().length; i++) {
            paramNames[i] = attribute.variableName(i + pos);
        }
        return paramNames;
    }

}
