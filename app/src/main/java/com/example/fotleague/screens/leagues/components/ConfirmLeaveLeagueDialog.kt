package com.example.fotleague.screens.leagues.components

import android.content.res.Configuration
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.fotleague.ui.theme.FotLeagueTheme

@Composable
fun ConfirmLeaveLeagueDialog(
    onDismiss: () -> Unit,
    onLeaveLeague: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = "Leave league") },
        text = { Text(text = "Are you sure you want to leave this league?") },
        confirmButton = {
            TextButton(onClick = onLeaveLeague) {
                Text(text = "Leave")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(text = "Cancel")
            }
        }
    )
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun ConfirmLeaveLeagueDialogPreview() {
    FotLeagueTheme {
        ConfirmLeaveLeagueDialog(
            onLeaveLeague = {},
            onDismiss = {}
        )
    }
}
