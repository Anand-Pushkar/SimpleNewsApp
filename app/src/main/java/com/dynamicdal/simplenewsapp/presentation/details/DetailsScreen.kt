package com.dynamicdal.simplenewsapp.presentation.details

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.dynamicdal.simplenewsapp.R
import com.dynamicdal.simplenewsapp.presentation.Dimens.ArticleImageHeight
import com.dynamicdal.simplenewsapp.presentation.Dimens.dp_16
import com.dynamicdal.simplenewsapp.presentation.common.DetailsTopBar
import com.dynamicdal.simplenewsapp.ui.theme.SimpleNewsAppTheme

@Composable
fun DetailsScreen(
    newsUrl: String?,
    imageUrl: String?,
    title: String,
    content: String,
    navigateUp: () -> Unit
) {

    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
    ) {
        DetailsTopBar(
            onBrowsingClick = {
                Intent(Intent.ACTION_VIEW).also {
                    it.data = Uri.parse(newsUrl)
                    if (it.resolveActivity(context.packageManager) != null) {
                        context.startActivity(it)
                    }
                }
            },
            onBackClick = navigateUp
        )

        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(
                start = dp_16,
                end = dp_16,
                top = dp_16
            )
        ) {
            item {

                AsyncImage(
                    model = ImageRequest.Builder(context = context).data(imageUrl)
                        .build(),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(ArticleImageHeight)
                        .clip(MaterialTheme.shapes.medium),
                    contentScale = ContentScale.Crop
                )

                Spacer(modifier = Modifier.height(dp_16))

                Text(
                    text = title,
                    style = MaterialTheme.typography.displaySmall,
                    color = colorResource(
                        id = R.color.text_title
                    )
                )

                Text(
                    text = content,
                    style = MaterialTheme.typography.bodyMedium,
                    color = colorResource(
                        id = R.color.body
                    )
                )
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun DetailsScreenPreview() {
    SimpleNewsAppTheme(dynamicColor = false) {
        DetailsScreen(
            imageUrl = "",
            newsUrl = "",
            title = "This is the title for this news",
            content = "This is content of the news. This is content of the news. This is content of the news" +
                    "This is content of the news. This is content of the news. This is content of the news" +
                    "This is content of the news. This is content of the news. This is content of the news",
            navigateUp = {}
        )
    }
}






