package mx.unam.firebasebasico.presentation.home

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import mx.unam.firebasebasico.presentation.model.Artist
import mx.unam.firebasebasico.ui.theme.Black
import org.intellij.lang.annotations.JdkConstants.HorizontalAlignment

//@Preview
@Composable
fun HomeScreen(viewmodel:HomeViewmodel = HomeViewmodel()){

    val artists:State<List<Artist>> = viewmodel.artist.collectAsState()

    Column(Modifier.fillMaxSize().background(Black)) {
        Text("Popular artist", color = Color.White,
            fontWeight = FontWeight.Bold, fontSize = 30.sp,
            modifier = Modifier.padding(24.dp)
        )

//        val artists = emptyList<Artist>()

        LazyRow {
            items(artists.value){
                ArtistItem(it)
            }
        }
    }

}

@Composable
fun ArtistItem(artist: Artist){
    Column (horizontalAlignment = Alignment.CenterHorizontally){
        AsyncImage(
            modifier = Modifier.size(100.dp).clip(CircleShape),
            model = artist.image,
            contentDescription = "Artist image"
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = artist.name.orEmpty(), color = Color.White)
    }
}

@Preview
@Composable
fun ArtistItemPreview(){
    val artist = Artist(
        "Pepe",
        "El mejor",
        "https://nupec.com/wp-content/uploads/2022/02/cat-watching-2021-08-26-15-42-24-utc.jpg",
    )
    ArtistItem(artist)
}









/*
fun createArtist(db: FirebaseFirestore){
    val random  = (1 .. 10000).random()
    val artist = Artist(name = "Random $random", numberOfSongs = random)
    db.collection("artists")
        .add(artist)
        .addOnSuccessListener {
            Log.i("Aris", "SUCCESS")
        }
        .addOnFailureListener {
            Log.i("Aris", "FAILURE")
        }
        .addOnCompleteListener {
            Log.i("Aris", "COMPLETE")
        }

}*/
