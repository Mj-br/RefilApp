package es.refil.common.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import es.refil.data.models.BottomNavItem

@Composable
fun BottomNavigationBar(
    items: List<BottomNavItem>,
    navController: NavHostController, //We need the navHostController to know which screen we are on (to know if is selected or not)
    modifier: Modifier = Modifier,
    onItemClick: (BottomNavItem) -> Unit,
    showBottomBar : Boolean = false
) {
    val backstackEntry =
        navController.currentBackStackEntryAsState() // We set the variable backstackEntry to save the state of the current button
    BottomNavigation(
        modifier = modifier,
        backgroundColor = Color.Transparent, //TODO: Cambiar el color a theme de la app
        elevation = 5.dp
    ) {
        items.forEach { item ->
            val selected =
                item.route == backstackEntry.value?.destination?.route //We check if the item is selected, this helps to know if the item is selected comparing it the item itself (item.route) with the current destination
            BottomNavigationItem(
                alwaysShowLabel = showBottomBar,
                selected = selected,
                onClick = {
                    onItemClick(item)
                },
                selectedContentColor = MaterialTheme.colors.primary, //TODO: Cambiar el color a theme de la app
                unselectedContentColor = Color.Gray, //TODO: Cambiar el color a theme de la app
                icon = {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        if (item.badgeCount > 0) {
                            BadgedBox(badge = {
                                Text(text = item.badgeCount.toString())
                            }) {
                                Icon(
                                    imageVector = item.icon,
                                    contentDescription = item.name
                                )

                            }
                        } else {
                            Icon(
                                imageVector = item.icon,
                                contentDescription = item.name
                            )
                        }
                        if (selected) {
                            Text(
                                text = item.name,
                                textAlign = TextAlign.Center,
                                fontSize = 10.sp
                            )

                        }

                    }

                }
            )
        }

    }
}