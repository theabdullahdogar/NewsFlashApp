package com.example.madquiz.ui.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.BrokenImage
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.OpenInBrowser
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.SubcomposeAsyncImage
import com.example.madquiz.ui.theme.*
import com.example.madquiz.ui.viewmodel.NewsViewModel

@Composable
fun DetailScreen(
    viewModel: NewsViewModel,
    onBackClick: () -> Unit
) {
    val article = viewModel.selectedArticle.collectAsState().value ?: run {
        onBackClick(); return
    }
    val context = LocalContext.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkBg)
    ) {
        Column(modifier = Modifier.verticalScroll(rememberScrollState())) {

            // ── Hero image ─────────────────────────────────────────────
            Box(modifier = Modifier.fillMaxWidth().height(280.dp)) {
                SubcomposeAsyncImage(
                    model              = article.image,
                    contentDescription = article.title,
                    contentScale       = ContentScale.Crop,
                    modifier           = Modifier.fillMaxSize(),
                    loading = {
                        Box(
                            Modifier.fillMaxSize().background(SurfaceDark),
                            contentAlignment = Alignment.Center
                        ) { CircularProgressIndicator(color = NewsRed) }
                    },
                    error = {
                        Box(
                            Modifier.fillMaxSize().background(SurfaceDark),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(Icons.Default.BrokenImage, null, tint = TextSecondary, modifier = Modifier.size(56.dp))
                        }
                    }
                )
                // Gradient over image bottom
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(160.dp)
                        .align(Alignment.BottomCenter)
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(Color.Transparent, DarkBg)
                            )
                        )
                )
            }

            // ── Article body card ───────────────────────────────────────
            Surface(
                modifier = Modifier.fillMaxWidth(),
                color    = CardBg,
                shape    = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
            ) {
                Column(modifier = Modifier.padding(24.dp)) {

                    // Source chip
                    Surface(
                        shape = RoundedCornerShape(6.dp),
                        color = NewsRed
                    ) {
                        Text(
                            text     = article.source.name,
                            color    = Color.White,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 5.dp)
                        )
                    }

                    Spacer(Modifier.height(14.dp))

                    // Title
                    Text(
                        text       = article.title,
                        color      = TextPrimary,
                        fontSize   = 22.sp,
                        fontWeight = FontWeight.ExtraBold,
                        lineHeight = 30.sp
                    )

                    Spacer(Modifier.height(12.dp))

                    // Date row
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Icon(
                            imageVector        = Icons.Default.CalendarToday,
                            contentDescription = null,
                            tint               = TextSecondary,
                            modifier           = Modifier.size(14.dp)
                        )
                        Text(
                            text     = formatDate(article.publishedAt),
                            color    = TextSecondary,
                            fontSize = 13.sp
                        )
                    }

                    HorizontalDivider(
                        modifier  = Modifier.padding(vertical = 20.dp),
                        color     = DividerColor,
                        thickness = 1.dp
                    )

                    // Description
                    article.description?.let {
                        Text(
                            text       = it,
                            color      = TextPrimary,
                            fontSize   = 16.sp,
                            lineHeight  = 26.sp,
                            fontWeight = FontWeight.Medium
                        )
                        Spacer(Modifier.height(20.dp))
                    }

                    // Content preview
                    article.content?.let { raw ->
                        val clean = raw.replace(Regex("\\[\\+\\d+ chars\\]"), "").trim()
                        if (clean.isNotBlank()) {
                            Text(
                                text      = "Content Preview",
                                color     = NewsRed,
                                fontSize  = 13.sp,
                                fontWeight = FontWeight.SemiBold,
                                letterSpacing = 0.5.sp
                            )
                            Spacer(Modifier.height(8.dp))
                            Text(
                                text      = clean,
                                color     = TextSecondary,
                                fontSize  = 14.sp,
                                lineHeight = 22.sp
                            )
                            Spacer(Modifier.height(28.dp))
                        }
                    }

                    // Read Full Article button
                    Button(
                        onClick = {
                            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(article.url))
                            context.startActivity(intent)
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(54.dp),
                        shape  = RoundedCornerShape(14.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = NewsRed,
                            contentColor   = Color.White
                        )
                    ) {
                        Icon(Icons.Default.OpenInBrowser, null)
                        Spacer(Modifier.width(10.dp))
                        Text(
                            text       = "Read Full Article",
                            fontSize   = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Spacer(Modifier.height(16.dp))
                }
            }
        }

        // ── Floating back button ─────────────────────────────────────────
        Box(
            modifier = Modifier
                .padding(16.dp)
                .statusBarsPadding()
        ) {
            IconButton(
                onClick  = onBackClick,
                modifier = Modifier
                    .size(42.dp)
                    .clip(CircleShape)
                    .background(Color(0xAA000000))
            ) {
                Icon(
                    imageVector        = Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    tint               = Color.White
                )
            }
        }
    }
}

private fun formatDate(raw: String): String = try {
    val input  = java.text.SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", java.util.Locale.getDefault())
    val output = java.text.SimpleDateFormat("MMMM d, yyyy • HH:mm", java.util.Locale.getDefault())
    val date   = input.parse(raw)
    if (date != null) output.format(date) else raw
} catch (e: Exception) { raw }
