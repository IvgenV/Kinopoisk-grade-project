package com.example.kinopoisk.feature.search.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
internal fun FilterDetailColumnItem(
    item: String,
    isSelected: Boolean,
    onClick: (String) -> Unit,
) {

    Column(
        modifier = Modifier.clickable {
            onClick.invoke(item)
        }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 4.dp),
                text = item
            )

            if (isSelected) {
                Icon(
                    modifier = Modifier.padding(end = 4.dp),
                    imageVector = Icons.Default.Check,
                    contentDescription = "Clear text",
                )
            }

        }
        HorizontalDivider(
            Modifier
                .padding(top = 4.dp)
        )
    }

}

@Composable
@Preview(showBackground = true)
private fun FilterDetailColumnItemPreview() {
    FilterDetailColumnItem(
        item = "Беларусь",
        isSelected = true,
        onClick = { }
    )
}

