package com.android.traveltube.ui.mypage

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.android.traveltube.R
import com.android.traveltube.databinding.FragmentMyVideoBinding
import android.app.AlertDialog
import android.content.Intent
import android.graphics.Color
import android.provider.MediaStore
import android.text.Editable
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.TextWatcher
import android.text.style.ForegroundColorSpan
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.ColorDrawable
import android.util.Log
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import java.io.ByteArrayOutputStream
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.traveltube.data.db.VideoSearchDatabase
import com.android.traveltube.databinding.DialogMypageBinding
import com.android.traveltube.factory.SharedViewModelFactory
import com.android.traveltube.model.db.VideoBasicModel
import com.android.traveltube.repository.YoutubeRepositoryImpl
import com.android.traveltube.viewmodel.SharedViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog

class MyVideoFragment : Fragment() {

    private var _binding: FragmentMyVideoBinding? = null
    private val binding get() = _binding
    private lateinit var sharedPref: SharedPreferences
    private lateinit var ivProfile: ImageView
    private lateinit var etName: EditText
    private lateinit var btnConfirm: Button
    private lateinit var btnGallery: Button
    private lateinit var btnCancel: Button
    private lateinit var btnEditImage: ImageView
    private lateinit var tvCharCount: TextView
    private val galleryRequestCode = 1
    private val maxNameLength = 6
    private var selectImage: Bitmap? = null
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: MyVideoAdapter
    private lateinit var bottomSheetDialog: BottomSheetDialog
    private var adapterToDelete: Int = RecyclerView.NO_POSITION

    private val watchHistoryViewModel: WatchHistoryViewModel by viewModels {
        WatchHistoryViewModelFactory(
            YoutubeRepositoryImpl(VideoSearchDatabase.getInstance(requireContext()))
        )
    }

    private val sharedViewModel by activityViewModels<SharedViewModel> {
        SharedViewModelFactory(YoutubeRepositoryImpl(VideoSearchDatabase.getInstance(requireContext())))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMyVideoBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initViewModel()
        setUpSpinner()

        sharedPref = requireContext().getSharedPreferences("profile_data", Context.MODE_PRIVATE)
        val savedName = sharedPref.getString("name", "")
        binding?.tvMyVideoName?.text = savedName
        val savedImageBytes = sharedPref.getString("image_bitmap", null)?.let { decodeBitmap(it) }
        if (savedImageBytes != null) {
            binding?.ivPfImage?.setImageBitmap(savedImageBytes)
        } else {
            binding?.ivPfImage?.setImageResource(R.drawable.ic_default_image)
        }
        binding?.ivImageEdit?.setOnClickListener {
            showCustomDialog()
        }

        recyclerView = view.findViewById(R.id.rc_myVideo)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter
        bottomSheetDialog = BottomSheetDialog(requireContext())
        val bottomSheetView = layoutInflater.inflate(R.layout.bottom_sheet_layout, null)
        bottomSheetDialog.setContentView(bottomSheetView)

        bottomSheetView.findViewById<Button>(R.id.btn_bottomDialog_confirm).setOnClickListener {
            val adapterPosition = adapterToDelete
            val itemToDelete =
                (recyclerView.adapter as MyVideoAdapter).currentList.getOrNull(adapterPosition)
            if (itemToDelete != null) {
                deleteItem(itemToDelete)
                // TODO
                watchHistoryViewModel.deleteWatchHistoryItem(itemToDelete.id)
            }
            bottomSheetDialog.dismiss()
        }
        bottomSheetView.findViewById<Button>(R.id.btn_bottomDialog_cancel).setOnClickListener {
            bottomSheetDialog.dismiss()
        }
        val etMyVideoHistory = view.findViewById<EditText>(R.id.et_myVideo_history)
        etMyVideoHistory.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val searchText = s.toString()
                searchHistory(searchText)
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
    }

    private fun searchHistory(query: String) {
        adapter.filter(query)
        Log.d("TAG", "$query")
    }

    private fun setUpSpinner() {
    }

    private fun initView() {
        adapter = MyVideoAdapter(
            onItemClick = { video ->
            },
            onItemLongClick = { video, position ->
                adapterToDelete = position
                bottomSheetDialog.show()
            }
        )
    }

    private fun deleteItem(video: VideoBasicModel) {
        // adapter.deleteItem(video)
    }

