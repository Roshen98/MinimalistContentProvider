package com.bignerdranch.android.minimalistcontentprovider

import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bignerdranch.android.minimalistcontentprovider.databinding.ActivityMainBinding


private const val TAG = "MainActivity"
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var mTextView: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mTextView = findViewById(R.id.textview)
    }

    fun onClickDisplayEntries(view:View){
        val queryUri: String = Contract.CONTENT_URI.toString()
        val projection = arrayOf(Contract.CONTENT_PATH)
        val selectionClause: String?
        val selectionArgs: Array<String>?
        val sortOrder: String? = null

        when(view.id) {
            R.id.button_display_all -> {
                selectionClause = null
                selectionArgs = null
            }
            R.id.button_display_first -> {
                selectionClause = "${Contract.WORD_ID} = ?"
                selectionArgs = arrayOf("0")
            }
            else -> {
                selectionClause = null
                selectionArgs = null
            }
        }

        val cursor: Cursor? = contentResolver
            .query(Uri.parse(queryUri),
                projection,
                selectionClause,
                selectionArgs,
                sortOrder)

        if(cursor != null){
            if(cursor.count > 0){
                cursor.moveToFirst()
                val columnIndex: Int = cursor.getColumnIndex(projection[0])
                do{
                    val word: String = cursor.getString(columnIndex)
                    mTextView?.append("$word\n")
                } while (cursor.moveToNext())
            }
            else{
                Log.d(TAG, "onClickDisplayEntries: No data returned.")
                mTextView?.append("No data returned.\n")
            }
            cursor.close()
        }
        else{
            Log.d(TAG, "onClickDisplayEntries: Cursor is null.")
            mTextView?.append("Cursor is null.\n")
        }

        /*
        Log.d(TAG, "Yay, I was clicked!")

        when(view.id){
            R.id.button_display_all ->
                Log.d(TAG, "Yay, ${R.id.button_display_all} was clicked!")

            R.id.button_display_first ->
                Log.d(TAG, "Yay, ${R.id.button_display_first} was clicked!")

            else ->
                Log.d(TAG, "Error. This should never happen.")

        }
        mTextView = findViewById(R.id.textview)

         */
    }
}
