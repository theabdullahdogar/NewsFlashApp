# NewsFlash — News Headlines App

A modern Android news app built with **Jetpack Compose** that displays real-time top headlines from **59 countries** using the [GNews API](https://gnews.io).

---

## APK Download

The pre-built debug APK is generated automatically by GitHub Actions on every push to `main`.

**To download the APK:**

1. Go to the [Actions tab](https://github.com/theabdullahdogar/NewsFlashApp/actions)
2. Click the latest **"Build Debug APK"** workflow run
3. Scroll down to **Artifacts**
4. Download **`NewsFlashApp-debug`** (contains `app-debug.apk`)

> **Note:** Artifacts are kept for **7 days** per run. If the latest run's artifact has expired, trigger a new build by clicking **"Run workflow"** on the Actions page.

---

## Features

- Real-time top headlines from **59 countries** (all GNews-supported regions)
- Country selector with flag chips — scroll horizontally to browse all countries
- Automatic **search fallback** for countries where top-headlines returns no English results (e.g. Saudi Arabia, UAE)
- Article detail screen with full content and a direct link to the original source
- Dark-themed UI with red accent (`#E53935`) built entirely in Jetpack Compose
- Empty-state and error-state screens with retry support
- Lazy loading with article images, source badge, and formatted publish date

---

## Countries Supported

All 59 countries supported by the GNews API are included:

Pakistan 🇵🇰 · United States 🇺🇸 · United Kingdom 🇬🇧 · India 🇮🇳 · Saudi Arabia 🇸🇦 · UAE 🇦🇪 · Australia 🇦🇺 · Canada 🇨🇦 · Germany 🇩🇪 · France 🇫🇷 · Japan 🇯🇵 · China 🇨🇳 · Russia 🇷🇺 · Brazil 🇧🇷 · Egypt 🇪🇬 · Turkey 🇹🇷 · Nigeria 🇳🇬 · South Africa 🇿🇦 · Mexico 🇲🇽 · Singapore 🇸🇬 · South Korea 🇰🇷 · Italy 🇮🇹 · Spain 🇪🇸 · Netherlands 🇳🇱 · Sweden 🇸🇪 · Norway 🇳🇴 · Switzerland 🇨🇭 · Argentina 🇦🇷 · Philippines 🇵🇭 · Malaysia 🇲🇾 · Indonesia 🇮🇩 · Ukraine 🇺🇦 · Israel 🇮🇱 · Morocco 🇲🇦 · Hong Kong 🇭🇰 · Taiwan 🇹🇼 · Thailand 🇹🇭 · New Zealand 🇳🇿 · Portugal 🇵🇹 · Romania 🇷🇴 · Greece 🇬🇷 · Poland 🇵🇱 · Bangladesh 🇧🇩 · Kenya 🇰🇪 · Ghana 🇬🇭 · Lebanon 🇱🇧 · Vietnam 🇻🇳 · Chile 🇨🇱 · Colombia 🇨🇴 · Peru 🇵🇪 · Finland 🇫🇮 · Belgium 🇧🇪 · Austria 🇦🇹 · Ireland 🇮🇪 · Hungary 🇭🇺 · Czechia 🇨🇿 · Slovakia 🇸🇰 · Bulgaria 🇧🇬 · Latvia 🇱🇻 · Lithuania 🇱🇹 · Estonia 🇪🇪 · Slovenia 🇸🇮 · Venezuela 🇻🇪 · Cuba 🇨🇺 · Senegal 🇸🇳 · Tanzania 🇹🇿 · Uganda 🇺🇬 · Ethiopia 🇪🇹 · Botswana 🇧🇼 · Namibia 🇳🇦 · Zimbabwe 🇿🇼

---

## Tech Stack

| Layer | Technology |
|---|---|
| UI | Jetpack Compose (Material 3) |
| Architecture | MVVM — ViewModel + StateFlow |
| Networking | Retrofit 2 + Gson |
| Image Loading | Coil |
| Navigation | Compose Navigation |
| News API | [GNews API v4](https://gnews.io) |
| Build | Gradle (Kotlin DSL) |
| CI/CD | GitHub Actions |

---

## Project Structure

```
app/src/main/java/com/example/madquiz/
├── data/
│   ├── api/          # Retrofit interface (top-headlines + search endpoints)
│   ├── model/        # Data classes + full country list
│   └── repository/   # NewsRepository with search fallback logic
├── navigation/       # AppNavigation (Compose NavHost)
├── ui/
│   ├── screens/      # HomeScreen, DetailScreen, SplashScreen
│   ├── theme/        # Colors, Typography, Theme
│   └── viewmodel/    # NewsViewModel (MVVM)
└── MainActivity.kt
```

---

## How to Build Locally

1. Clone the repo
   ```bash
   git clone https://github.com/theabdullahdogar/NewsFlashApp.git
   ```
2. Open in **Android Studio Hedgehog** or later
3. Wait for Gradle sync to complete
4. Run on an emulator or physical device (API 24+)

> The API key is already embedded in `NewsViewModel.kt` for convenience during evaluation.

---

## API

This app uses the **GNews API** (`https://gnews.io/api/v4/`).

- **Endpoint used:** `GET /top-headlines` (primary) and `GET /search` (fallback)
- **Free tier:** 100 requests/day
- Free API key is included — no setup needed to run the app
