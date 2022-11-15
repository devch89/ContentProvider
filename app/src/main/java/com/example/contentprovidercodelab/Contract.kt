package com.example.contentprovidercodelab

import android.net.Uri

class Contract private constructor() {

    companion object {
        val AUTHORITY: String = "com.android.example.minimalistcontentprovider.provider"
        val CONTENT_PATH: String = "words"
        val WORD_ID: String = "id"
        val CONTENT_URI: Uri = Uri.parse("content://" + AUTHORITY + "/" + CONTENT_PATH)
        val ALL_ITEMS: Int = -2
        val SINGLE_RECORD_MIME_TYPE: String =
            "vnd.android.cursor.item/vnd.com.example.provider.words"
        val MULTIPLE_RECORD_MIME_TYPE: String =
            "vnd.android.cursor.dir/vnd.com.example.provider.words"
    }
}