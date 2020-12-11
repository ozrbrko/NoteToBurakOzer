package ise308.ozer.burak.notetoburakozer

import org.json.JSONException;
import org.json.JSONObject;
import android.content.Context;
import org.json.JSONArray
import org.json.JSONTokener
import java.io.*


private val JSON_TITLE = "title"
private val JSON_DESCRIPTION = "description"
private val JSON_IDEA = "idea"
private val JSON_TODO = "todo"
private val JSON_IMPORTANT = "important"

class Note {
    var title: String? = null
    var description: String? = null
    var idea: Boolean = false
    var todo: Boolean = false
    var important: Boolean = false


    @Throws(JSONException::class)
    constructor(jsonObject: JSONObject) {
        title= jsonObject.getString(JSON_TITLE)
        description= jsonObject.getString(JSON_DESCRIPTION)
        idea=jsonObject.getBoolean(JSON_IDEA)
        todo=jsonObject.getBoolean(JSON_TODO)
        important=jsonObject.getBoolean(JSON_IDEA)

    }
    constructor(){

    }
    @Throws(JSONException::class)
    fun convertToJSON(): JSONObject {
        val jo = JSONObject()
        jo.put(JSON_TITLE, title)
        jo.put(JSON_DESCRIPTION, description)
        jo.put(JSON_IDEA, idea)
        jo.put(JSON_TODO, todo)
        jo.put(JSON_IMPORTANT, important)
        return jo
    }

}