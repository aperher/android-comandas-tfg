package tfg.aperher.comandas.presentation.orderstables

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class SpacingItemDecoration(
    private val spanCount: Int,
    private val spacing: Int
) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        outRect.top = if (parent.getChildAdapterPosition(view) < spanCount) {
            spacing * 2
        } else {
            spacing
        }

        outRect.bottom = spacing
        outRect.right = spacing
        outRect.left = spacing
    }
}