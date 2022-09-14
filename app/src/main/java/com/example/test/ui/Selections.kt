package com.example.test.ui

import com.example.test.ui.base.OnChange
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map

interface SelectionState {

    /**
     * Whether the item is checked or not.
     */
    fun isChecked(id: String): Boolean
}

/**
 * In-memory handling of check state.
 */
class Selections : SelectionState {

    private val checkedIds = mutableSetOf<String>()
    private val checkedIdsFlow =
        MutableStateFlow(OnChange(checkedIds))

    /**
     * Select the item if it is not selected and vise versa.
     */
    fun toggle(id: String) {
        if (checkedIds.contains(id)) {
            checkedIds.remove(id)
        } else {
            checkedIds.add(id)
        }
        checkedIdsFlow.value = OnChange(checkedIds)
    }

    /**
     * Whether the item is selected or not.
     */
    override fun isChecked(id: String) = checkedIds.contains(id)

    /**
     * Listen for the selection state changes.
     */
    fun flow(): Flow<SelectionState> = checkedIdsFlow.map { this }

}