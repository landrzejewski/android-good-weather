package pl.training.goodweather.profile.adapter.view

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.jakewharton.rxbinding4.widget.textChanges
import com.squareup.picasso.Picasso
import io.reactivex.rxjava3.core.Observable.combineLatest
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.addTo
import pl.training.goodweather.R
import pl.training.goodweather.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {

    private val viewModel: ProfileViewModel by activityViewModels()
    private lateinit var binding: FragmentProfileBinding
    private val disposables = CompositeDisposable()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentProfileBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        bindViews()
    }

    private fun initViews() {
        Picasso.get()
            .load("https://www.kindpng.com/picc/m/3-36825_and-art-default-profile-picture-png-transparent-png.png")
            .into(binding.photoImageView)
    }

    private fun bindViews() {
        val fullName = binding.fullNameEditText.textChanges().map { it.length > 3 }
        val email = binding.emailNameEditText.textChanges().map { it.contains("@") }
        combineLatest(fullName, email) { fullNameResult, emailResult -> fullNameResult && emailResult}
            .skip(1)
            .subscribe { binding.errorsTextView.text = if (it) "" else getString(R.string.invalidForm) }
            .addTo(disposables)
        fullName.map(::toColor).subscribe { binding.fullNameEditText.setTextColor(it) }
        email.map(::toColor).subscribe { binding.emailNameEditText.setTextColor(it) }
    }

    private fun toColor(isValid: Boolean) = requireContext().getColor(if (isValid) R.color.primaryTextColor else R.color.invalid)

}