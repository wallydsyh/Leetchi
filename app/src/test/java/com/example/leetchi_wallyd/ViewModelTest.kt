package com.example.leetchi_wallyd

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.leetchi_wallyd.api.ApiHelper
import com.example.leetchi_wallyd.api.ApiService
import com.example.leetchi_wallyd.model.*
import com.example.leetchi_wallyd.repository.GiphyRepository
import com.example.leetchi_wallyd.util.Constant
import com.example.leetchi_wallyd.util.Resource
import com.example.leetchi_wallyd.viewModel.GiphyViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner


@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class ViewModelTest {

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @Mock
    private lateinit var apiService: ApiService

    @Mock
    private lateinit var giphyViewModel: GiphyViewModel

    @Mock
    private lateinit var listGif: ArrayList<Gif>

    @Mock
    private lateinit var apiUsersObserver: Observer<Resource<GiphyResponse>>

    private lateinit var list: GiphyResponse

    @Before
    fun setUp() {
        apiService = mock(ApiService::class.java)
        giphyViewModel = spy(GiphyViewModel(GiphyRepository(ApiHelper(apiService))))
        listGif.add(Gif("1", "title", GifImages(OriginalImage("", ""), Preview(""))))
        list = GiphyResponse(listGif)
    }

    @Test
    fun givenServerResponse200_whenFetch_shouldReturnSuccess() {
        testCoroutineRule.runBlockingTest {
            doReturn(emptyList<Gif>())
                .`when`(apiService)
                .getGifs(Constant.API_KEY, Constant.LIMIT)
            giphyViewModel.allGifs.observeForever(apiUsersObserver)
            (apiUsersObserver).onChanged(Resource.Success(list))
            verify(apiUsersObserver).onChanged(Resource.Success(list))
            giphyViewModel.allGifs.removeObserver(apiUsersObserver)

            apiService.getGifs(Constant.API_KEY, Constant.LIMIT)
            verify(apiService).getGifs(Constant.API_KEY, Constant.LIMIT)
        }
    }

    @After
    fun tearDown() {
    }

}