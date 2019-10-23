package com.zjhuang.serialize.impl;

import com.zjhuang.serialize.Serializer;
import io.protostuff.LinkedBuffer;
import io.protostuff.ProtobufIOUtil;
import io.protostuff.Schema;
import io.protostuff.runtime.RuntimeSchema;

/**
 * Protobuf序列化工具
 * @author zjhuang
 * @create 2018/11/24 22:09
 **/
public class ProtobufSerializer implements Serializer {

    @Override
    public <T> byte[] serialize(T obj) {
        Schema schema = RuntimeSchema.getSchema(obj.getClass());
        return ProtobufIOUtil.toByteArray(obj, schema, LinkedBuffer.allocate(256));
    }

    @Override
    public <T> Object deserialize(byte[] bytes, Class<T> clazz) {
        T obj = null;
        try {
            obj = clazz.newInstance();
            Schema schema = RuntimeSchema.getSchema(clazz);
            ProtobufIOUtil.mergeFrom(bytes, obj, schema);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return obj;
    }

}
