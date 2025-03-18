package com.example.fotleague.ui.navigation

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.example.fotleague.R
import com.example.fotleague.Screen
import com.example.fotleague.ui.theme.DarkGray
import com.example.fotleague.ui.theme.LightGray
import com.example.fotleague.ui.theme.Primary

private data class BottomNavigationItem(
    val title: String,
    val screen: Screen,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
)

@Composable
fun BottomNavigation(
    navController: NavHostController,
    navBackStackEntry: NavBackStackEntry?
) {

    val items = listOf(
        BottomNavigationItem(
            title = "Matches",
            screen = Screen.Matches,
            selectedIcon = ImageVector.vectorResource(id = R.drawable.sports_32),
            unselectedIcon = ImageVector.vectorResource(id = R.drawable.sports_24)
        ), BottomNavigationItem(
            title = "Leagues",
            screen = Screen.Leagues,
            selectedIcon = ImageVector.vectorResource(id = R.drawable.groups_filled_32),
            unselectedIcon = ImageVector.vectorResource(id = R.drawable.groups_24)
        ), BottomNavigationItem(
            title = "Leaderboard",
            screen = Screen.Leaderboard,
            selectedIcon = ImageVector.vectorResource(id = R.drawable.trophy_filled_32),
            unselectedIcon = ImageVector.vectorResource(id = R.drawable.trophy_24)
        ), BottomNavigationItem(
            title = "Stats",
            screen = Screen.Stats,
            selectedIcon = ImageVector.vectorResource(id = R.drawable.leaderboard_filled_32),
            unselectedIcon = ImageVector.vectorResource(id = R.drawable.leaderboard_24)
        ), BottomNavigationItem(
            title = "More",
            screen = Screen.More,
            selectedIcon = ImageVector.vectorResource(id = R.drawable.menu_32),
            unselectedIcon = ImageVector.vectorResource(id = R.drawable.menu_24)
        )
    )

    NavigationBar(
        modifier = Modifier.height(120.dp),
        containerColor = DarkGray,
    ) {
        val currentDestination = navBackStackEntry?.destination

        items.forEach { item ->
            val isSelected = currentDestination?.hasRoute(item.screen::class) == true
                    ||
                    item.screen == Screen.Leagues && currentDestination?.hasRoute(Screen.LeagueDetails::class) == true

            NavigationBarItem(selected = isSelected, label = {
                Text(
                    text = item.title,
                    fontSize = 10.sp,
                    color = if (isSelected) Primary else LightGray,
                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                )
            }, onClick = {
                navController.navigate(item.screen) {
                    popUpTo(navController.graph.findStartDestination().id) {
                        saveState = true
                    }
                    // Avoid multiple copies of the same destination when
                    // reselecting the same item
                    launchSingleTop = true
                    // Restore state when reselecting a previously selected item
                    restoreState = true
                }
            }, colors = NavigationBarItemDefaults.colors(indicatorColor = DarkGray), icon = {
                Icon(
                    imageVector = if (isSelected) item.selectedIcon else item.unselectedIcon,
                    contentDescription = item.title,
                    tint = if (isSelected) Primary else LightGray,
                    modifier = Modifier.size(if (isSelected) 32.dp else 24.dp)
                )
            })
        }
    }
}