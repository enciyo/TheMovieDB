package com.enciyo.themoviedb.ext

import androidx.annotation.CheckResult
import androidx.appcompat.widget.SearchView
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged


@FlowPreview
@CheckResult
fun SearchView.queryTextChanges(
    debounce: Long = 500,
) = callbackFlow<CharSequence> {
    val listener = object : SearchView.OnQueryTextListener {
        override fun onQueryTextChange(newText: String): Boolean {
            trySend(newText)
            return true
        }

        override fun onQueryTextSubmit(query: String): Boolean = false
    }
    setOnQueryTextListener(listener)
    awaitClose { setOnQueryTextListener(null) }
}
    .conflate()
    .debounce(debounce)
    .distinctUntilChanged()