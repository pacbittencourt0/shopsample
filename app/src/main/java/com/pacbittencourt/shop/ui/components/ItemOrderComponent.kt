package com.pacbittencourt.shop.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Divider
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import com.pacbittencourt.shop.ui.model.ItemOrderUi

@Composable
fun ItemOrder(item: ItemOrderUi, onDeleteItem: () -> Unit = {}, isItemFromCart: Boolean = false) {
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        val fontSize = TextUnit(16f, TextUnitType.Sp)
        Column(modifier = Modifier.padding(8.dp)) {
            Box(modifier = Modifier.fillMaxWidth()) {
                Text(
                    modifier = Modifier.padding(8.dp),
                    text = item.itemDescription,
                    fontSize = fontSize
                )
                Text(
                    modifier = Modifier
                        .padding(8.dp)
                        .align(Alignment.BottomEnd),
                    text = "R$${item.itemPrice}",
                    fontSize = fontSize
                )
            }
            Divider(modifier = Modifier.padding(horizontal = 4.dp))
            Box(modifier = Modifier.fillMaxWidth()) {
                Text(
                    modifier = Modifier.padding(8.dp),
                    text = "Quantidade: ${item.quantity}",
                    fontSize = fontSize
                )
                Text(
                    modifier = Modifier
                        .padding(8.dp)
                        .align(Alignment.BottomEnd),
                    text = "R$${item.totalPrice}",
                    fontWeight = FontWeight.Bold,
                    fontSize = TextUnit(18f, TextUnitType.Sp)
                )
            }
            if (isItemFromCart) {
                IconButton(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    onClick = {
                        onDeleteItem()
                    }) {
                    Icon(Icons.Default.Close, contentDescription = "delete")
                }
            }
        }
    }
}

@Preview
@Composable
fun Preview() {
    ItemOrder(item = ItemOrderUi("2,00", "Arroz", "3", "6,00"), {}, true)

}