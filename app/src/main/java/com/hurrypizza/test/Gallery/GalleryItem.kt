package com.hurrypizza.test.Gallery

import android.graphics.Bitmap
import com.hurrypizza.test.SecondFragmentGallery

data class GalleryItem (
    var type : Int, // 0 for file descriptor, 1 for directory, 2 for bitmap file
    var img : Int?,
    var bitmap: Bitmap?,
    var dirName : String?,
    var frag : SecondFragmentGallery?
)