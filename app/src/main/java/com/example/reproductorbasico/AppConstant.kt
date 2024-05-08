package com.example.reproductorbasico

data class Song(
    val title: String,
    val audioResId: Int,
    val imageResId: Int,
){}
 class AppConstant{
     companion object{

         const val LOG_MAIN_ACTIVITY = "MainActivityReproductor"
         const val MEDIA_PLAYER_POSITION = "Position"
         const val MEDIA_PLAYER_SONG_INDEX = "IndiceCancion"
         const val MEDIA_PLAYER_PLAYSTATUS = "Status"

         val song= listOf(
             Song("Pretty Please Remix - Dua Lipa",R.raw.pp_remix,R.drawable.pretty_please),
             Song("SummerTime Sadness Remix - Lana del Rey",R.raw.lr_ss,R.drawable.lr_ss),
             Song("In the end Remix - Linkin Park", R.raw.lp_in_the_end_remix,R.drawable.lp_in_the_emd_remix),
         )
     }
 }