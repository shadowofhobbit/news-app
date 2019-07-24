package test.task.newsapp


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment


class EmptyFragment : Fragment() {

    private var page: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        page = arguments?.getInt(ARG_PAGE) ?: 0

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_page, container, false)
        val typedArray = requireContext().resources.obtainTypedArray(R.array.background_colors)
        view.setBackgroundColor(typedArray.getColor(page, 0))
        typedArray.recycle()
        return view
    }

    companion object {
        const val ARG_PAGE = "ARG_PAGE"

        fun newInstance(page: Int): EmptyFragment {
            val args = Bundle()
            args.putInt(ARG_PAGE, page)
            val fragment = EmptyFragment()
            fragment.arguments = args
            return fragment
        }
    }
}