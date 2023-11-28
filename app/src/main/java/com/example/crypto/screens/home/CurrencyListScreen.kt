package com.example.crypto.screens.home

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.crypto.R
import com.example.crypto.model.CryptoCurrency
import com.example.crypto.viewmodel.CoinCapViewModel
import kotlinx.coroutines.launch


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CurrencyListScreen(navController: NavHostController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Currencys") }
            )
        }
    ) {
        val viewModel: CoinCapViewModel = viewModel()
        val tickers by viewModel.assets.collectAsState()
        TickerList(tickers = tickers, navController = navController)
    }
}


@Composable
fun ExpandableCard(cryptocurrency: CryptoCurrency, navController: NavHostController) {
    var expandedState by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current // Access LocalContext outside @Composable

    // Specify the custom font family
    val customFontFamily = FontFamily(Font(R.font.ubuntu_bold))

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .animateContentSize()
            .clickable {
                expandedState = !expandedState
            }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Text aligned to the left with custom font
                cryptocurrency.name?.let {
                    Text(
                        text = it,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        fontFamily = customFontFamily
                    )
                }

                // Icon aligned to the right
                IconButton(
                    onClick = {
                        expandedState = !expandedState
                    },
                    modifier = Modifier.padding(start = 4.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = "Drop-Down Arrow"
                    )
                }
            }

            if (expandedState) {
                // Display additional details when expanded
                Spacer(modifier = Modifier.height(8.dp)) // Add some spacing for better readability

                // Apply custom font to all the Text composables
                val textStyle = TextStyle(
                    fontFamily = customFontFamily,
                    fontSize = 18.sp
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp) // Adjust spacing as needed
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.cryp),
                        contentDescription = "Local Image",
                        modifier = Modifier
                            .size(50.dp)
                            .padding(8.dp)
                    )
                    Text("Currency: ${cryptocurrency.symbol}", style = textStyle)
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp) // Adjust spacing as needed
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.maxsupp),
                        contentDescription = "Local Image",
                        modifier = Modifier
                            .size(50.dp)
                            .padding(8.dp)
                    )
                    Text(
                        "Max Supply: ${
                            cryptocurrency.maxSupply?.let {
                                formatStringWithTwoDecimals(
                                    it
                                )
                            } ?: "N/A"
                        }", style = textStyle)
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp) // Adjust spacing as needed
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.marketcap),
                        contentDescription = "Local Image",
                        modifier = Modifier
                            .size(50.dp)
                            .padding(8.dp)
                    )
                    Text(
                        "Market Cap USD: ${
                            cryptocurrency.marketCapUsd?.let {
                                formatStringWithTwoDecimals(
                                    it
                                )
                            } ?: "N/A"
                        }", style = textStyle)
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp) // Adjust spacing as needed
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.twentyfourhour),
                        contentDescription = "Local Image",
                        modifier = Modifier
                            .size(50.dp)
                            .padding(8.dp)
                    )
                    Text(
                        "24hr Volume: ${
                            cryptocurrency.volumeUsd24Hr?.let {
                                formatStringWithTwoDecimals(
                                    it
                                )
                            } ?: "N/A"
                        }", style = textStyle)
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp) // Adjust spacing as needed
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.dollar),
                        contentDescription = "Local Image",
                        modifier = Modifier
                            .size(50.dp)
                            .padding(8.dp)
                    )
                    Text("Price USD: ${cryptocurrency.priceUsd?.let { formatStringWithTwoDecimals(it) } ?: "N/A"}",
                        style = textStyle)
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp) // Adjust spacing as needed
                ) {
                    // Style the change percentage text based on positive/negative change
                    val textColor =
                        if ((cryptocurrency.changePercent24Hr?.toDouble() ?: 0.0) >= 0) {
                            Color.Green
                        } else {
                            Color.Red
                        }
                    Image(
                        painter = painterResource(id = R.drawable.graphbar),
                        contentDescription = "Local Image",
                        modifier = Modifier
                            .size(50.dp)
                            .padding(8.dp)
                    )
                    Text(
                        "Change (24hr): ${
                            cryptocurrency.changePercent24Hr?.let {
                                formatStringWithTwoDecimals(
                                    it
                                )
                            } ?: "N/A"
                        }", color = textColor, style = textStyle)
                }


                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp) // Adjust spacing as needed
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.clock),
                        contentDescription = "Local Image",
                        modifier = Modifier
                            .size(50.dp)
                            .padding(8.dp)
                    )
                    Text(
                        "VWAP (24hr): ${
                            cryptocurrency.vwap24Hr?.let {
                                formatStringWithTwoDecimals(
                                    it
                                )
                            } ?: "N/A"
                        }",
                        style = textStyle)


                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp) // Adjust spacing as needed
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.globe),
                        contentDescription = "Local Image",
                        modifier = Modifier
                            .size(50.dp)
                            .padding(8.dp)
                    )
                    cryptocurrency.explorer?.let { url ->
                        ClickableText(
                            text = buildAnnotatedString {
                                withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.primary)) {
                                    append("Webpage ")
                                }
                                append(url)
                            },
                            onClick = {
                                // Handle the click event, for example, open the URL in a web browser
                                coroutineScope.launch {
                                    openWebPage(url, context)
                                }
                            },
                            modifier = Modifier.padding(top = 8.dp),
                            style = textStyle
                        )
                    }
                }



                Spacer(modifier = Modifier.height(16.dp)) // Add some spacing before the button
                Button(
                    shape = MaterialTheme.shapes.medium,
                    onClick = {
                        navController.navigate("Detail_screen/${cryptocurrency.priceUsd}/${cryptocurrency.name}/${cryptocurrency.id}")
                    },
                    modifier = Modifier.padding(top = 8.dp)
                ) {
                    Text(
                        text = "Go to Details",
                        modifier = Modifier.padding(8.dp),
                        style = textStyle
                    )
                }
            }
        }
    }
}


fun formatStringWithTwoDecimals(input: String): String {

    val value =
        input.toDoubleOrNull() ?: return input // Return the original string if conversion fails
    val formattedString = String.format("%.2f", value)

    return formattedString
}


private fun openWebPage(url: String, context: Context) {
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
    startActivity(context, intent, null)
}

@Composable
fun TickerList(tickers: List<CryptoCurrency>, navController: NavHostController) {
    LazyColumn(Modifier.padding(0.dp, 55.dp, 0.dp, 0.dp)) {
        itemsIndexed(tickers) { index, ticker ->
            ExpandableCard(ticker, navController)
        }
    }
}


