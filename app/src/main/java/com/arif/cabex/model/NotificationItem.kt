package com.arif.cabex.model

import android.graphics.Bitmap
import com.arif.cabex.helper.type.NotificationType

data class NotificationItem(
    var isRead: Boolean,
    var bitmap: Bitmap,
    var type: NotificationType,
)
