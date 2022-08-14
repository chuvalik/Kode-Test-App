package com.example.feature.presentation.home.epoxy

import com.airbnb.epoxy.TypedEpoxyController
import com.example.feature.domain.model.DomainDataSource
import com.example.feature.presentation.home.epoxy.model.HomeLoadingHeaderModel
import com.example.feature.presentation.home.epoxy.model.HomeUserItem
import com.example.feature.presentation.home.epoxy.model.ShimmerUserItemModel
import com.example.feature.presentation.home.model.UiState

class HomeEpoxyController(
    private val onMoveToDetail: (DomainDataSource) -> Unit,
) : TypedEpoxyController<UiState>() {

    override fun buildModels(state: UiState?) {
        if (state?.isLoading == true && state.isInit) {
            HomeLoadingHeaderModel(state.departmentFilter)
                .id("shimmer_loading_header")
                .addTo(this)

            repeat(8) {
                ShimmerUserItemModel()
                    .id("shimmer_user_item_$it")
                    .addTo(this)
            }
        } else {
            state?.data?.forEach { item ->
                HomeUserItem(item, state.sortType, onMoveToDetail)
                    .id("user_${item.id}")
                    .addTo(this)
            }
        }
    }
}