    private fun initViewModel() = with(watchHistoryViewModel) {
        historyVideos.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showCustomDialog() {
        val dialogBinding = DialogMypageBinding.inflate(layoutInflater)
        val dialog = AlertDialog.Builder(requireActivity()).create()
        dialog.setView(dialogBinding.root)

        ivProfile = dialogBinding.ivPfImage
        etName = dialogBinding.etDialogEdit
        btnConfirm = dialogBinding.btnDialogConfirm
        btnCancel = dialogBinding.btnDialogCancel
        btnGallery = dialogBinding.btnGallerySelect
        btnEditImage = dialogBinding.ivImageEdit
        tvCharCount = dialogBinding.tvCharCount

        val savedName = sharedPref.getString("name", "")
        val savedImageBytes = sharedPref.getString("image_bitmap", null)?.let { decodeBitmap(it) }

        etName.setText(savedName)
        tvCharCount.text = savedName?.let { getFormattedCharCountText(it.length) }

        if (savedImageBytes != null) {
            ivProfile.setImageBitmap(savedImageBytes)
        } else {
            ivProfile.setImageResource(R.drawable.ic_default_image)
        }

        if (savedImageBytes != null) {
            btnEditImage.visibility = View.VISIBLE
        } else {
            btnEditImage.visibility = View.GONE
        }

        btnGallery.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(intent, galleryRequestCode)
        }

        btnConfirm.setOnClickListener {
            etName.clearFocus()
            val inputMethodManager =
                requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(etName.windowToken, 0)

            val newName = etName.text.toString()

            if (newName.matches(Regex("^[a-zA-Z0-9가-힣]*$")) && newName.length in 1..maxNameLength) {
                sharedPref.edit().apply {
                    putString("name", newName)
                    putString("image_bitmap", encodeBitmap(selectImage))
                    apply()
                }

                updateName(newName)
                selectImage?.let { bitmap ->
                    updateImage(bitmap)
                }

                if (selectImage == null) {
                    binding?.ivPfImage?.setImageResource(R.drawable.ic_default_image)
                }

                dialog.dismiss()
            } else {
                Toast.makeText(
                    requireContext(),
                    "한글, 영어, 숫자만 입력 가능하며 최대 6글자까지 입력 가능합니다.",
                    Toast.LENGTH_SHORT
                ).show()
            }

            ivProfile.setImageResource(R.drawable.ic_default_image)
            selectImage = null
            btnEditImage.visibility = View.GONE
        }

        btnCancel.setOnClickListener {
            etName.clearFocus()
            val inputMethodManager =
                requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(etName.windowToken, 0)

            selectImage?.let { bitmap ->
                ivProfile.setImageBitmap(bitmap)
                btnEditImage.visibility = View.VISIBLE
            }

            dialog.dismiss()
        }

        btnEditImage.setOnClickListener {
            ivProfile.setImageResource(R.drawable.ic_default_image)
            selectImage = null
            btnEditImage.visibility = View.GONE
        }

        etName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val length = s?.length ?: 0
                tvCharCount.text = getFormattedCharCountText(length)
                btnConfirm.isEnabled = length in 1..maxNameLength
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        dialog.show()
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }

    private fun updateName(newName: String) {
        binding?.root?.findViewById<TextView>(R.id.tv_myVideo_name)?.text = newName
    }

    private fun updateImage(newImageBitmap: Bitmap) {
        binding?.root?.findViewById<ImageView>(R.id.iv_pf_image)?.setImageBitmap(newImageBitmap)
    }

    private fun getFormattedCharCountText(length: Int): CharSequence {
        val coloredText = SpannableStringBuilder("$length/$maxNameLength")
        val textColor = if (length == maxNameLength) Color.RED else Color.BLACK
        coloredText.setSpan(
            ForegroundColorSpan(textColor),
            0,
            1,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        return coloredText
    }

    private fun encodeBitmap(bitmap: Bitmap?): String {
        val outputStream = ByteArrayOutputStream()
        bitmap?.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
        val byteArray = outputStream.toByteArray()
        return android.util.Base64.encodeToString(byteArray, android.util.Base64.DEFAULT)
    }

    private fun decodeBitmap(codeString: String): Bitmap? {
        val codeByteArray = android.util.Base64.decode(codeString, android.util.Base64.DEFAULT)
        return BitmapFactory.decodeByteArray(codeByteArray, 0, codeByteArray.size)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == galleryRequestCode && resultCode == Activity.RESULT_OK && data != null) {
            val selectedImageUri = data.data
            selectedImageUri?.let {
                val inputStream = requireContext().contentResolver.openInputStream(it)
                selectImage = BitmapFactory.decodeStream(inputStream)
                ivProfile.setImageBitmap(selectImage)
                btnEditImage.visibility = View.VISIBLE
            }
        }
    }
}