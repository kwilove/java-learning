package com.zjhuang.serialize;

public interface Serializer {

	/**
	 * 序列化
	 * @param obj	对象实例
	 * @param <T>	字节数组
	 * @return
	 */
	<T> byte[] serialize(T obj);

	/**
	 * 反序列化
	 * @param bytes	字节数组
	 * @param clazz	反序列化的目标对象类型
	 * @param <T>	对象实例
	 * @return
	 */
	<T> Object deserialize(byte[] bytes, Class<T> clazz) ;
}
