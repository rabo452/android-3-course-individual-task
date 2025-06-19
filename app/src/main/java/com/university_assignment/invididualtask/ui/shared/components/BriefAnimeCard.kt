package com.university_assignment.invididualtask.ui.shared.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.university_assignment.invididualtask.data.models.anime.BriefAnimeModel

@Composable
fun BriefAnimeCard(anime: BriefAnimeModel, onClick: () -> Unit) {
    Column(
        modifier = Modifier.width(150.dp).clickable { onClick() }
    ) {
        AsyncImage(
            model = anime.thumbnailUrl ,
            contentDescription = null,
            modifier = Modifier.width(150.dp).height(225.dp),
            contentScale = ContentScale.Crop
        )

        Text(
            anime.title,
            modifier = Modifier.padding(top = 15.dp,).fillMaxWidth(),
            textAlign = TextAlign.Start,
            maxLines = 1
        )
    }
}