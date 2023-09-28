package com.pacbittencourt.shop.ui.products

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pacbittencourt.shop.R
import org.koin.androidx.compose.getViewModel

@Composable
fun ProductsScreen(
    modifier: Modifier,
    viewModel: ProductsViewModel = getViewModel()
) {
    val state = viewModel.itemsState.collectAsState().value
    if (state.isLoading) {
        Box(modifier = modifier.fillMaxSize()) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }
    } else {
        Column(modifier = modifier) {
            LazyColumn {
                items(state.items) {
                    ProductItem(it) { id, quantity ->
                        viewModel.addItemToCart(id, quantity)
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductItem(product: ItemUi, addItemToCart: (Int, Double) -> Unit) {
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp)
    ) {
        var quantity by remember { mutableDoubleStateOf(0.0) }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp, start = 16.dp, end = 16.dp),
            Arrangement.SpaceBetween
        ) {
            Text(text = product.description)
            Text(text = "R$ ${product.price}")
        }
        Row(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(
                    top = 16.dp,
                    bottom = 8.dp
                ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { if (quantity > 0) quantity-- }) {
                Icon(
                    painterResource(id = R.drawable.remove),
                    contentDescription = "Minus"
                )
            }
            TextField(
                modifier = Modifier.width(80.dp),
                value = quantity.toString(),
                onValueChange = {
                    quantity = it.toDouble()
                })
            //Text(text = quantity.toString())
            IconButton(onClick = { quantity++ }) {
                Icon(Icons.Default.Add, contentDescription = "Add")
            }
            val context = LocalContext.current
            ElevatedButton(onClick = {
                if (quantity > 0) {
                    addItemToCart(product.id, quantity)
                    quantity = 0.0
                    Toast.makeText(
                        context,
                        "Produto adicionado",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }) {
                Text(text = "Adicionar")
            }
        }
    }
}

@Composable
@Preview
fun Preview() {
    ProductItem(ItemUi(1, "Arroz", "10.0")) { _, _ ->
    }
}