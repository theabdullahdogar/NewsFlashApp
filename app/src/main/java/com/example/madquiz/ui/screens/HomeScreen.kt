package com.example.madquiz.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BrokenImage
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.WifiOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.SubcomposeAsyncImage
import com.example.madquiz.data.model.Article
import com.example.madquiz.data.model.countryList
import com.example.madquiz.ui.theme.*
import com.example.madquiz.ui.viewmodel.NewsUiState
import com.example.madquiz.ui.viewmodel.NewsViewModel
import java.text.SimpleDateFormat
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: NewsViewModel,
    onArticleClick: (Article) -> Unit
) {
    val uiState       by viewModel.uiState.collectAsState()
    val selectedCountry by viewModel.selectedCountry.collectAsState()

    Scaffold(
        containerColor = DarkBg,
        topBar = {
            TopAppBar(
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(32.dp)
                                .clip(CircleShape)
                                .background(NewsRed),
                            contentAlignment = Alignment.Center
                        ) {
                            Text("N", color = Color.White, fontWeight = FontWeight.ExtraBold, fontSize = 16.sp)
                        }
                        Spacer(Modifier.width(10.dp))
                        Text(
                            text       = "NewsFlash",
                            color      = Color.White,
                            fontWeight = FontWeight.ExtraBold,
                            fontSize   = 22.sp,
                            letterSpacing = 0.5.sp
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { viewModel.loadNews() }) {
                        Icon(
                            imageVector        = Icons.Default.Refresh,
                            contentDescription = "Refresh",
                            tint               = NewsRed
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = CardBg
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // ── Country Chips ────────────────────────────────────────────
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(CardBg)
                    .horizontalScroll(rememberScrollState())
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                countryList.forEach { country ->
                    val selected = selectedCountry == country.code
                    Surface(
                        onClick = { viewModel.selectCountry(country.code) },
                        shape   = RoundedCornerShape(50),
                        color   = if (selected) NewsRed else SurfaceDark,
                        modifier = Modifier.height(36.dp)
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 14.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Text(country.flag, fontSize = 16.sp)
                            Text(
                                text       = country.name,
                                color      = if (selected) Color.White else TextSecondary,
                                fontSize   = 13.sp,
                                fontWeight = if (selected) FontWeight.SemiBold else FontWeight.Normal
                            )
                        }
                    }
                }
            }

            HorizontalDivider(color = DividerColor, thickness = 1.dp)

            // ── Content ──────────────────────────────────────────────────
            when (val state = uiState) {
                is NewsUiState.Loading -> LoadingView()

                is NewsUiState.Error   -> ErrorView(
                    message   = state.message,
                    onRetry   = { viewModel.loadNews() }
                )

                is NewsUiState.Success -> {
                    LazyColumn(
                        contentPadding      = PaddingValues(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        items(state.articles) { article ->
                            ArticleCard(
                                article  = article,
                                onClick  = { onArticleClick(article) }
                            )
                        }
                        item { Spacer(Modifier.height(8.dp)) }
                    }
                }
            }
        }
    }
}

@Composable
private fun ArticleCard(article: Article, onClick: () -> Unit) {
    Card(
        onClick   = onClick,
        shape     = RoundedCornerShape(16.dp),
        colors    = CardDefaults.cardColors(containerColor = CardBg),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        modifier  = Modifier.fillMaxWidth()
    ) {
        Column {
            // Image with gradient overlay
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            ) {
                SubcomposeAsyncImage(
                    model             = article.image,
                    contentDescription = article.title,
                    contentScale      = ContentScale.Crop,
                    modifier          = Modifier.fillMaxSize(),
                    loading = {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(SurfaceDark),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator(color = NewsRed, modifier = Modifier.size(32.dp))
                        }
                    },
                    error = {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(SurfaceDark),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(Icons.Default.BrokenImage, null, tint = TextSecondary, modifier = Modifier.size(40.dp))
                        }
                    }
                )
                // Gradient at bottom of image
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(80.dp)
                        .align(Alignment.BottomCenter)
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(Color.Transparent, Color(0xCC000000))
                            )
                        )
                )
                // Source badge
                Surface(
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .padding(10.dp),
                    shape  = RoundedCornerShape(6.dp),
                    color  = NewsRed
                ) {
                    Text(
                        text     = article.source.name,
                        color    = Color.White,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }

            // Text content
            Column(modifier = Modifier.padding(14.dp)) {
                Text(
                    text       = article.title,
                    color      = TextPrimary,
                    fontSize   = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    maxLines   = 2,
                    overflow   = TextOverflow.Ellipsis,
                    lineHeight = 22.sp
                )

                Spacer(Modifier.height(8.dp))

                article.description?.let { desc ->
                    Text(
                        text     = desc,
                        color    = TextSecondary,
                        fontSize = 13.sp,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        lineHeight = 18.sp
                    )
                    Spacer(Modifier.height(10.dp))
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment     = Alignment.CenterVertically
                ) {
                    Text(
                        text     = formatDate(article.publishedAt),
                        color    = TextSecondary,
                        fontSize = 11.sp
                    )
                    Surface(
                        shape = RoundedCornerShape(4.dp),
                        color = SurfaceDark
                    ) {
                        Text(
                            text     = "Read more →",
                            color    = NewsRed,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.SemiBold,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun LoadingView() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(16.dp)) {
            CircularProgressIndicator(color = NewsRed, strokeWidth = 3.dp)
            Text("Fetching headlines…", color = TextSecondary, fontSize = 14.sp)
        }
    }
}

@Composable
private fun ErrorView(message: String, onRetry: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Icon(
                imageVector        = Icons.Default.WifiOff,
                contentDescription = null,
                tint               = NewsRed,
                modifier           = Modifier.size(72.dp)
            )
            Text(
                text       = "Something went wrong",
                color      = TextPrimary,
                fontSize   = 18.sp,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                text      = message,
                color     = TextSecondary,
                fontSize  = 13.sp,
                maxLines  = 3,
                overflow  = TextOverflow.Ellipsis
            )
            Button(
                onClick = onRetry,
                colors  = ButtonDefaults.buttonColors(containerColor = NewsRed),
                shape   = RoundedCornerShape(12.dp)
            ) {
                Icon(Icons.Default.Refresh, null, tint = Color.White)
                Spacer(Modifier.width(8.dp))
                Text("Try Again", color = Color.White, fontWeight = FontWeight.SemiBold)
            }
        }
    }
}

private fun formatDate(raw: String): String = try {
    val input  = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
    val output = SimpleDateFormat("MMM d, yyyy", Locale.getDefault())
    val date   = input.parse(raw)
    if (date != null) output.format(date) else raw
} catch (e: Exception) { raw }
