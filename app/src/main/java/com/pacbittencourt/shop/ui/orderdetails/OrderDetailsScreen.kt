package com.pacbittencourt.shop.ui.orderdetails

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.pacbittencourt.shop.ui.components.ItemOrder
import org.koin.androidx.compose.getViewModel

@Composable
fun OrderDetailsScreen(
    modifier: Modifier,
    orderId: String?,
    viewModel: OrderDetailsViewModel = getViewModel()
) {
    val state = viewModel.detailsState.collectAsState().value
    LaunchedEffect(key1 = true) {
        viewModel.loadOrderDetails(orderId ?: "0")
    }
    Box(modifier = modifier.fillMaxSize().padding(8.dp)) {
        if (state.isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        } else if (state.orderDetailsUi != null) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp)
            ) {
                Text(
                    text = "Número do pedido: $orderId",
                    style = MaterialTheme.typography.titleLarge
                )
                Row(modifier = Modifier.fillMaxWidth(), Arrangement.SpaceBetween) {
                    Text(text = "Valor do pedido", style = MaterialTheme.typography.titleLarge)
                    Text(
                        text = "R$${state.orderDetailsUi.totalPrice}",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                }
                Divider(modifier = Modifier.padding(vertical = 16.dp))
                Text(text = "Itens", style = MaterialTheme.typography.titleLarge)
                LazyColumn {
                    items(state.orderDetailsUi.items) {
                        ItemOrder(item = it)
                    }
                }
            }
        }
    }
}