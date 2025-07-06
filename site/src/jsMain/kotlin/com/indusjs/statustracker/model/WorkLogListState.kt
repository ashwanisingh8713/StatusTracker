package com.indusjs.statustracker.model

import com.indusjs.domain.model.WorkLogResponse

data class WorkLogListState(val workLogListResponse: ResourceUiState<List<WorkLogResponse>>)
