package com.example.projeto1.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.projeto1.repository.retrofit.ExchangeData
import androidx.compose.ui.graphics.Color

@Composable
fun TrocaCard(troca: ExchangeData) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .background(
                color = MaterialTheme.colorScheme.primaryContainer,
                shape = RoundedCornerShape(16.dp)
            )
            .padding(16.dp)
    ) {
        Text("Livro: ${troca.book_name}", style = MaterialTheme.typography.titleMedium)
        Text("Estado: ${troca.book_state}")
        Text("Buscando: ${troca.searching_for}")
    }
}
