package com.example.post.ui

import android.app.Application
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.post.PostApplication
import com.example.post.ui.home.HomeViewModel
import com.example.post.ui.item.ItemDetailsViewModel
import com.example.post.ui.item.ItemEditViewModel
import com.example.post.ui.item.ItemEntryViewModel

object AppViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            ItemEditViewModel(
                this.createSavedStateHandle(),
                inventoryApplication().container.itemsRepository
            )
        }

        initializer {
            ItemEntryViewModel(inventoryApplication().container.itemsRepository)
        }


        initializer {
            ItemDetailsViewModel(
                this.createSavedStateHandle(),
                inventoryApplication().container.itemsRepository
            )
        }

        initializer {
            HomeViewModel(inventoryApplication().container.itemsRepository)
        }
    }
}

fun CreationExtras.inventoryApplication(): PostApplication =
    (this[AndroidViewModelFactory.APPLICATION_KEY] as PostApplication)
