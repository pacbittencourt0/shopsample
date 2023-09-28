package com.pacbittencourt.shop.ui.orders

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.pacbittencourt.shop.ui.model.OrderSummaryUi
import org.koin.androidx.compose.getViewModel

@Composable
fun OrdersScreen(
    modifier: Modifier,
    viewModel: OrdersViewModel = getViewModel(),
    onOrderClickAction: (String) -> Unit = {}
) {
    LaunchedEffect(key1 = true) {
        viewModel.loadOrders()
    }
    val state = viewModel.orderState.collectAsState().value
    Column(modifier = modifier.fillMaxSize()) {
        if (state.isLoading) {
            CircularProgressIndicator()
        } else if (state.orders.isEmpty()) {
            EmptyState()
        } else {
            LazyColumn(modifier = modifier) {
                items(state.orders) {
                    OrderItem(it, onOrderClickAction)
                }
            }
            Text(text = "Total R$${state.totalOrders}")
        }
    }
}

@Composable
private fun EmptyState() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier.padding(8.dp),
            textAlign = TextAlign.Center,
            text = "Você ainda não realizou pedidos"
        )
        Text(
            modifier = Modifier.padding(8.dp),
            textAlign = TextAlign.Center,
            text = "Seus pedidos aparecerão aqui"
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun OrderItem(order: OrderSummaryUi, onOrderClickAction: (String) -> Unit) {
    ElevatedCard(modifier = Modifier
        .fillMaxWidth()
        .padding(8.dp),
        onClick = {
            onOrderClickAction(order.orderId)
        }) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(text = "Número do Pedido: ${order.orderId}", fontWeight = FontWeight.Bold)
            Divider(modifier = Modifier.padding(vertical = 4.dp))
            Text(text = "Quantidade de itens: ${order.itemQuantity}")
            Text(text = "Valor total do pedido: R$${order.totalPrice}")
        }
    }
}