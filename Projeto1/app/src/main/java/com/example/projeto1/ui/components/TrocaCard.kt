package com.example.projeto1.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.res.stringResource
import com.example.projeto1.R

@Composable
fun TrocaCard(troca: ExchangeData, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .background(
                color = MaterialTheme.colorScheme.primaryContainer,
                shape = RoundedCornerShape(16.dp)
            )
            .clickable { onClick() }
            .padding(16.dp)
    ) {
        Text(
            text = stringResource(id = R.string.label_livro, troca.book_name),
            style = MaterialTheme.typography.titleMedium
        )
        Text(text = stringResource(id = R.string.label_estado, troca.book_state))
        Text(text = stringResource(id = R.string.label_buscando, troca.searching_for))
    }
}
