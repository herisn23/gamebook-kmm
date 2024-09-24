package cz.roldy.gb

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import compose.view.StoryGameView
import core.remember.getValue
import core.remember.lazyRemember
import core.story.loadStories
import core.story.loadStory

@Composable
fun DevStoryScreen() {
    val story by lazyRemember {
        loadStory(loadStories().first { it.id == "dev_story" })
    }
    story?.let {
        Text(it.metadata.id)
        Button(onClick = { println("Hello") }) {
            Text("Hello")
        }
    }
}