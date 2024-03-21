package com.example.diplomaproject.login

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.diplomaproject.R
import com.example.diplomaproject.core.repository.SharedPreferencesRepo
import com.example.diplomaproject.core.state.UpdateProfileState
import com.example.diplomaproject.databinding.FragmentProfileInfoBinding
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import javax.inject.Inject

private const val PICK_IMAGE_REQUEST = 1
private val MEDIA_TYPE_TEXT = "text/plain".toMediaType()
private val MEDIA_TYPE_IMAGE = "image/*".toMediaType()

@AndroidEntryPoint
class ProfileInfoFragment : Fragment(R.layout.fragment_profile_info) {
    @Inject lateinit var sharedPreferencesRepo: SharedPreferencesRepo
    private val viewBinding: FragmentProfileInfoBinding by viewBinding()
    private val viewModel: ProfileInfoViewModel by viewModels()

    private lateinit var username: RequestBody
    private lateinit var dateOfBirthday: RequestBody
    private lateinit var country: RequestBody
    private lateinit var profileImage: MultipartBody.Part

    private var filePath = ""
    private lateinit var file: File

    private lateinit var resultLauncher: ActivityResultLauncher<Intent>

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)

        initResultLauncher()
        initActions()
        initObserver()
    }

    private fun initResultLauncher() {
        resultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == PICK_IMAGE_REQUEST &&
                    result.resultCode == Activity.RESULT_OK &&
                    result.data != null && result.data?.data != null
                ) {
                    val selectedImageUri = result.data!!.data
                    val filePathColumn = arrayOf(MediaStore.Images.Media.DATA)

                    val cursor =
                        selectedImageUri?.let {
                            activity?.contentResolver?.query(
                                it,
                                filePathColumn,
                                null,
                                null,
                                null,
                            )
                        }
                    cursor?.moveToFirst()

                    val columnIndex = cursor?.getColumnIndex(filePathColumn[0])
                    filePath = columnIndex?.let { cursor.getString(it) }.toString()

                    Glide.with(this)
                        .asBitmap()
                        .load(selectedImageUri)
                        .apply(RequestOptions.circleCropTransform())
                        .into(viewBinding.imageView)

                    cursor?.close()
                }
            }
    }

    private fun initActions() =
        with(viewBinding) {
            setUpDropDownMenu()

            imageView.setOnClickListener {
                openGallery()
            }

            buttonSave.setOnClickListener {
                username = fieldUsername.text.toString().toRequestBody(MEDIA_TYPE_TEXT)
                dateOfBirthday =
                    "${dropdownMenuYear.text}-${dropdownMenuMonth.text}-${dropdownMenuDay.text}".toRequestBody(
                        MEDIA_TYPE_TEXT,
                    )
                country = dropdownMenuCountry.text.toString().toRequestBody(MEDIA_TYPE_TEXT)

                file = File(filePath)

                profileImage =
                    MultipartBody.Part.createFormData(
                        "profileImage",
                        file.name,
                        file.asRequestBody(MEDIA_TYPE_IMAGE),
                    )

                viewModel.updateProfile(
                    sharedPreferencesRepo.getUserAccessToken(),
                    username,
                    dateOfBirthday,
                    country,
                    profileImage,
                )
            }
        }

    private fun setUpDropDownMenu() =
        with(viewBinding) {
            dropdownContainerDay.isVisible = true
            dropdownContainerMonth.isVisible = true
            dropdownContainerYear.isVisible = true
            if (dropdownMenuDay.isVisible) {
                val items =
                    listOf(
                        "1",
                        "2",
                        "3",
                        "4",
                        "5",
                        "6",
                        "7",
                        "8",
                        "9",
                        "10",
                        "11",
                        "12",
                        "13",
                        "14",
                        "15",
                        "16",
                        "17",
                        "18",
                        "19",
                        "20",
                        "21",
                        "22",
                        "23",
                        "24",
                        "25",
                        "26",
                        "27",
                        "28",
                        "29",
                        "30",
                        "31",
                    )
                dropdownMenuDay.setUpDropdownMenu(items)
            }
            if (dropdownContainerMonth.isVisible) {
                val items =
                    listOf(
                        "january",
                        "february",
                        "march",
                        "april",
                        "may",
                        "june",
                        "july",
                        "august",
                        "september",
                        "october",
                        "november",
                        "december",
                    )
                dropdownMenuMonth.setUpDropdownMenu(items)
            }
            if (dropdownContainerYear.isVisible) {
                val items =
                    listOf(
                        "1990",
                        "1991",
                        "1992",
                        "1993",
                        "1994",
                        "1995",
                        "1996",
                        "1997",
                        "1998",
                        "1999",
                        "2000",
                        "2001",
                        "2002",
                        "2003",
                        "2004",
                        "2005",
                        "2006",
                        "2007",
                        "2008",
                        "2009",
                        "2010",
                        "2011",
                        "2012",
                        "2013",
                    )
                dropdownMenuYear.setUpDropdownMenu(items)
            }
            val itemCountry = listOf("Kazakhstan", "Turkey")
            dropdownMenuCountry.setUpDropdownMenu(itemCountry)

            layoutt.setOnClickListener {
                hideKeyboard()
            }
        }

    private fun initObserver() {
        viewModel.profileStateLiveData.observe(viewLifecycleOwner, ::handleState)
    }

    private fun handleState(state: UpdateProfileState) {
        when (state) {
            is UpdateProfileState.Failed -> {
                Toast.makeText(requireContext(), state.message, Toast.LENGTH_LONG).show()
            }

            is UpdateProfileState.Loading -> {}
            is UpdateProfileState.Success -> {
                if (state.message == "CONTINUE") {
                    Toast.makeText(
                        requireContext(),
                        "Profile Updated",
                        Toast.LENGTH_SHORT,
                    ).show()

                    sharedPreferencesRepo.apply {
                        setUsername(username.toString())
                        setUserImage(filePath)
                    }
                    findNavController().navigate(R.id.action_profileInfoFragment_to_homeActivity)
                }
            }
        }
    }

    private fun openGallery() {
        val requestPermission =
            registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
                if (isGranted) {
                    val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                    resultLauncher.launch(gallery)
                    Log.i("DEBUG", "permission granted")
                } else {
                    Log.i("DEBUG", "permission denied")
                }
            }

        requestPermission.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
    }
}

fun Fragment.hideKeyboard() {
    view?.let { activity?.hideKeyboard(it) }
}

fun Context.hideKeyboard(view: View) {
    val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}

fun AutoCompleteTextView.setUpDropdownMenu(itemsList: List<String>) {
    val adapter = ArrayAdapter(this.context, R.layout.item_text, itemsList)
    this.setAdapter(adapter)
}
