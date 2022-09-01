package com.bignerdranch.android.minimalistcontentprovider

import android.net.Uri

object Contract{

        const val AUTHORITY: String =
                "com.android.example.minimalistcontentprovider.provider"
               // "com.android.example.wordlistsqlwithcontentprovider.provider"

        const val CONTENT_PATH: String =
                "words"

        const val COUNT = "count"

        val CONTENT_URI: Uri =
                Uri.parse("content://$AUTHORITY/$CONTENT_PATH")

 //       val ROW_COUNT_URI =
   //             Uri.parse("content://$AUTHORITY/$CONTENT_PATH/$COUNT")

        const val ALL_ITEMS: Int = -2

        const val WORD_ID: String = "id"

        const val SINGLE_RECORD_MIME_TYPE: String =
                "vnd.android.cursor.item/vnd.com.example.provider.words"
        const val MULTIPLE_RECORD_MIME_TYPE: String =
                "vnd.android.cursor.dir/vnd.com.example.provider.words"
}