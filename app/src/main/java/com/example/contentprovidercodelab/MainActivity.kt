package com.example.contentprovidercodelab


import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {

    private lateinit var mTextView: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mTextView = findViewById(R.id.textview)
        findViewById<Button>(R.id.button_display_all).setOnClickListener {
            onCLickDisplayEntries(it)
        }
        findViewById<Button>(R.id.button_display_first).setOnClickListener {
            onCLickDisplayEntries(it)
        }


    }

    fun onCLickDisplayEntries(view: View) {
        Log.d(TAG, "onCLickDisplayEntries: ")
        var btnDisplayALl = R.id.button_display_all
        var btnDisplayFirst = R.id.button_display_first
        val queryUri = Contract.CONTENT_URI.toString()
        // Only get words. String[]
        val projection = arrayOf(Contract.CONTENT_PATH)
        var selectionClause: String?
        var selectionArgs: Array<String>?
        // For this example, accept the order returned by the response.
        val sortOrder: String? = null


        when (view.id) {
            btnDisplayALl -> {
                selectionClause = null
                selectionArgs = arrayOf("")
            }
            btnDisplayFirst -> {
                selectionClause = Contract.WORD_ID + " = ?"
                selectionArgs = arrayOf("0")
            }
            else -> {
                selectionClause = null
                selectionArgs = arrayOf("")
            }
        }


        val cursor: Cursor? = contentResolver.query(
            Uri.parse(queryUri),
            projection,
            selectionClause,
            selectionArgs,
            sortOrder
        )

        if (cursor != null) {
            if (cursor.count > 0) {
                cursor.moveToFirst()
                val columnIndex = cursor.getColumnIndex(projection[0])
                do {
                    val word = cursor.getString(columnIndex)
                    mTextView.append(
                        """
                    $word
                    
                    """.trimIndent()
                    )
                } while (cursor.moveToNext())
            } else {
                Log.d(TAG, "onClickDisplayEntries " + "No data returned.")
                mTextView.append(
                    """
                No data returned.
                
                """.trimIndent()
                )
            }
            cursor.close()
        } else {
            Log.d(TAG, "onClickDisplayEntries " + "Cursor is null.")
            mTextView.append(
                """
            Cursor is null.
            
            """.trimIndent()
            )
        }

    }

}