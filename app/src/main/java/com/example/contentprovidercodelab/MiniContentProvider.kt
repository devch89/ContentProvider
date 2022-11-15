package com.example.contentprovidercodelab


import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.database.MatrixCursor
import android.net.Uri
import android.util.Log


private const val TAG = "MiniContentProvider"

class MiniContentProvider : ContentProvider() {
    lateinit var mData: Array<String>
    private val sUriMatcher = UriMatcher(UriMatcher.NO_MATCH)

    override fun onCreate(): Boolean {
        val context = context
        mData = context?.resources?.getStringArray(R.array.words) as Array<String>
        initializeUriMatching()
        return true

    }

    override fun query(
        uri: Uri,
        projection: Array<out String>?,
        selection: String?,
        selectionArgs: Array<out String>?,
        sortOrder: String?
    ): Cursor? {
        var id = -1;
        when (sUriMatcher.match(uri)) {
            0 -> {
                // Matches URI to get all of the entries.
                id = Contract.ALL_ITEMS;
                // Look at the remaining arguments
                // to see whether there are constraints.
                // In this example, we only support getting
                //a specific entry by id. Not full search.
                // For a real-life app, you need error-catching code;
                // here we assume that the
                // value we need is actually in selectionArgs and valid.
                if (selection != null) {
                    id = selectionArgs?.get(0)?.toInt() ?: id
                }
            }


            1 -> {
                // The URI ends in a numeric value, which represents an id.
                // Parse the URI to extract the value of the last,
                // numeric part of the path,
                // and set the id to that value.
                id = uri.getLastPathSegment()?.toInt() ?: id
                // With a database, you would then use this value and
                // the path to build a query.
            }


            UriMatcher.NO_MATCH -> {
                // You should do some error handling here.
                Log.d(TAG, "NO MATCH FOR THIS URI IN SCHEME.");
                id = -1
            }

            else -> {
                // You should do some error handling here.
                Log.d(TAG, "INVALID URI - URI NOT RECOGNIZED.");
                id = -1;
            }

        }

        Log.d(TAG, "query: " + id);
        return populateCursor(id);
    }

    private fun populateCursor(id: Int): Cursor? {
        val cursor = MatrixCursor(arrayOf(Contract.CONTENT_PATH))
        // If there is a valid query, execute it and add the result to the cursor.
        if (id == Contract.ALL_ITEMS) {
            for (i in 0 until mData.size) {
                val word = mData[i]
                cursor.addRow(arrayOf<Any>(word))
            }
        } else if (id >= 0) {
            // Execute the query to get the requested word.
            val word = mData[id]
            // Add the result to the cursor.
            cursor.addRow(arrayOf<Any>(word))
        }
        return cursor
    }


    override fun getType(uri: Uri): String? {
        return when (sUriMatcher.match(uri)) {
            0 -> Contract.MULTIPLE_RECORD_MIME_TYPE
            1 -> Contract.SINGLE_RECORD_MIME_TYPE
            else ->                 // Alternatively, throw an exception.
                null
        }
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        Log.d(TAG, "insert: ")
        TODO("Not yet implemented")
    }


    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int {
        TODO("Not yet implemented")
        Log.d(TAG, "delete: ")
    }

    override fun update(
        uri: Uri,
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<out String>?
    ): Int {
        TODO("Not yet implemented")
        Log.d(TAG, "update: ")
    }

    private fun initializeUriMatching() {

        sUriMatcher.addURI(Contract.AUTHORITY, Contract.CONTENT_PATH + "/#", 1)
        sUriMatcher.addURI(Contract.AUTHORITY, Contract.CONTENT_PATH, 0)
    }
}