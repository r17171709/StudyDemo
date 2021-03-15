package com.renyu.kotlin.chapter10

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.TypeAdapter
import com.google.gson.TypeAdapterFactory
import com.google.gson.internal.Streams
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonWriter

/**
 * 参考文章：https://juejin.cn/post/6939117474105278472/
 */

open class BaseMessage(val msgType: String?, val id: Int?)

class TextMessage(content: String?, msgType: String?, id: Int?) : BaseMessage(msgType, id)

class ImageMessage(url: String?, msgType: String?, id: Int?) : BaseMessage(msgType, id)

class BaseMessageTypeAdapter(private val gson: Gson, private val skipPast: TypeAdapterFactory) :
    TypeAdapter<BaseMessage>() {
    override fun write(p0: JsonWriter?, p1: BaseMessage?) {
        if (p1 == null) {
            p0?.nullValue()
            return
        }
        getTypeAdapterByType(p1.msgType)?.write(p0, p1)
    }

    override fun read(p0: JsonReader?): BaseMessage? {
        val a = Streams.parse(p0)
        val jsonObject = a.asJsonObject
        val msgType = jsonObject.get("msgType").asString
        // 根据msgType的值解析该类型的TypeAdapter
        val adapter = getTypeAdapterByType(msgType)
        return adapter?.fromJsonTree(jsonObject)
    }

    private fun getTypeAdapterByType(msgType: String?): TypeAdapter<BaseMessage>? {
        return when (msgType) {
            "text" -> {
                getTypeAdapter(TextMessage::class.java)
            }
            "image" -> {
                getTypeAdapter(ImageMessage::class.java)
            }
            else -> null
        }
    }

    private fun <R : BaseMessage> getTypeAdapter(clazz: Class<R>): TypeAdapter<BaseMessage> {
        return SubTypeAdapterWrapper(clazz, gson.getDelegateAdapter(skipPast, TypeToken.get(clazz)))
    }
}

class SubTypeAdapterWrapper<T, R : T>(private val clazz: Class<R>, private val adapter: TypeAdapter<R>) :
    TypeAdapter<T>() {
    override fun write(p0: JsonWriter?, p1: T) {
        adapter.write(p0, p1 as R)
    }

    override fun read(p0: JsonReader?): T {
        return adapter.read(p0)
    }
}

class BaseMessageTypeAdapterFactory : TypeAdapterFactory {
    override fun <T : Any?> create(p0: Gson, p1: TypeToken<T>): TypeAdapter<T>? {
        if (!BaseMessage::class.java.isAssignableFrom(p1.rawType)) {
            return null
        }
        return BaseMessageTypeAdapter(gson = p0, this) as TypeAdapter<T>
    }

}

fun main() {
    val json = """
            	{
		"msgType" : "image",
		"id" : "3",
		"url" : "https://xxxx.jpg",
		"size" : "300x300"
	}
        """
    val gson = GsonBuilder()
        .registerTypeAdapterFactory(BaseMessageTypeAdapterFactory())
        .create()
    val image = gson.fromJson<BaseMessage>(json, BaseMessage::class.java)
    if (image is ImageMessage) {

    }
}