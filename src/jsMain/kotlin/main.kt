import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Window
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.js.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


import org.jetbrains.skiko.wasm.onWasmReady

fun main() {
    onWasmReady {
        Window(title = "Test") {
            HttpClient()
            var msisdn by remember { mutableStateOf("") }
            var showData by remember {
                mutableStateOf(false)
            }


            var jsonData = JsonData(
                msisdn = "1279144490",
                segmentId = "4384",
                rechargeValue = 100,
                currentTariff = "FREEmax 50 (New)",
                targetTariff = "FREEmax 200 (New)",
                currentBundle = null,
                targetBundle = "GO 7-500 SuperMBs",
                targetExtension = "1250MB for 25LE",
                offerResponses = listOf("اشحن كارت الكبير 100 و اكسب 3000وحدة"),
                giftResponses = listOf("ليك 50وحدة هدية عرض ال 25 سنة اورنچ "),
                hTriggers = listOf(
                    "RECHARGE 10.0",
                    "RECHARGE 10.0",
                    "RECHARGE 10.0",
                    "RENEW_FREEMAX FREEmax 200 (New)",
                    "RENEW_FREEMAX FREEmax 50 (New)",
                    "MIGRATE_FREEMAX FREEmax 200 (New)"
                )
            )
            Column(horizontalAlignment = Alignment.Start) {

                TextField(value = msisdn, onValueChange = { msisdn = it })

                Button(content = {
                    Text("Inquire")
                }, onClick = {
                    GlobalScope.launch {
                        val httpClient = HttpClient(JsClient())
                        val httpResponse = httpClient.get("http://localhost:3040/getProfile?msisdn$msisdn")
                        val data :JsonData =  httpResponse.body()
                        jsonData = data;
                        showData = true
                    }
                    showData = true
                })
                if (showData) {
                    MyComposeUi(jsonData)
                }


            }

        }
    }
}

//private fun callApi() {
//    GlobalScope.launch {
//        val client = HttpClient()
//        val response = client.get("https://ktor.io/docs/")
//        console.log("response", response.bodyAsText())
//    }
//}


@Composable

fun MyComposeUi(jsonData: JsonData) {
    Column(

        modifier = Modifier.fillMaxWidth(),

        ) {


        // Display the MSISDN

        Text(

            text = "MSISDN: ${jsonData.msisdn}",

            style = TextStyle(fontSize = 24.sp)

        )


        // Display the segment ID

        Text(

            text = "Segment ID: ${jsonData.segmentId}",

            style = TextStyle(fontSize = 24.sp)

        )


        // Display the recharge value

        Text(

            text = "Recharge Value: ${jsonData.rechargeValue}",

            style = TextStyle(fontSize = 24.sp)

        )


        // Display the current tariff

        Text(

            text = "Current Tariff: ${jsonData.currentTariff}",

            style = TextStyle(fontSize = 24.sp)

        )


        // Display the target tariff

        Text(

            text = "Target Tariff: ${jsonData.targetTariff}",

            style = TextStyle(fontSize = 24.sp)

        )


        // Display the current bundle

        Text(

            text = "Current Bundle: ${jsonData.currentBundle}",

            style = TextStyle(fontSize = 24.sp)

        )


        // Display the target bundle

        Text(

            text = "Target Bundle: ${jsonData.targetBundle}",

            style = TextStyle(fontSize = 24.sp)

        )


        // Display the target extension

        Text(

            text = "Target Extension: ${jsonData.targetExtension}",

            style = TextStyle(fontSize = 24.sp)

        )


        Text(text = "Triggers", style = TextStyle(fontSize = 24.sp))



        Column(


        ) {


            jsonData.hTriggers.forEach { trigger ->

                Text(trigger)

            }


        }





        Text(text = "Gifts", style = TextStyle(fontSize = 24.sp))
        Column {


            jsonData.giftResponses.forEach { gift ->

                Text(gift)

            }


        }





        Text(text = "Offers", style = TextStyle(fontSize = 24.sp))


        Column(


        ) {


            jsonData.offerResponses.forEach { offerResponse ->

                Text(offerResponse)

            }


        }


    }


}

data class JsonData(

    val msisdn: String,

    val segmentId: String,

    val rechargeValue: Int,

    val currentTariff: String,

    val targetTariff: String,

    val currentBundle: String?,

    val targetBundle: String,

    val targetExtension: String,

    val offerResponses: List<String>,

    val giftResponses: List<String>,

    val hTriggers: List<String>

)
