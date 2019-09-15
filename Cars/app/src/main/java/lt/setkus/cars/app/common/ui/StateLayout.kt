package lt.setkus.cars.app.common.ui

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import androidx.annotation.LayoutRes
import lt.setkus.cars.R

class StateLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private var layoutState: State = State.NONE

    private var contentLayout: View? = null
    private var loadingLayout: View? = null
    private var errorLayout: View? = null

    @LayoutRes
    private var loadingLayoutRes: Int
    @LayoutRes
    private var errorLayoutRes: Int

    init {
        if (isInEditMode) {
            layoutState = State.CONTENT
        }

        context.obtainStyledAttributes(attrs, R.styleable.StateLayout, 0, 0).apply {
            try {
                loadingLayoutRes = getResourceId(R.styleable.StateLayout_loadingLayout, R.layout.state_loading)
                errorLayoutRes = getResourceId(R.styleable.StateLayout_errorLayout, R.layout.state_error)
            } finally {
                recycle()
            }
        }
    }

    private fun updateLoadingVisibility(visibility: Int) =
        when (visibility) {
            VISIBLE -> loadingLayout?.visibility = VISIBLE
            else -> loadingLayout?.visibility = GONE
        }

    override fun onFinishInflate() {
        super.onFinishInflate()
        prepareContentLayout()
        prepareLoadingLayout()
        prepareErrorLayout()
        updateWithState()
    }

    private fun updateWithState() {
        when (layoutState) {
            State.LOADING -> loading()
            State.CONTENT -> content()
            State.ERROR -> error()
            State.NONE -> content()
            else -> hideAll()
        }
    }

    fun loading(): StateLayout {
        layoutState = State.LOADING
        updateLoadingVisibility(VISIBLE)
        contentLayout?.visibility = GONE
        return this
    }

    private fun hideAll() {
        contentLayout?.visibility = GONE
        loadingLayout?.visibility = GONE
    }

    fun content(): StateLayout {
        layoutState = State.CONTENT
        updateLoadingVisibility(GONE)
        contentLayout?.visibility = VISIBLE
        return this
    }

    fun error(): StateLayout {
        layoutState = State.ERROR
        updateLoadingVisibility(GONE)
        errorLayout?.visibility = View.VISIBLE
        return this
    }

    private fun prepareLoadingLayout() {
        loadingLayout = LayoutInflater.from(context).inflate(loadingLayoutRes, null)
        loadingLayout?.visibility = GONE
        addView(loadingLayout, LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, Gravity.CENTER))
    }

    private fun prepareContentLayout() {
        contentLayout = getChildAt(0)
        contentLayout?.visibility = GONE
    }

    private fun prepareErrorLayout() {
        errorLayout = LayoutInflater.from(context).inflate(errorLayoutRes, null)
        errorLayout?.visibility = GONE
        addView(errorLayout, LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, Gravity.CENTER))
    }

    enum class State {
        LOADING, CONTENT, ERROR, EMPTY, NONE
    }
}