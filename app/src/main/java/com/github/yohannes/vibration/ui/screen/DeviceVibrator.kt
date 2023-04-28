@file:OptIn(ExperimentalMaterialApi::class)

package com.github.yohannes.vibration.ui.screen

import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.github.yohannes.vibration.ui.components.CircularSlider
import com.github.yohannes.vibration.ui.components.SettingsListItem
import kotlinx.coroutines.launch


@Composable
fun DeviceVibrator(context: Context, paddingValues: PaddingValues = PaddingValues(10.dp)) {

    val coroutineScope = rememberCoroutineScope()
    val modalSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        confirmStateChange = {
            it != ModalBottomSheetValue.HalfExpanded
        },
        skipHalfExpanded = true
    )
    val isSheetFullScreen by remember { mutableStateOf(false) }
    val roundedCornerRadius = if (isSheetFullScreen) 0.dp else 16.dp
    val modifier = if (isSheetFullScreen)
        Modifier
            .fillMaxSize()
    else
        Modifier.fillMaxWidth()

    val patternsList =
        listOf("Pattern 1", "Pattern 2", "Pattern 3", "Pattern 4", "Pattern 5", "Pattern 6")
    var selectedPattern by remember {
        mutableStateOf("Pattern 1")
    }

    var sliderValue by remember {
        mutableStateOf(0f)
    }

    var openInfo by remember {
        mutableStateOf(false)
    }

    val vibrationMap = mapOf(
        "Pattern 1" to longArrayOf(0, 30, 30, 30, 30),
        "Pattern 2" to longArrayOf(0, 50, 50, 50, 50),
        "Pattern 3" to longArrayOf(0, 70, 70, 70, 70),
        "Pattern 4" to longArrayOf(0, 80, 80, 80, 80),
        "Pattern 5" to longArrayOf(0, 90, 90, 90, 90),
        "Pattern 6" to longArrayOf(0, 100, 100, 100, 100)
    )

    ModalBottomSheetLayout(
        sheetState = modalSheetState,
        sheetShape = RoundedCornerShape(
            topStart = roundedCornerRadius,
            topEnd = roundedCornerRadius
        ),
        sheetContent = {
            Column(
                modifier = modifier,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                BackHandler(enabled = modalSheetState.isVisible) {
                    coroutineScope.launch {
                        modalSheetState.hide()
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                Surface(
                    shape = RoundedCornerShape(50),
                    color = MaterialTheme.colors.secondary,
                    modifier = Modifier
                        .width(80.dp)
                        .height(8.dp)
                ) {}
                SettingsListItem(
                    settingsIcon = {
                        Icon(
                            Icons.Default.Info,
                            contentDescription = "Settings"
                        )
                    },
                    title = "About the Dev",
                    subtitle = null,
                    onItemClicked = {
                        openInfo = true
                    },
                    backgroundColor = MaterialTheme.colors.background,
                    tintColor = MaterialTheme.colors.secondary,
                    horizontalPadding = 20.dp,
                    verticalPadding = 12.dp,
                )

                LazyVerticalGrid(
                    columns = GridCells.Fixed(3),
                    contentPadding = PaddingValues(8.dp)
                ) {
                    items(patternsList.size) { index ->
                        Card(
                            modifier = Modifier.padding(8.dp),
                            shape = RoundedCornerShape(16.dp),
                            elevation = 4.dp,
                            border = if (selectedPattern == patternsList[index]) BorderStroke(
                                2.dp,
                                MaterialTheme.colors.primaryVariant
                            ) else BorderStroke(2.dp, Color.LightGray),
                            backgroundColor = if (selectedPattern == patternsList[index]) MaterialTheme.colors.primaryVariant else Color.White,
                            onClick = {
                                selectedPattern = patternsList[index]
                            }) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier.padding(10.dp)
                            ) {
                                Icon(
                                    Icons.Default.Info,
                                    contentDescription = "Settings",
                                    modifier = Modifier.size(28.dp),
                                    tint = if (selectedPattern == patternsList[index]) Color.White else MaterialTheme.colors.primaryVariant
                                )
                                Text(
                                    text = patternsList[index],
                                    fontSize = 16.sp,
                                    color = if (selectedPattern == patternsList[index]) Color.White else MaterialTheme.colors.primaryVariant
                                )
                            }
                        }
                    }
                }
            }
        },
        sheetBackgroundColor = MaterialTheme.colors.background,
        sheetContentColor = MaterialTheme.colors.primary
    ) {
        Scaffold(
            contentColor = MaterialTheme.colors.secondary,
        ) {
            Surface(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it),
                color = MaterialTheme.colors.background
            ) {

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                ) {

                    val vibrator = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                        val vibratorManager =
                            context.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager
                        vibratorManager.defaultVibrator
                    } else {
                        @Suppress("DEPRECATION")
                        context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
                    }

                    if (openInfo) {

                            AlertDialog(
                                modifier = Modifier
                                    .padding(12.dp)
                                    .padding(12.dp),
                                shape = RoundedCornerShape(12.dp),
                                onDismissRequest = {
                                    openInfo = false
                                },
                                text = {
                                    Column {
                                        Text(
                                            text = "Hi,\uD83D\uDC4B This app was made by [Enter name here.]",
                                            textAlign = TextAlign.Center
                                        )
                                    }
                                },
                                confirmButton = {
                                    Button(
                                        colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primaryVariant),
                                        shape = RoundedCornerShape(50),
                                        onClick = {
                                            openInfo = false
                                        }) {
                                        Text("Okay")
                                    }
                                }
                            )
                    }

                    Text(text = "Slide to right to increase speed", fontSize = 20.sp)

                    LaunchedEffect(selectedPattern, sliderValue) {

                        val gap = 100 - (sliderValue * 100).toLong()
                        val modifiedPattern =
                            vibrationMap[selectedPattern]?.mapIndexed { index, value -> if (index % 2 == 0) gap else value }
                                ?.toLongArray()
                        val vibrationEffect: VibrationEffect =
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                VibrationEffect.createWaveform(
                                    modifiedPattern,
                                    0
                                )
                            } else {
                                TODO("VERSION.SDK_INT < O")
                            }

                        vibrator.cancel()
                        if ((sliderValue * 100) > 3) {
                            vibrator.vibrate(vibrationEffect)
                        }
                    }

                    CircularSlider(
                        modifier = Modifier.size(300.dp),
                        gradientColors = listOf(
                            Color.White,
                            Color.Blue,
                            Color.Magenta,
                            Color.Red
                        ),
                        onChange = { value ->
                            sliderValue = value
                        })

                    Button(
                        colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primaryVariant),
                        shape = RoundedCornerShape(50),
                        onClick = {
                            coroutineScope.launch {
                                if (modalSheetState.isVisible)
                                    modalSheetState.hide()
                                else
                                    modalSheetState.animateTo(ModalBottomSheetValue.Expanded)
                            }
                        }) {
                        Icon(
                            Icons.Default.KeyboardArrowUp,
                            contentDescription = "Settings",
                            modifier = Modifier.size(28.dp)
                        )
                        Text("Patterns", modifier = Modifier.padding(5.dp), fontSize = 16.sp)
                    }
                }
            }
        }
    }
}