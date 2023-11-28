package com.example.crypto.screens.detail

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.crypto.R
import com.example.crypto.model.HistoryData
import com.example.crypto.viewmodel.CoinCapViewModel
import com.example.crypto.viewmodel.CurrencyViewModel
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.utils.ColorTemplate
import kotlinx.coroutines.delay
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun DetailScreen(navController: NavController, priceUsd: String, name: String, id: String) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = name)
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        content = {
            // Your DetailScreen content
            val customFontFamily = FontFamily(Font(R.font.ubuntu_bold))
            Text(
                text = priceUsd,
                fontFamily = customFontFamily // Apply custom font
            )
            CurrencyConverterApp(priceUsd, name, id)
        }
    )
}


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CurrencyConverterApp(priceUsd: String, name: String, id: String) {

    val customFontFamily = FontFamily(Font(R.font.ubuntu_bold))
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(name) }
            )
        }
    ) {
        val viewModel: CurrencyViewModel = viewModel()
        viewModel.convertCryptoCoinToSek(priceUsd.toDoubleOrNull() ?: 0.0)
        val currencyState by viewModel.currencypriceStateFlow.collectAsState()
        val priceSek = viewModel.convertedOneCoinAmount.value


        currencyState?.let {
            val upperCaseid = id.uppercase(Locale.ROOT)

            Text(
                text = "$upperCaseid current rate",
                style = typography.bodyLarge,
                fontWeight = FontWeight.Bold,
                fontFamily = customFontFamily,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp, 75.dp, 0.dp, 0.dp)
            )

            CryptoCard(priceUsd, priceSek, name)

        }
        MyApp(oneCoinUsd = priceUsd, oneCoinSek = priceSek, id)
        LineChartContent(id)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyApp(oneCoinUsd: String, oneCoinSek: String, id: String) {

    val customFontFamily = FontFamily(Font(R.font.ubuntu_bold))
    var currency by remember { mutableStateOf("Dollar") }
    val viewModel: CurrencyViewModel = viewModel()
    val currencyState by viewModel.currencyStateFlow.collectAsState()

    var openBottomSheet: Boolean by remember { mutableStateOf(false) }
    val bottomSheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )


    LaunchedEffect(viewModel) {
        viewModel.getCurrencyRates()
    }

    var userInput by remember { mutableStateOf(TextFieldValue("")) }

    currencyState?.let { response ->
        response.data["SEK"]

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Exchanger", fontFamily = customFontFamily)
            TextField(
                value = userInput,
                onValueChange = {
                    userInput = it
                },
                label = { Text("Enter amount", fontFamily = customFontFamily) },
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                CurrencyImageButton(R.drawable.america, "Dollar") {
                    currency = "Dollar"
                }
                Button(
                    onClick = {
                        openBottomSheet = true
                        viewModel.convertToSEK(userInput.text.toDoubleOrNull() ?: 1.0)
                    }, modifier = Modifier.padding(8.dp), enabled = userInput.text.isNotBlank()

                ) { Text("Convert") }
                CurrencyImageButton(R.drawable.sweden, "SEK") {
                    currency = "SEK"
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Use conditional statements to show/hide values based on the selected currency
            val conversionRate: Double? = when (currency) {
                "Dollar" -> oneCoinUsd.toDoubleOrNull()
                "SEK" -> oneCoinSek.toDoubleOrNull()
                else -> null // Handle other currencies if needed
            }

            val convertedValue = conversionRate?.let { userInput.text.toDoubleOrNull()?.times(it) }

            val currencyText = when (currency) {
                "Dollar" -> "dollar"
                "SEK" -> "sek"
                else -> "unknown currency" // Handle other currencies if needed
            }
            Text(
                "Converted to $currency: ${convertedValue?.toString() ?: "0.00"} $currencyText",
                fontFamily = customFontFamily
            )
        }
        if (openBottomSheet) {
            ModalBottomSheet(
                sheetState = bottomSheetState,
                onDismissRequest = { openBottomSheet = false },
                dragHandle = {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        BottomSheetDefaults.DragHandle()
                        Text(text = "Currency", style = typography.titleLarge)
                        Spacer(modifier = Modifier.height(10.dp))
                        Divider()
                    }
                }
            ) {
                ConversionBottomSheetContent(
                    userInput.text , currency,  id, oneCoinSek, oneCoinUsd,
                )
            }
        }
    }
}

