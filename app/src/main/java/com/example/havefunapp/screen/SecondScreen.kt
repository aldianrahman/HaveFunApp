@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.havefunapp.screen

import android.annotation.SuppressLint
import android.widget.Toolbar
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.havefunapp.R
import com.example.havefunapp.ui.theme.DeepBlue
import com.example.havefunapp.util.BottomMenuContent

data class Transaction(val title: String, val amount: String)

@Composable
fun SecondScreen(index: String?) {
    Box(modifier = Modifier
        .background(DeepBlue)
        .fillMaxSize()){
        if (index != null) {

            ExpenseTrackerApp()


//            BottomMenu(
//                editor = null,
//                items = listOf(
//                    BottomMenuContent("Home", R.drawable.ic_home),
//                    BottomMenuContent("Meditate", R.drawable.ic_bubble),
//                    BottomMenuContent("Sleep", R.drawable.ic_moon),
//                    BottomMenuContent("Music", R.drawable.ic_music),
//                    BottomMenuContent("Profile", R.drawable.ic_profile),
//                ), modifier = Modifier.align(Alignment.BottomCenter)
//                , navController = null,
//                initialSelectedItemIndex = index.toInt()
//            )
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ExpenseTrackerApp() {
    Scaffold(
        topBar = { Toolbar() },
        content = { TransactionList() },
        floatingActionButton = { AddTransactionButton() }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Toolbar() {
    TopAppBar(
        title = { Text(text = "Expense Tracker") },
        modifier = Modifier.background(Color.Blue),

    )
}

@Composable
fun AddTransactionButton() {
    FloatingActionButton(
        onClick = { /* Tambahkan logika untuk menambahkan transaksi */ },
        modifier = Modifier
            .padding(16.dp)
    ) {
        Icon(
            imageVector = Icons.Default.Add,
            contentDescription = "Add Transaction"
        )
    }
}

@Composable
fun TransactionList() {
    val transactions = listOf(
        Transaction("Groceries", "-$50.00"),
        Transaction("Dinner", "-$30.00"),
        Transaction("Salary", "+$2000.00")
    )

    LazyColumn {
        items(transactions) { transaction ->
            TransactionItem(transaction)
        }
    }
}

@Composable
fun TransactionItem(transaction: Transaction) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = transaction.title,
            modifier = Modifier.weight(1f),
            style = TextStyle(fontSize = 16.sp)
        )
        Text(
            text = transaction.amount,
            style = TextStyle(
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = if (transaction.amount.startsWith("-")) Color.Red else Color.Green
            )
        )
    }
}