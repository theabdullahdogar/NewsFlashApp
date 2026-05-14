package com.example.madquiz.ui.screens

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Article
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.madquiz.ui.theme.DarkBg
import com.example.madquiz.ui.theme.NewsRed
import com.example.madquiz.ui.theme.NewsRedDark
import com.example.madquiz.ui.theme.TextSecondary
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun SplashScreen(onFinished: () -> Unit) {
    val scale  = remember { Animatable(0.4f) }
    val alpha  = remember { Animatable(0f) }
    val tagAlpha = remember { Animatable(0f) }
    val scope  = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        scope.launch {
            scale.animateTo(1f, tween(700, easing = FastOutSlowInEasing))
        }
        scope.launch {
            alpha.animateTo(1f, tween(600))
        }
        delay(500)
        tagAlpha.animateTo(1f, tween(600))
        delay(1200)
        onFinished()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.radialGradient(
                    colors  = listOf(Color(0xFF1A0A0A), DarkBg),
                    radius  = 1200f
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Logo circle
            Box(
                modifier = Modifier
                    .scale(scale.value)
                    .size(110.dp)
                    .clip(CircleShape)
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(NewsRed, NewsRedDark)
                        )
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector        = Icons.Default.Article,
                    contentDescription = null,
                    tint               = Color.White,
                    modifier           = Modifier.size(56.dp)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // App name
            Text(
                text       = "NewsFlash",
                fontSize   = 42.sp,
                fontWeight = FontWeight.ExtraBold,
                color      = Color.White,
                modifier   = Modifier.alpha(alpha.value),
                letterSpacing = 1.sp
            )

            // Tagline
            Text(
                text      = "Stay ahead of the headlines",
                fontSize  = 15.sp,
                color     = TextSecondary,
                modifier  = Modifier.alpha(tagAlpha.value),
                textAlign = TextAlign.Center
            )
        }

        // Bottom powered-by text
        Text(
            text     = "Powered by GNews",
            fontSize = 12.sp,
            color    = TextSecondary.copy(alpha = 0.5f),
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 40.dp)
                .alpha(tagAlpha.value)
        )
    }
}
