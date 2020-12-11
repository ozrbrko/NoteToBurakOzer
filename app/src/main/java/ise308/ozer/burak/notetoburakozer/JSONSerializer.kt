package ise308.ozer.burak.notetoburakozer

import android.content.Context
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONTokener
import java.io.*

class JSONSerializer(private val filename: String, private val context: Context) {
    @Throws(IOException::class, JSONException::class)
    fun save(noteList: List<Note>){
        val jsonArray = JSONArray()
        for(note in noteList){
            jsonArray.put(note.convertToJSON()) //This will convert note to a Json objects so Json object array can hold Json object
        }
        var writer: Writer? = null
        try {
            val out = context.openFileOutput(filename, Context.MODE_PRIVATE)
            writer = OutputStreamWriter(out)
            writer.write(jsonArray.toString())
        }finally {
            if (writer != null) {
                writer.close()
            }
        }
    }
    @Throws(IOException::class, JSONException::class)
    fun load(): ArrayList<Note> {
        val noteList = ArrayList<Note>()
        var reader: BufferedReader? = null

        try {
            val infile = context.openFileInput(filename)
            reader = BufferedReader(InputStreamReader(infile))
            val jsonString = StringBuilder()

            for (line in reader.readLine()) {
                jsonString.append(line)
            }

            val jsonArray = JSONTokener(jsonString.toString())
                .nextValue() as JSONArray

            for (i in 0 until jsonArray.length()) {
                noteList.add(Note(jsonArray.getJSONObject(i)))
            }

        } catch (e: FileNotFoundException) {
            // we will ignore this one, since it happens
            // when we start fresh. You could add a log here.

        } finally {
            // This will always run
            reader?.close()
        }
        return noteList
    }

}