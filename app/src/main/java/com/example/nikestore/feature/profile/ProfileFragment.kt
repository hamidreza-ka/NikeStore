package com.example.nikestore.feature.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.nikestore.R
import com.example.nikestore.core.NikeFragment
import com.example.nikestore.feature.auth.AuthActivity
import com.example.nikestore.feature.favorites.FavoriteProductsActivity
import kotlinx.android.synthetic.main.fragment_profile.*
import org.koin.android.viewmodel.ext.android.viewModel

class ProfileFragment : NikeFragment() {

    private val viewModel: ProfileViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        favoriteProductsBtn.setOnClickListener {
            startActivity(Intent(requireContext(), FavoriteProductsActivity::class.java))
        }
    }

    override fun onResume() {
        super.onResume()
        checkAuthState()
    }

    private fun checkAuthState() {
        if (viewModel.isSignedIn) {
            authBtn.text = getString(R.string.signOut)
            authBtn.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_sign_out, 0)
            usernameTv.text = viewModel.userName
            authBtn.setOnClickListener {
                viewModel.signOut()
                checkAuthState()
            }
        } else {
            authBtn.text = getString(R.string.signIn)
            authBtn.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_sign_in, 0)
            authBtn.setOnClickListener {
                startActivity(Intent(requireContext(), AuthActivity::class.java))
            }

            usernameTv.text = getString(R.string.guestUser)
        }
    }
}