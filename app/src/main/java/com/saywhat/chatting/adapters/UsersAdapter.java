package com.saywhat.chatting.adapters;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.saywhat.chatting.databinding.ItemContainerUserBinding;
import com.saywhat.chatting.listeners.UserListener;
import com.saywhat.chatting.models.User;
import java.util.Base64;
import java.util.List;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.UserViewHolder> {
    private final List<User> users;
    private final UserListener userListener;

    public UsersAdapter(List<User> users, UserListener userListener) {
        this.users = users;
        this.userListener = userListener;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemContainerUserBinding itemContainerUserBinding = ItemContainerUserBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false
        );
        return new UserViewHolder(itemContainerUserBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        holder.setUserData(users.get(position));
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    class UserViewHolder extends RecyclerView.ViewHolder {
        ItemContainerUserBinding binding;

        UserViewHolder(ItemContainerUserBinding itemContainerUserBinding) {
            super(itemContainerUserBinding.getRoot());
            binding = itemContainerUserBinding;
        }

        void setUserData(User user) {
            binding.textName.setText(user.name);
            binding.textEmail.setText(user.email);
            binding.imageProfile.setImageBitmap(getUserImage(user.image));
            binding.getRoot().setOnClickListener(v -> userListener.onUserClicked(user));
        }
    }

//    private Bitmap getUserImage(String encodedImage) {
//        Log.d("UserImageDebug", "Image String: " + encodedImage);
//        byte[] bytes = Base64.getUrlDecoder().decode(encodedImage);
//        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
//    }
    
    private Bitmap getUserImage(String encodedImage) {
        Log.d("UserImageDebug", "Image String: " + encodedImage);

        try {
            String base64String = encodedImage.replace('-', '+').replace('_', '/');
            byte[] bytes = Base64.getDecoder().decode(base64String);
            return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        } catch (IllegalArgumentException e) {
            Log.e("UserImageDebug", "Failed to decode image: " + e.getMessage());
            return null;
        }
    }
}
