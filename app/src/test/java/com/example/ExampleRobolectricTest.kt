package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.data.local.entity.NewsEntity
import com.example.util.DateUtils
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class ExampleRobolectricTest {

    @Test
    fun `read string from context`() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val appName = context.getString(R.string.app_name)
        assertEquals("AniFlow", appName)
    }

    @Test
    fun `verify news entity to domain mapping`() {
        val entity = NewsEntity(
            id = "test_1",
            title = "Frieren Season 2",
            author = "Redação",
            source = "Crunchyroll",
            publishedAtMillis = System.currentTimeMillis(),
            category = "Anime",
            imageUrl = "https://example.com/image.jpg",
            articleUrl = "https://example.com/article",
            summary = "Sinopse da matéria",
            isBookmarked = false
        )
        val domain = entity.toDomain()
        assertEquals(entity.id, domain.id)
        assertEquals(entity.title, domain.title)
        assertEquals(entity.category, domain.category)
    }

    @Test
    fun `verify date utils relative time formatting`() {
        val now = System.currentTimeMillis()
        val oneHourAgo = now - 3600 * 1000L
        val formatted = DateUtils.formatRelativeTime(oneHourAgo)
        assertNotNull(formatted)
        assertEquals("há 1h", formatted)
    }
}
