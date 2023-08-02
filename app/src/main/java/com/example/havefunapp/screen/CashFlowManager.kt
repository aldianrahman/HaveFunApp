@file:Suppress("NAME_SHADOWING")

package com.example.havefunapp.screen

import android.app.DatePickerDialog
import android.content.Context
import android.widget.DatePicker
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.havefunapp.MainActivity
import com.example.havefunapp.R
import com.example.havefunapp.model.DataTransaksi
import com.example.havefunapp.model.Movies
import com.example.havefunapp.ui.theme.AquaBlue
import com.example.havefunapp.ui.theme.DeepBlue
import com.example.havefunapp.ui.theme.TextWhite
import com.example.havefunapp.util.Util
import java.time.LocalDate
import java.util.Calendar


@Composable
fun CashFlowManager(){
    val context = LocalContext.current
    val mainActivity = MainActivity()
    val onBack = {
        Util.toastToText(
            context,
            "Tekan tombol 'Back' sekali lagi untuk menutup aplikasi"
        )
    }
    var showAlertButton by remember { mutableStateOf(false) }


//    mainActivity.BackPressHandler(onBackPressed = onBack)
    Box(
        modifier = Modifier
            .background(DeepBlue)
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(16.dp)
        ) {
            TextHeader(
                "User"
            )
            TextHeader(
                "Tanggal"
            )
            TextHeader(
                "Rp. 10.000.000 ,-"
            )
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                items(200) { index ->
                    Column {
                        Text(""+index)
                    }
                }
            }
        }

        // Floating Button
        FloatingActionButton(
            containerColor = AquaBlue,
            onClick = {
                showAlertButton = true
            },
            shape = CircleShape,
            modifier = Modifier
                .size(70.dp).padding(bottom = 16.dp)
                .align(Alignment.BottomCenter)
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                modifier = Modifier.size(24.dp),
                contentDescription = "Tambah",
                tint = TextWhite
            )
        }

        if (showAlertButton) {
            InputData(
                context,
                mainActivity
            ){
                showAlertButton = false
            }
        }
    }
}

@Composable
fun InputData(
    context: Context,
    mainActivity: MainActivity,
    onClose: () -> Unit,
) {
    var checkEmpty by remember { mutableStateOf(true) }
    AlertDialog(
        containerColor = DeepBlue,
        onDismissRequest = {
            onClose.invoke()
        },
        title = { Text(stringResource(R.string.input_transaksi_text), color = TextWhite) },
        text = {
            BudgetManagementForm(
                context,
                mainActivity,
            ){bool->
                checkEmpty = bool
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    if (!checkEmpty){
                        Util.toastToText(context,"Berhasil di Simpan")

                        onClose.invoke()
                    }else{
                        Util.toastToText(context,"Mohon Isi Dengan Lengkap")
                    }


                },
                colors = mainActivity.defaultButtonColor()
            ) {
                Text("Simpan", color = Color.White)
            }
            Button(
                onClick = {
                    onClose.invoke()
                },
                colors = mainActivity.defaultButtonColor()
            ) {
                Text("Close", color = Color.White)
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BudgetManagementForm(
    context: Context,
    mainActivity: MainActivity,
    onSuccess: (Boolean) -> Unit
) {
    var budgetName by remember { mutableStateOf("") }
    var budgetAmount by remember { mutableStateOf("") }
    val transaksiData = mutableListOf<DataTransaksi>()

    val context = LocalContext.current
    val calendar = Calendar.getInstance()

    var selectedDateText by remember { mutableStateOf("") }
    var mJenisTransaksi by remember { mutableStateOf("") }

// Fetching current year, month and day
    val year = calendar[Calendar.YEAR]
    val month = calendar[Calendar.MONTH]
    val dayOfMonth = calendar[Calendar.DAY_OF_MONTH]

    val datePicker = DatePickerDialog(
        context,
        { _: DatePicker, selectedYear: Int, selectedMonth: Int, selectedDayOfMonth: Int ->
            selectedDateText = "$selectedDayOfMonth - ${selectedMonth + 1} - $selectedYear"
        }, year, month, dayOfMonth
    )

    LazyColumn(
        modifier = Modifier.padding(8.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {


            DropdownDemo(intetJenisTransaksi = {jenisTransaksi->
                mJenisTransaksi = jenisTransaksi
            }){bool->
                if (selectedDateText.isEmpty() || bool || budgetName.isEmpty()|| budgetAmount.isEmpty()){
                    onSuccess(true)
                }else{
                    onSuccess(false)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(width = 2.dp, color = TextWhite)
                    .clickable { datePicker.show() },
                text = if (selectedDateText.isNotEmpty()) {
                    "Tanggal Transaksi \n $selectedDateText"
                } else {
                    "Tanggal Transaksi"
                },
                color = TextWhite,
                textAlign = TextAlign.Center
            )


            Spacer(modifier = Modifier.height(16.dp))


            CoolTextField(
                value = budgetName,
                onValueChange = { newbudgetName -> budgetName = newbudgetName },
                placeholder = stringResource(R.string.nama_transaksi_text),
                modifier = Modifier,
                colors = mainActivity.defaultTextFieldColor()
            )

            Spacer(modifier = Modifier.height(8.dp))

            CoolTextField(
                value = budgetAmount,
                onValueChange = { newBudgetAmount -> budgetAmount = newBudgetAmount },
                placeholder = stringResource(R.string.jumlah_transaksi_text),
                modifier = Modifier,
                colors = mainActivity.defaultTextFieldColor()
            )
        }


    }
}



@Composable
fun DropdownDemo(
    intetJenisTransaksi: (String) -> Unit,
    onSuccess: (Boolean) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    val items = listOf("Pendapatan", "Pengeluaran", "Transfer", "Investasi", "Hutang", "Pemasukan Non-Uang ","Pengeluaran Non-Uang","Pajak","Lainnya")
    var selectedIndex by remember { mutableStateOf(0) }
    var isFirsTime by remember { mutableStateOf(true) }

    Box(
        modifier = Modifier.fillMaxWidth().border(width = 2.dp, color = Color.White).clickable { expanded = true },
        contentAlignment = Alignment.Center
        ) {
        Text(
            if (isFirsTime) stringResource(R.string.pilih_jenis_transaksi_text) else items[selectedIndex],
            modifier = Modifier
                .background(DeepBlue),
            color = Color.White
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.background(DeepBlue).align(Alignment.Center)
        ) {
            items.forEachIndexed { index, s ->
                val isDisabled = s == items[selectedIndex]
                val onItemClick: () -> Unit = {
                    selectedIndex = index
                    expanded = false
                }
                DropdownMenuItem(
                    onClick = {
                        onItemClick()
                        isFirsTime = false
                    },
                    modifier = Modifier
                        .background(if (isDisabled) Color.White else Color.Transparent)
                        .height(24.dp)
                        .fillMaxWidth(),
                    trailingIcon = {},
                    text = {
                        Text(items[index])
                    }
                )
            }
        }
        onSuccess(isFirsTime)
        intetJenisTransaksi(items[selectedIndex])
    }
}


@Composable
fun TextHeader(s: String) {
    Text(
        text = s,
        textAlign = TextAlign.Center,
        modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
    )
}
