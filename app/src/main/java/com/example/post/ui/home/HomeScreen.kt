package com.example.post.ui.home

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.post.PostTopAppBar
import com.example.post.R
import com.example.post.data.Item
import com.example.post.ui.AppViewModelProvider
import com.example.post.ui.navigation.NavigationDestination
import com.example.post.ui.theme.PostTheme

object HomeDestination : NavigationDestination {
    override val route = "home"
    override val titleRes = R.string.all_post
}

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreen(
    navigateToItemEntry: () -> Unit,
    navigateToItemUpdate: (Int) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val homeUiState by viewModel.homeUiState.collectAsState()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            PostTopAppBar(
                title = stringResource(HomeDestination.titleRes),
                canNavigateBack = false,
                scrollBehavior = scrollBehavior
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = navigateToItemEntry,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_large))
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(R.string.add_post)
                )
            }
        },
    ) { innerPadding ->
        HomeBody(
            itemList = homeUiState.itemList,
            onItemClick = navigateToItemUpdate,
            modifier = modifier
                .padding(innerPadding)
                .fillMaxSize()
        )
    }
}

@Composable
private fun HomeBody(
    itemList: List<Item>, onItemClick: (Int) -> Unit, modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        if (itemList.isEmpty()) {
            Text(
                text = stringResource(R.string.no_item_description),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleLarge
            )
        } else {
            PostList(
                itemList = itemList,
                onItemClick = { onItemClick(it.id) },
                modifier = Modifier
                    .padding(horizontal = dimensionResource(id = R.dimen.padding_small))
            )
        }
    }
}

@Composable
private fun PostList(
    itemList: List<Item>, onItemClick: (Item) -> Unit, modifier: Modifier = Modifier
) {
    LazyColumn(modifier = modifier) {
        items(items = itemList, key = { it.id }) { item ->
            PostItem(
                item = item,
                onItemClick =onItemClick,
                modifier = Modifier
                    .padding(dimensionResource(id = R.dimen.padding_small))
                    .clickable { onItemClick(item) })
        }
    }
}

@Composable
private fun PostItem(
    item: Item, onItemClick: (Item) -> Unit ,modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier, elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(id = R.dimen.padding_large)),
            verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_small))
        ) {
            Text(
                text = item.title,
                style = MaterialTheme.typography.titleLarge,
            )
            Text(
                text = item.description,
                style = MaterialTheme.typography.titleMedium,
                maxLines = 2
            )
            Row (horizontalArrangement = Arrangement.SpaceEvenly){
                Row (modifier = Modifier.padding(10.dp)){
                    ItemDetailsfav(
                        icon = Icons.Default.Favorite,
                        ThumbNo = "5"
                    )
                    Spacer(modifier = Modifier.width(40.dp))
                    ItemDetailsfav(
                        icon = Icons.Default.FavoriteBorder,
                        ThumbNo = "2",
                        modifier = Modifier.rotate(270f)
                    )   
                }
                Spacer(modifier = Modifier.weight(1f))
                Button(
                    onClick = { onItemClick(item) },
                    shape = MaterialTheme.shapes.small
//                    modifier = Modifier.clickable { onItemClick(item)}
                ) {
                    Text(text = stringResource(R.string.read_more))
                }
                }
            }
        }
    }


@Composable
private fun ItemDetailsfav(
    icon : ImageVector, ThumbNo:String, modifier: Modifier= Modifier
) {
    Row {
        Icon(
            imageVector = icon,
            contentDescription = stringResource(R.string.edit_post),
            modifier = Modifier
        )
        Spacer(modifier = Modifier.width(5.dp))
        Text(text = ThumbNo)
    }
}

@Preview(showBackground = true)
@Composable
fun HomeBodyPreview() {
    PostTheme {
        HomeBody(listOf(
            Item(1, "Game", "", ""),
            Item(2, "Pen", "", ""),
            Item(3, "TV", "", "")
        ), onItemClick = {})
    }
}

@Preview(showBackground = true)
@Composable
fun HomeBodyEmptyListPreview() {
    PostTheme {
        HomeBody(listOf(), onItemClick = {})
    }
}


