package com.example.madquiz.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.madquiz.data.model.Article
import com.example.madquiz.data.repository.NewsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed class NewsUiState {
    object Loading : NewsUiState()
    data class Success(val articles: List<Article>) : NewsUiState()
    data class Error(val message: String) : NewsUiState()
}

class NewsViewModel : ViewModel() {

    // ── TODO: paste your gnews.io API key here ──────────────────────────────
    private val apiKey = "b70d0d8ae1692fb480d28eb15ba053af"
    // ────────────────────────────────────────────────────────────────────────

    private val repository = NewsRepository()

    private val _uiState = MutableStateFlow<NewsUiState>(NewsUiState.Loading)
    val uiState: StateFlow<NewsUiState> = _uiState

    private val _selectedArticle = MutableStateFlow<Article?>(null)
    val selectedArticle: StateFlow<Article?> = _selectedArticle

    private val _selectedCountry = MutableStateFlow("us")
    val selectedCountry: StateFlow<String> = _selectedCountry

    init { loadNews() }

    fun loadNews(country: String = _selectedCountry.value) {
        _uiState.value = NewsUiState.Loading
        viewModelScope.launch {
            val result = repository.getHeadlines(country = country, apiKey = apiKey)
            _uiState.value = if (result.isSuccess) {
                NewsUiState.Success(result.getOrDefault(emptyList()))
            } else {
                val msg = result.exceptionOrNull()?.message ?: "Unknown error"
                NewsUiState.Error(msg)
            }
        }
    }

    fun selectCountry(code: String) {
        _selectedCountry.value = code
        loadNews(code)
    }

    fun selectArticle(article: Article) {
        _selectedArticle.value = article
    }
}
