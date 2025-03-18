package com.example.fotleague.screens.auth.components

import android.graphics.Matrix
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.asAndroidPath
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.vector.PathParser
import androidx.compose.ui.unit.Dp
import com.example.fotleague.ui.theme.Primary

fun Modifier.drawTopAndBottomCurves(topPadding: Dp): Modifier {
    return this.drawWithCache {
        onDrawBehind {
            val path =
                PathParser()
                    .parsePathString("M0 0H360V56.7889C192.305 10.8926 75.5466 150.969 0 113.578V0Z")
                    .toPath()
            val pathSize = path.getBounds().size
            val matrix = Matrix()
            matrix.postScale(size.width / pathSize.width, size.width / pathSize.width)
            path
                .asAndroidPath()
                .transform(matrix)

            val bottomPath =
                PathParser()
                    .parsePathString("M0 0H360V56.7889C192.305 10.8926 75.5466 150.969 0 113.578V0Z")
                    .toPath()
            bottomPath.moveTo(0f, 0f)
            var bottomPathSize = bottomPath.getBounds().size
            val bottomMatrix = Matrix()
            bottomMatrix.postScale(
                size.width / bottomPathSize.width,
                size.width / bottomPathSize.width
            )
            bottomPath
                .asAndroidPath()
                .transform(bottomMatrix)
            bottomPathSize = bottomPath.getBounds().size

            path.translate(Offset(0f, -topPadding.toPx()))
            drawPath(path, Primary)
            rotate(180f) {
//                bottomPath.translate(Offset(0f, size.height - bottomPathSize.height))
                drawPath(bottomPath, Primary)
            }
        }
    }
}