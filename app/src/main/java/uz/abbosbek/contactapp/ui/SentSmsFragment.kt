package uz.abbosbek.contactapp.ui

import android.os.Bundle
import android.telephony.SmsManager
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.navigation.fragment.findNavController
import com.github.florent37.runtimepermission.kotlin.askPermission
import uz.abbosbek.contactapp.R
import uz.abbosbek.contactapp.databinding.FragmentSentSmsBinding
import uz.abbosbek.contactapp.models.ContactInfo


class SentSmsFragment : Fragment() {

    private val binding by lazy { FragmentSentSmsBinding.inflate(layoutInflater) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {val user = arguments?.getSerializable("user") as ContactInfo
        binding.apply {
            tvName.text = user.name
            tvNumber.text = user.number
            btnSend.setOnClickListener {
                val message = edtMessage.text.toString().trim()
                if (message.isNotEmpty()) {
                    askSmsPermission(message, user)
                } else {
                    Toast.makeText(context, "Message is empty", Toast.LENGTH_SHORT).show()
                }
            }

        }

        binding.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

        return binding.root
    }

    private fun askSmsPermission(message: String, user: ContactInfo) {
        askPermission(
            android.Manifest.permission.SEND_SMS
        ) {
            val smsManager = SmsManager.getDefault()
            val parts = smsManager.divideMessage(message)
            smsManager.sendMultipartTextMessage(user.number, null, parts, null, null)
            Toast.makeText(context, "Message is sent", Toast.LENGTH_SHORT).show()
        }.onDeclined { e ->
            if (e.hasDenied()) {

                AlertDialog.Builder(requireContext()).setMessage("Please accept our permissions")
                    .setPositiveButton("ok") { _, _ ->
                        e.askAgain()
                    } //ask again
                    .setNegativeButton("no") { dialog, which ->
                        dialog.dismiss()
                    }.show()
            }

            if (e.hasForeverDenied()) {
                //the list of forever denied permissions, user has check 'never ask again'
                // you need to open setting manually if you really need it
                e.goToSettings()
            }
        }
    }
}