package com.example.fotleague.screens.leagues.leaguesettings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.exclude
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Replay
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScaffoldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.fotleague.R
import com.example.fotleague.Screen
import com.example.fotleague.screens.leagues.components.ConfirmDeleteLeagueDialog
import com.example.fotleague.screens.leagues.components.ConfirmLeaveLeagueDialog
import com.example.fotleague.screens.leagues.components.GenerateNewLeagueCodeDialog
import com.example.fotleague.screens.leagues.components.RenameLeagueDialog
import com.example.fotleague.ui.components.RowButton
import com.example.fotleague.ui.theme.Background
import com.example.fotleague.ui.theme.DarkGray
import com.example.fotleague.ui.theme.LightGray

@Composable
fun LeagueSettingsScreen(
    viewModel: LeagueSettingsViewModel = hiltViewModel(),
    navController: NavHostController
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(state.leagueLeft) {
        if (state.leagueLeft) {
            navController.navigate(Screen.Leagues) {
                popUpTo(Screen.Leagues)
            }
        }
    }

    Scaffold(
        contentWindowInsets = ScaffoldDefaults.contentWindowInsets.exclude(NavigationBarDefaults.windowInsets),
        topBar = { TopBar(navController::popBackStack) }
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            LeagueSettingsContent(
                state = state,
                onEvent = { viewModel.onEvent(it) }
            )
        }

    }
}

@Composable
private fun LeagueSettingsContent(
    state: LeagueSettingsState,
    onEvent: (LeagueSettingsEvent) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Background)
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Spacer(Modifier.height(4.dp))
        RowButton(
            icon = Icons.Default.Edit,
            text = "Rename league",
            onClick = { onEvent(LeagueSettingsEvent.OpenRenameLeagueDialog) }
        )
        HorizontalDivider()
        RowButton(
            icon = Icons.Default.Replay,
            text = "Generate new code",
            onClick = { onEvent(LeagueSettingsEvent.OpenGenerateNewLeagueCodeDialog) }
        )
        HorizontalDivider()
        if (state.isLeagueOwner) {
            RowButton(
                icon = Icons.Default.Delete,
                text = "Delete league",
                onClick = { onEvent(LeagueSettingsEvent.OpenDeleteLeagueDialog) }
            )
        } else {
            RowButton(
                icon = ImageVector.vectorResource(id = R.drawable.logout_24),
                text = "Leave league",
                onClick = { onEvent(LeagueSettingsEvent.OpenLeaveLeagueDialog) }
            )
        }
    }

    if (state.isRenameLeagueDialogOpen) {
        RenameLeagueDialog(
            name = state.renamedLeagueName,
            setName = { onEvent(LeagueSettingsEvent.SetRenamedLeagueName(it)) },
            onRenameClick = { onEvent(LeagueSettingsEvent.RenameLeague) },
            onDismiss = { onEvent(LeagueSettingsEvent.CloseRenameLeagueDialog) }
        )
    }

    if (state.isLeaveLeagueDialogOpen) {
        ConfirmLeaveLeagueDialog(
            onLeaveLeague = { onEvent(LeagueSettingsEvent.LeaveLeague) },
            onDismiss = { onEvent(LeagueSettingsEvent.CloseLeaveLeagueDialog) }
        )
    }
    if (state.isDeleteLeagueDialogOpen) {
        ConfirmDeleteLeagueDialog(
            onDeleteLeague = { onEvent(LeagueSettingsEvent.DeleteLeague) },
            onDismiss = { onEvent(LeagueSettingsEvent.CloseDeleteLeagueDialog) }
        )
    }

    if (state.isGenerateNewLeagueCodeDialogOpen) {
        GenerateNewLeagueCodeDialog(
            onGenerateCode = { onEvent(LeagueSettingsEvent.GenerateNewCode) },
            onDismiss = { onEvent(LeagueSettingsEvent.CloseGenerateNewLeagueCodeDialog) }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TopBar(onBackArrowClick: () -> Unit) {
    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(containerColor = DarkGray),
        title = { Text(text = "League Settings", fontWeight = FontWeight.Medium) },
        navigationIcon = {
            IconButton(onClick = onBackArrowClick) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = null,
                    tint = LightGray
                )
            }
        }
    )
}