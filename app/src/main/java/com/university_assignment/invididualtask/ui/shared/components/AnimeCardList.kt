package com.university_assignment.invididualtask.ui.shared.components

import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.university_assignment.invididualtask.data.models.anime.BriefAnimeModel

@Composable
fun AnimeCardList(
    briefAnimes: List<BriefAnimeModel>,
    onSelectCallback: (anime: BriefAnimeModel) -> Unit) {
    LazyRow(
        modifier = Modifier.fillMaxWidth()
    ) {
        items(briefAnimes) { anime ->
            BriefAnimeCard(anime) {onSelectCallback(anime)}
            Spacer(Modifier.width(15.dp))
        }
    }
}