package uz.abbosbek.contactapp.ui

import android.app.Activity
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import uz.abbosbek.contactapp.R
import uz.abbosbek.contactapp.databinding.FragmentAddBinding
import uz.abbosbek.contactapp.db.MyDbHelper
import uz.abbosbek.contactapp.models.ContactInfo
import uz.abbosbek.contactapp.models.MyConstant.UZB_PHONE_NUMBER_CODE


class AddFragment : Fragment() {

    private val binding by lazy { FragmentAddBinding.inflate(layoutInflater) }
    private lateinit var myDbHelper: MyDbHelper

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? { myDbHelper = MyDbHelper(requireContext())

        /** listener for ADD icon*/
        binding.toolbar.setOnMenuItemClickListener {
            if (it.itemId == R.id.menu_save) addUser()

            true
        }

        /** listener for toolbar navigation icon (BACK BUTTON)*/
        binding.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }



        return binding.root
    }

    private fun addUser() {
        val name = binding.edtName.text.toString().trim()
        val surname = binding.edtSurname.text.toString().trim()
        val number = binding.edtNumber.text.toString().trim()
        if (name.isEmpty()) {
            binding.edtName.error = "invalid name"
        } else if (surname.isEmpty()){
            binding.edtSurname.error = "invalid surname"

        }else if (number.length < 9 || !(UZB_PHONE_NUMBER_CODE.contains(number.substring(0, 2).toInt()))
        ) {
            binding.edtNumber.error = "invalid number"
        } else {
            val user = ContactInfo(name,surname, number)
            myDbHelper.addContact(user)
            val imm = requireContext().getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(requireView().windowToken, 0)
            Toast.makeText(context, "Saved", Toast.LENGTH_SHORT).show()
            findNavController().popBackStack()
        }
    }
}