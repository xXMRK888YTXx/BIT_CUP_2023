package com.xxmrk888ytxx.bit_cup_2023.detail.data.downloadImageManager

import android.annotation.SuppressLint
import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Context.RECEIVER_EXPORTED
import android.content.Intent
import android.content.IntentFilter
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.widget.Toast
import androidx.core.content.getSystemService
import com.xxmrk888ytxx.bit_cup_2023.R
import com.xxmrk888ytxx.bit_cup_2023.data.networkObserver.NetworkObserver
import com.xxmrk888ytxx.bit_cup_2023.detail.domain.downloadImageManager.DownloadImageManager
import com.xxmrk888ytxx.bit_cup_2023.domain.model.Image
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject


class DownloadImageManagerImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    private val networkObserver: NetworkObserver,
) : DownloadImageManager {

    private val downloadManager by lazy {
        context.getSystemService<DownloadManager>()!!
    }

    private val downloadStateBroadcastReceiver = object : BroadcastReceiver() {
        @SuppressLint("Range")
        override fun onReceive(context: Context, intent: Intent?) {
            if (intent?.action != DownloadManager.ACTION_DOWNLOAD_COMPLETE) return
            val downloadId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1)
            if (downloadId == -1L) return

            val query = DownloadManager.Query()
                .setFilterById(downloadId)

            val cursor: Cursor = downloadManager.query(query)

            if (cursor.moveToFirst()) {

                when (cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS))) {
                    DownloadManager.STATUS_SUCCESSFUL -> {
                        Toast.makeText(
                            context,
                            R.string.image_successfully_downloaded,
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    DownloadManager.STATUS_FAILED -> {
                        Toast.makeText(
                            context,
                            R.string.an_error_occurred_while_uploading_the_image,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

            }

        }
    }


    override suspend fun downloadImageInDownloadDir(image: Image) {
        if (!networkObserver.isNetworkAvailable.value) {
            withContext(Dispatchers.Main) {
                Toast.makeText(
                    context,
                    R.string.no_connection_download_image_is_impossible,
                    Toast.LENGTH_SHORT
                ).show()
            }

            return
        }

        val downloadImageRequest = DownloadManager.Request(Uri.parse(image.imageUrl))
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "${image.id}.jpeg")

        downloadManager.enqueue(downloadImageRequest)

        withContext(Dispatchers.Main) {
            Toast.makeText(
                context,
                context.getString(R.string.downloading_image),
                Toast.LENGTH_SHORT
            )
                .show()
        }
    }

    init {
        context.registerReceiver(
            downloadStateBroadcastReceiver,
            IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE),
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) RECEIVER_EXPORTED
            else 0
        )
    }
}