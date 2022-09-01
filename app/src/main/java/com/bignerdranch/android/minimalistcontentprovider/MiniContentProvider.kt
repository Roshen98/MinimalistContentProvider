package com.bignerdranch.android.minimalistcontentprovider

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.database.MatrixCursor
import android.net.Uri
import android.util.Log
import android.widget.Toast
import java.lang.Integer.parseInt


private const val TAG = "MiniContentProvider"

class MiniContentProvider: ContentProvider(){
    private lateinit var mData: Array<String>
    private var sUriMatcher: UriMatcher = UriMatcher(UriMatcher.NO_MATCH)

    override fun onCreate(): Boolean {
        initializeUriMatching()
        val context = context
        mData = context!!.resources.getStringArray(R.array.words)
        return true
    }

    private fun initializeUriMatching(){
        sUriMatcher.addURI(Contract.AUTHORITY, "${Contract.CONTENT_PATH}/#", 1)
        sUriMatcher.addURI(Contract.AUTHORITY, Contract.CONTENT_PATH, 0)
    }


    override fun query(
        p0: Uri,
        p1: Array<out String>?,
        p2: String?,
        p3: Array<out String>?,
        p4: String?,
    ): Cursor {
        var id: Int
        when(sUriMatcher.match(p0)){
            0 -> {
                id = Contract.ALL_ITEMS
                if (p2 != null) {
                    id = p3!![0].toInt()
                }
            }

            1 -> id = p0.lastPathSegment?.let { parseInt(it) }!!

            UriMatcher.NO_MATCH -> {
                Log.d(TAG, "NO MATCH FOR THIS URI IN SCHEME.")
                id = -1
            }

            else -> {
                Log.d(TAG, "INVALID URI - URI NOT RECOGNIZED.")
                id = -1
            }
        }
        Log.d(TAG, "query: $id")
        return populateCursor(id)
    }

    private fun populateCursor(id: Int): Cursor {
        val cursor = MatrixCursor(arrayOf(Contract.CONTENT_PATH))
        if(id == Contract.ALL_ITEMS){
            for(element in mData){
                cursor.addRow(arrayOf(element))
            }
        }
        else if(id >= 0){
            val word = mData[id]
            cursor.addRow(arrayOf(word))
        }

        return cursor
    }

    override fun getType(p0: Uri): String? {
        return when(sUriMatcher.match(p0)){
            0 -> Contract.MULTIPLE_RECORD_MIME_TYPE
            1 -> Contract.SINGLE_RECORD_MIME_TYPE
            else -> return null
        }
    }

    override fun insert(p0: Uri, p1: ContentValues?): Uri? {
        Toast.makeText(context, "word inserted!", Toast.LENGTH_SHORT).show()
        Log.d(TAG, "Not implemented: update uri: $p0")
        return context!!.contentResolver.insert(p0,p1)
    }

    override fun delete(p0: Uri, p1: String?, p2: Array<out String>?): Int {
        Toast.makeText(context, "word deleted!", Toast.LENGTH_SHORT).show()
        Log.d(TAG, "Not implemented: update uri: $p0")
        return context!!.contentResolver.delete(p0,p1,p2)
    }

    override fun update(p0: Uri, p1: ContentValues?, p2: String?, p3: Array<out String>?): Int {
        Toast.makeText(context, "word updated!", Toast.LENGTH_SHORT).show()
        Log.d(TAG, "Not implemented: update uri: $p0")
        return context!!.contentResolver.update(p0,p1,p2,p3)

    }
}