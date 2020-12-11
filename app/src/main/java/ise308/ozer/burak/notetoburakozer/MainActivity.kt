package ise308.ozer.burak.notetoburakozer

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.os.Bundle
import com.google.android.material.floatingactionbutton.FloatingActionButton
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import android.content.Intent
import android.provider.Settings
import android.util.Log

private const val TAG = "MainActivity"
class MainActivity : AppCompatActivity() {
    private var noteList: ArrayList<Note>? = null
    private var jsonserializer: JSONSerializer?= null
    private var recyclerView: RecyclerView? = null
    private var adapter: NoteAdapter? = null
    private var showDividers: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))

        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener { view ->
            val dialog = DialogNewNote()
            dialog.show(supportFragmentManager, "")
        }
        jsonserializer = JSONSerializer("NoteToSelf",applicationContext)

        try {
            noteList = jsonserializer!!.load()
        } catch (e: Exception) {
            noteList = ArrayList()

        }


        recyclerView = findViewById<View>(R.id.recyclerView) as RecyclerView
        adapter = NoteAdapter(this, noteList!!)
        val layoutManager = LinearLayoutManager(applicationContext)
        recyclerView!!.layoutManager = layoutManager
        recyclerView!!.itemAnimator = DefaultItemAnimator()

        recyclerView!!.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))

        recyclerView!!.adapter = adapter
    }

    override fun onResume() {
        super.onResume()
        val prefs = getSharedPreferences("Note to Self", Context.MODE_PRIVATE)
        showDividers = prefs.getBoolean("dividers", true)
        if (showDividers){
            recyclerView!!.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))
        } else {

            if (recyclerView!!.itemDecorationCount > 0)
                recyclerView!!.removeItemDecorationAt(0)
        }
    }

    fun createNewNote(n: Note) {

        noteList!!.add(n)
        adapter!!.notifyDataSetChanged()
    }

    fun showNote(noteToShow: Int) {
        val dialog = DialogShowNote()
        noteList?.get(noteToShow)?.let { dialog.sendNoteSelected(it) }

        dialog.show(supportFragmentManager, "")
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {

        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id= item.itemId
        val b= when(id){
            R.id.action_settings->{
                val intentToSettings = Intent(this,SettingsActivity::class.java)
                startActivity(intentToSettings)
                true
            }
            else-> super.onOptionsItemSelected(item)
        }

        return b
    }


    private fun saveNotes() {
        try {
            jsonserializer!!.save(this.noteList!!)
        } catch (e: Exception) {
            Log.e(TAG,"Error Saving Notes")
        }
    }

    override fun onPause() {
        super.onPause()
        saveNotes()
    }


}