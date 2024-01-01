package com.sample.arrive.ui.component

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sample.arrive.R
import com.sample.arrive.ui.theme.ArriveTheme


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropDownList(
    label: String,
    options: List<String>,
    selectedOption: String?,
    onSelectionChanged: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
        modifier = modifier
    ) {
        TextField(
            readOnly = true,
            label = { Text(text = label) },
            value = selectedOption?:"",
            placeholder = { Text(text = stringResource(R.string.dropdown_prompt))},
            onValueChange = { },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            colors = ExposedDropdownMenuDefaults.textFieldColors(),
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth()
                .shadow(elevation = 8.dp)
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            for(index in options.indices) {
                val option = options[index]
                DropdownMenuItem(
                    onClick = {
                        expanded = false
                        onSelectionChanged(index)
                    },
                    text = { Text(text = option, modifier = Modifier.fillMaxWidth()) },
                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DropDownListTest()
{
    ArriveTheme {
        DropDownList(
            label = "test",
            options = listOf("option1", "option2", "option3"),
            selectedOption = "option1",
            onSelectionChanged = {},
            modifier = Modifier.fillMaxSize()
        )
    }
}