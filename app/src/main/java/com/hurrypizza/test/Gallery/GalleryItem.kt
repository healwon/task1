package com.hurrypizza.test.Gallery

import com.hurrypizza.test.SecondFragmentGallery

data class GalleryItem (
    var type : Int,
    var img : Int,
    var dirName : String?,
    var frag : SecondFragmentGallery?
)