@Composable
fun CurrencyImageButton(drawableId: Int, name: String, onClick: () -> Unit) {
    val customFontFamily = FontFamily(Font(R.font.ubuntu_bold))
    Box(
        modifier = Modifier
            .clickable { onClick() }
            .padding(8.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = drawableId),
                contentDescription = null,
                modifier = Modifier.size(48.dp)
            )
            Text(name, fontFamily = customFontFamily)
        }
    }
}


@Composable
fun ConversionBottomSheetContent(
    convertedAmount: String,
    currencyName: String?,
    cryptoName: String,
    oneCoinSek: String,
    oneCoinUsd: String
) {
    LazyColumn(
        contentPadding = PaddingValues(16.dp)
    ) {
        item {
            when (currencyName) {
                "SEK" -> {
                    val sekValue = convertedAmount.toDouble() * oneCoinSek.toDouble()
                    Text(
                        text = "You have converted $cryptoName $convertedAmount for $sekValue $currencyName. ",
                        style = typography.bodySmall,
                        fontWeight = FontWeight.Normal,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )
                }

                "Dollar" -> {
                    val usdValue = convertedAmount.toDouble() * oneCoinUsd.toDouble()
                    Text(
                        text = "You have converted $cryptoName $convertedAmount coin for $usdValue $currencyName. ",
                        style = typography.bodySmall,
                        fontWeight = FontWeight.Normal,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )
                }

                else -> {
                    Text(
                        text = "You have converted $convertedAmount $cryptoName for $currencyName.",
                        style = typography.bodySmall,
                        fontWeight = FontWeight.Normal,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )
                }
            }
        }
    }
}


@Composable
fun LineChartContent(id: String) {
    val upperCaseID = id.uppercase(Locale.ROOT)

    val coinCapViewModel: CoinCapViewModel = viewModel()

    LaunchedEffect(id) {
        coinCapViewModel.fetchCoinCapHistory(id)
    }

    val cryptoCoinData = coinCapViewModel.historyData.collectAsState()

    if (cryptoCoinData.value.isEmpty()) {
        Text("Loading data...")
    } else {

        val lineData = lineChartDataSet(cryptoCoinData)

        // Rest of the LineChartContent function
        val context = LocalContext.current
        val lineChart = LineChart(context)
        val lineDataSet = LineDataSet(lineData, id)
        lineDataSet.colors = ColorTemplate.COLORFUL_COLORS.toList()
        val iLineDataSets = listOf(lineDataSet)

        val data = LineData(iLineDataSets)
        lineChart.data = data
        lineChart.invalidate()

        lineChart.setNoDataText("Data not Available")


        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
        ) {
            // Other UI components above the bottom-aligned content

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
            ) {
                Text(
                    text = "$upperCaseID history rate",
                    style = typography.bodyLarge,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .fillMaxWidth()
                    // Adjust the padding as needed
                )

                AndroidView(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                    factory = { lineChart }
                )
            }
        }
    }
}



private fun lineChartDataSet(data: State<List<HistoryData>>): List<Entry> {
    val dataList = data.value
    val lastIndex = dataList.size - 1
    val startIndex =
        if (lastIndex >= 4) lastIndex - 4 else 0 // Adjust to show the latest five entries
    return dataList.subList(startIndex, lastIndex + 1).mapIndexed { index, bitcoinData ->
        Entry(index.toFloat(), bitcoinData.priceUsd.toFloat())
    }
}


@Composable
fun CryptoCard(priceUsd: String, priceSek: String, name: String) {
    val formattedUSDString = "Value: %.2f".format(priceUsd.toDouble())
    val formattedSEKString = "Value: %.2f".format(priceSek.toDouble())

    val textColor = Color(0xFF000000)

    Box {
        var visible by remember { mutableStateOf(false) }
        LaunchedEffect(Unit) {
            delay(1000)
            visible = true
        }

        AnimatedVisibility(
            visible = visible,
            enter = fadeIn() + slideInVertically { fullHeight -> fullHeight },
        ) {
            Row(Modifier.padding(5.dp, 80.dp, 0.dp, 0.dp)) {
                CryptoCardContent(name, formattedUSDString + "USD", textColor)
                CryptoCardContent(name, formattedSEKString + "SEK", textColor)
            }
        }
    }
}

@Composable
private fun CryptoCardContent(
    name: String, price: String,
    textColor: Color
) {
    Column(
        verticalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.size(100.dp)
    ) {
        Spacer(modifier = Modifier.height(12.dp))

        Column(
            verticalArrangement = Arrangement.SpaceAround,
            modifier = Modifier.padding(4.dp)
        ) {
            Text(
                text = name,
                color = textColor,
                style = typography.labelMedium
            )
            Text(
                text = price,
                color = textColor,
                style = typography.labelMedium
            )
        }
    }

}





