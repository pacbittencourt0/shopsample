package com.pacbittencourt.shop.ui.cart

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import com.pacbittencourt.shop.ui.components.ItemOrder
import org.koin.androidx.compose.getViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartScreen(
    modifier: Modifier,
    viewModel: CartViewModel = getViewModel()
) {
    LaunchedEffect(key1 = true) {
        viewModel.loadCurrentCart()
    }
    val state = viewModel.cartState.collectAsState().value
    var discount by remember { mutableStateOf("0.0") }
    Box(modifier = modifier.fillMaxSize()) {
        if (state.isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        } else if (state.orderRequestSuccess) {
            OrderRequestSuccess()
        } else if (state.cart.isEmpty()) {
            EmptyState()
        } else {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Transparent)
            ) {
                LazyColumn(modifier = Modifier.padding(bottom = 56.dp)) {
                    itemsIndexed(state.cart) { index, item ->
                        ItemOrder(item, { viewModel.deleteItem(index) }, isItemFromCart = true)
                    }
                }
                Column(modifier = Modifier.align(Alignment.BottomCenter)) {
                    Row {
                        Text(text = "Desconto: ")
                        TextField(
                            modifier = Modifier.width(80.dp),
                            value = discount, onValueChange = {
                                discount = it
                                viewModel.applyDiscount(discount.toDouble())
                            })
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        ElevatedButton(
                            onClick = {
                                viewModel.assembleOrder()
                            }
                        ) {
                            Text(text = "Fazer Pedido")
                        }
                        ElevatedButton(
                            onClick = {
                                viewModel.clearOrder()
                            }
                        ) {
                            Text(text = "Limpar")
                        }
                        Text(
                            text = "Total: R$${state.cartTotalPrice}",
                            fontSize = TextUnit(16f, TextUnitType.Sp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun OrderRequestSuccess() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            modifier = Modifier.size(64.dp),
            imageVector = Icons.Default.CheckCircle,
            contentDescription = "success",
            tint = Color.Green
        )
        Text(
            modifier = Modifier.padding(8.dp),
            textAlign = TextAlign.Center,
            text = "Pedido Registrado!",
            fontSize = TextUnit(24f, TextUnitType.Sp)
        )
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
            text = "Seu carrinho está vazio"
        )
        Text(
            modifier = Modifier.padding(8.dp),
            textAlign = TextAlign.Center,
            text = "Vá na aba de produtos para adicionar itens ao seu pedido"
        )
    }
}
