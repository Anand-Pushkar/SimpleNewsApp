package com.dynamicdal.simplenewsapp.data.mappers

import com.dynamicdal.simplenewsapp.data.local.enitites.SourceEntity
import com.dynamicdal.simplenewsapp.data.network.models.SourceAM
import com.dynamicdal.simplenewsapp.domain.models.Source

fun SourceAM.toSourceEntity(): SourceEntity =
    SourceEntity(
        id = id,
        name = name
    )

fun SourceEntity.toSource(): Source =
    Source(
        id = id,
        name = name
    )