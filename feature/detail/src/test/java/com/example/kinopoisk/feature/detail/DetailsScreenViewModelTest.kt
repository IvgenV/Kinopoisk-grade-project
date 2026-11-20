package com.example.kinopoisk.feature.detail

import androidx.paging.PagingData
import com.example.core.data.model.dto.FilmFilter
import com.example.core.data.model.dto.FilmImages
import com.example.core.data.model.dto.FilmsByFiltersDomain
import com.example.core.data.model.dto.PremierItemDto
import com.example.core.data.model.response.FilmDetailResponse
import com.example.core.data.repository.films.FilmsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class DetailsScreenViewModelTest {

    private val testDispatcher = UnconfinedTestDispatcher()
    private val repository = FakeFilmsRepository()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `ui state is default when repository emits nothing`() = runTest(testDispatcher) {
        repository.filmDetailFlow = emptyFlow()
        repository.filmPosterFlow = emptyFlow()

        val viewModel = createViewModel()

        assertEquals(DetailsScreenState(), viewModel.uiState.value)
    }

    @Test
    fun `film details and posters are mapped into ui state`() = runTest(testDispatcher) {
        repository.filmDetailFlow = flowOf(
            FilmDetailResponse(
                coverUrl = "https://cover",
                posterUrl = "https://poster",
                nameRu = "Побег из Шоушенка",
                logoUrl = "https://logo",
                reviewsCount = 120,
                ratingKinopoisk = 9.1,
                ratingKinopoiskVoteCount = 1_000_000,
                nameOriginal = "The Shawshank Redemption",
                startYear = 1994,
                year = 1995,
                filmLength = 142,
                genres = listOf(
                    FilmDetailResponse.Genre("драма"),
                    FilmDetailResponse.Genre("криминал"),
                ),
                countries = listOf(FilmDetailResponse.Country("США")),
                description = "Описание фильма",
            )
        )
        repository.filmPosterFlow = flowOf(
            listOf(
                FilmImages.FilmsItem(imageUrl = "https://still1"),
                FilmImages.FilmsItem(imageUrl = "https://still2"),
            )
        )

        val viewModel = createViewModel()

        assertEquals(
            DetailsScreenState(
                coverUrl = "https://cover",
                logoUrl = "https://logo",
                title = "Побег из Шоушенка",
                reviewsCount = 120,
                ratingKinopoisk = 9.1,
                ratingKinopoiskVoteCount = 1_000_000,
                nameOriginal = "The Shawshank Redemption",
                country = listOf("США"),
                filmDuration = "2 ч 22 м",
                description = "Описание фильма",
                year = 1994,
                genres = listOf("драма", "криминал"),
                posters = listOf("https://still1", "https://still2"),
            ),
            viewModel.uiState.value,
        )
    }

    @Test
    fun `null film fields fall back to defaults`() = runTest(testDispatcher) {
        repository.filmDetailFlow = flowOf(FilmDetailResponse())

        val viewModel = createViewModel()

        assertEquals(DetailsScreenState(), viewModel.uiState.value)
    }

    @Test
    fun `cover url falls back to poster url`() = runTest(testDispatcher) {
        repository.filmDetailFlow = flowOf(
            FilmDetailResponse(coverUrl = null, posterUrl = "https://poster")
        )

        val viewModel = createViewModel()

        assertEquals("https://poster", viewModel.uiState.value.coverUrl)
    }

    @Test
    fun `year falls back to year when start year is null`() = runTest(testDispatcher) {
        repository.filmDetailFlow = flowOf(
            FilmDetailResponse(startYear = null, year = 2000)
        )

        val viewModel = createViewModel()

        assertEquals(2000, viewModel.uiState.value.year)
    }

    @Test
    fun `genres are filtered limited to two and null names become empty`() = runTest(testDispatcher) {
        repository.filmDetailFlow = flowOf(
            FilmDetailResponse(
                genres = listOf(
                    null,
                    FilmDetailResponse.Genre("драма"),
                    FilmDetailResponse.Genre(null),
                    FilmDetailResponse.Genre("комедия"),
                )
            )
        )

        val viewModel = createViewModel()

        assertEquals(listOf("драма", ""), viewModel.uiState.value.genres)
    }

    @Test
    fun `countries are filtered limited to three and null names become empty`() = runTest(testDispatcher) {
        repository.filmDetailFlow = flowOf(
            FilmDetailResponse(
                countries = listOf(
                    null,
                    FilmDetailResponse.Country("США"),
                    FilmDetailResponse.Country(null),
                    FilmDetailResponse.Country("Россия"),
                    FilmDetailResponse.Country("Франция"),
                )
            )
        )

        val viewModel = createViewModel()

        assertEquals(listOf("США", "", "Россия"), viewModel.uiState.value.country)
    }

    @Test
    fun `posters with null image url are skipped`() = runTest(testDispatcher) {
        repository.filmPosterFlow = flowOf(
            listOf(
                FilmImages.FilmsItem(imageUrl = "https://still1"),
                FilmImages.FilmsItem(imageUrl = null),
                FilmImages.FilmsItem(imageUrl = "https://still2"),
            )
        )

        val viewModel = createViewModel()

        assertEquals(listOf("https://still1", "https://still2"), viewModel.uiState.value.posters)
    }

    @Test
    fun `film duration is formatted from minutes`() = runTest(testDispatcher) {
        val cases = listOf(142 to "2 ч 22 м", 120 to "2 ч", 45 to "45 м")

        cases.forEach { (minutes, expected) ->
            repository.filmDetailFlow = flowOf(FilmDetailResponse(filmLength = minutes))

            val viewModel = createViewModel()

            assertEquals(expected, viewModel.uiState.value.filmDuration)
        }
    }

    @Test
    fun `ui state stays default when detail flow fails`() = runTest(testDispatcher) {
        repository.filmDetailFlow = flow { error("Network error") }

        val viewModel = createViewModel()

        assertEquals(DetailsScreenState(), viewModel.uiState.value)
    }

    @Test
    fun `ui state stays default when poster flow fails`() = runTest(testDispatcher) {
        repository.filmPosterFlow = flow { error("Network error") }

        val viewModel = createViewModel()

        assertEquals(DetailsScreenState(), viewModel.uiState.value)
    }

    @Test
    fun `getDetailFilmInfo reloads film data`() = runTest(testDispatcher) {
        repository.filmDetailFlow = flowOf(FilmDetailResponse(nameRu = "Первый"))
        val viewModel = createViewModel()
        assertEquals("Первый", viewModel.uiState.value.title)

        repository.filmDetailFlow = flowOf(FilmDetailResponse(nameRu = "Второй"))
        viewModel.getDetailFilmInfo()

        assertEquals("Второй", viewModel.uiState.value.title)
    }

    @Test
    fun `repository is queried with film id and still poster type`() = runTest(testDispatcher) {
        createViewModel(kinopoiskId = 301)

        assertEquals(301, repository.requestedDetailId)
        assertEquals(301, repository.requestedPosterId)
        assertEquals("STILL", repository.requestedPosterType)
    }

    @Test
    fun `ui state is updated only when both flows emit`() = runTest(testDispatcher) {
        val detailFlow = MutableSharedFlow<FilmDetailResponse>()
        val posterFlow = MutableSharedFlow<List<FilmImages.FilmsItem>>()
        repository.filmDetailFlow = detailFlow
        repository.filmPosterFlow = posterFlow

        val viewModel = createViewModel()
        assertEquals(DetailsScreenState(), viewModel.uiState.value)

        detailFlow.emit(FilmDetailResponse(nameRu = "Фильм"))
        advanceUntilIdle()
        assertEquals(DetailsScreenState(), viewModel.uiState.value)

        posterFlow.emit(listOf(FilmImages.FilmsItem(imageUrl = "https://still")))
        advanceUntilIdle()
        assertEquals(
            DetailsScreenState(title = "Фильм", posters = listOf("https://still")),
            viewModel.uiState.value,
        )
    }

    private fun createViewModel(kinopoiskId: Int = FILM_ID): DetailsScreenViewModel =
        DetailsScreenViewModel(kinopoiskId, repository)

    private class FakeFilmsRepository : FilmsRepository {

        var filmDetailFlow: Flow<FilmDetailResponse> = flowOf(FilmDetailResponse())
        var filmPosterFlow: Flow<List<FilmImages.FilmsItem>> = flowOf(emptyList())

        var requestedDetailId: Int? = null
        var requestedPosterId: Int? = null
        var requestedPosterType: String? = null

        override suspend fun getFilmDetail(id: Int): Flow<FilmDetailResponse> {
            requestedDetailId = id
            return filmDetailFlow
        }

        override suspend fun getFilmPoster(id: Int, type: String): Flow<List<FilmImages.FilmsItem>> {
            requestedPosterId = id
            requestedPosterType = type
            return filmPosterFlow
        }

        override fun getFilmsCollections(type: String): Flow<PagingData<FilmsByFiltersDomain.ItemDomain>> = TODO()

        override suspend fun getFilmsPremieres(year: Int, month: String): List<PremierItemDto> = TODO()

        override fun getFilmImagesPaging(filmId: Int, type: String): Flow<PagingData<FilmImages.FilmsItem>> = TODO()

        override suspend fun getFilmFilters(): FilmFilter = TODO()

        override fun getFilms(queryParams: Map<String, Any>): Flow<PagingData<FilmsByFiltersDomain.ItemDomain>> = TODO()
    }

    private companion object {
        const val FILM_ID = 42
    }
}