package com.example.unspoken.presentation.home.main_feed

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.unspoken.R
import com.example.unspoken.data.repository.MainRepository
import com.example.unspoken.domain.MainFeedItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.ticker
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainFeedViewModel @Inject constructor(
    private val mainRepository: MainRepository
): ViewModel() {

    private val _uiState = MutableStateFlow(MainFeedUiState())
    val uiState = _uiState.asStateFlow()

    init {
//        viewModelScope.launch {
//            mainRepository.getMainFeeds().let { feed ->
//                _uiState.update {
//                    it.copy(mainFeed = feed)
//                }
//            }
//        }
    }

    fun selectTrending(select: Boolean){
        _uiState.update {
            it.copy(selectedTrending = select)
        }
    }

}

data class MainFeedUiState(
    val mainFeed: List<MainFeedItem> = listOf(
        MainFeedItem.SimpleFeed(
            views = "3K",
            comments = "120",
            likes = "1.5k",
            date = "3h",
            authorId = "clankMerger",
            collegeId = "MIT",
            title = "Controlled diffusion model can change material properties in images",
            content = "“Alchemist” system adjusts the material attributes of specific objects within images to potentially modify video game models to fit different environments, fine-tune VFX, and diversify robotic training."
        ),
        MainFeedItem.SimpleFeed(
            views = "10K",
            comments = "345",
            likes = "2k",
            authorProfilePhoto = R.drawable.test_profile,
            date = "1d",
            authorId = "research_news",
            collegeId = "Stanford",
            title = "New AI tool generates realistic images of proteins",
            content = "Researchers have developed a new artificial intelligence (AI) tool that can generate realistic images of proteins. The tool, called ProteinMPNN, uses a type of neural network called a message passing neural network (MPNN) to learn the relationships between different parts of a protein molecule. This allows the tool to generate images of proteins that are both accurate and detailed."
        ),
        MainFeedItem.SimpleFeed(
            views = "100",
            comments = "32",
            likes = "11",
            authorProfilePhoto = R.drawable.test2,
            date = "1h",
            authorId = "career_services",
            collegeId = "UC Berkeley",
            title = "Need help with your job search? Attend our upcoming career fair!",
            content = "The UC Berkeley Career Services Center is hosting a career fair on March 8th from 10am to 4pm in the Student Union. This is a great opportunity to meet with potential employers and learn about job openings. We will have representatives from a variety of industries, including tech, finance, healthcare, and more. Dress professionally and bring your resume. We look forward to seeing you there!"
        ),
        MainFeedItem.SimpleFeed(
            views = "270",
            comments = "55",
            likes = "120",
            date = "5h",
            authorId = "alumni_news",
            collegeId = "Yale",
            title = "Yale alumna wins Nobel Prize in Chemistry",
            content = "Yale alumna Carolyn Bertozzi has been awarded the 2022 Nobel Prize in Chemistry for her work on the development of click chemistry and bioorthogonal chemistry. Bertozzi's research has led to the development of new drugs and treatments for a variety of diseases, including cancer and Alzheimer's disease."
        )
    ),
    val selectedTrending: Boolean = true,
    val statusList: List<Status> = listOf(
        Status(0, "RESULTS OUT", "little_pancake", R.drawable.test_profile, false),
        Status(1, "RESULTS OUT", "CuriousCat", R.drawable.test2, false),
        Status(2, "RESULTS OUT", "TechWizard", R.drawable.test3, false),
        Status(3, "RESULTS OUT", "BookwormOwl", R.drawable.test4, true),
        Status(4, "RESULTS OUT", "MusicMaestro", R.drawable.test5, true),
        Status(5, "RESULTS OUT", "NatureExplorer", R.drawable.test6, true),
    )
)

data class Status(
    val id: Int,
    val title: String,
    val userName: String,
    val userImage: Int,
    val seen: Boolean
)