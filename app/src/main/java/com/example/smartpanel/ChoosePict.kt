import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import android.widget.ImageButton
import android.widget.ImageView

class ChoosePict(private val activity: Activity, private val profileImage: ImageView, private val choosePhotoButton: ImageButton) {

    private val PICK_IMAGE_REQUEST = 1

    init {
        // Menangani klik pada tombol ImageButton
        choosePhotoButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            activity.startActivityForResult(intent, PICK_IMAGE_REQUEST)
        }
    }

    // Menangani hasil dari memilih foto
    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.data != null) {
            val selectedImageUri: Uri = data.data!!
            profileImage.setImageURI(selectedImageUri)
        }
    }
}
