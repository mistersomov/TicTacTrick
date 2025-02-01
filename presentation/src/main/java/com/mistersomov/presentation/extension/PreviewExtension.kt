package com.mistersomov.presentation.extension

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.ui.tooling.preview.Preview

@Preview(
    name = "Light_Portrait",
    group = "Phone",
    device = "spec:width=411dp,height=891dp,orientation=portrait",
    uiMode = UI_MODE_NIGHT_NO,
)
@Preview(
    name = "Light_Landscape",
    group = "Phone",
    device = "spec:width=411dp,height=891dp,orientation=landscape",
    uiMode = UI_MODE_NIGHT_NO,
)
@Preview(
    name = "Dark_Portrait",
    group = "Phone",
    device = "spec:width=411dp,height=891dp,orientation=portrait",
    uiMode = UI_MODE_NIGHT_YES,
)
@Preview(
    name = "Dark_Landscape",
    group = "Phone",
    device = "spec:width=411dp,height=891dp,orientation=landscape",
    uiMode = UI_MODE_NIGHT_YES,
)
annotation class PreviewPhone

@Preview(
    name = "Light_Tablet",
    group = "Tablet",
    device = "spec:width=1280dp,height=891dp",
    uiMode = UI_MODE_NIGHT_NO,
)
@Preview(
    name = "Dark_Tablet",
    group = "Tablet",
    device = "spec:width=1280dp,height=891dp",
    uiMode = UI_MODE_NIGHT_YES,
)
annotation class PreviewTablet

@PreviewPhone
@PreviewTablet
annotation class MultiPreview