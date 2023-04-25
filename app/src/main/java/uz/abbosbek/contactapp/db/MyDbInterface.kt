package uz.abbosbek.contactapp.db

import uz.abbosbek.contactapp.models.ContactInfo

interface MyDbInterface {
    fun addContact(contactInfo: ContactInfo)
    fun getAllContacts():ArrayList<ContactInfo>
    fun deleteContact(contactInfo: ContactInfo)
    fun editContact(contactInfo: ContactInfo):Int
}