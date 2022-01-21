package com.example.lesson25.ui.form;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.lesson25.App;
import com.example.lesson25.R;
import com.example.lesson25.data.models.Post;
import com.example.lesson25.databinding.FragmentFormBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FormFragment extends Fragment {

    private static final int GROUP_ID = 38;
    private static final int USER_ID = 0;
    private Post post;

    private FragmentFormBinding binding;

    public FormFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentFormBinding.inflate(inflater,
                container,
                false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initBundle();
        poster();

    }

    private void poster() {
        binding.btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = binding.etTitle.getText().toString();
                String content = binding.etContent.getText().toString();
                post = new Post(
                        title,
                        content,
                        USER_ID,
                        GROUP_ID
                );
                if (getArguments()!=null ){
                    App.api.updatePost(post.getId(),post).enqueue(new Callback<Post>() {
                        @Override
                        public void onResponse(Call<Post> call, Response<Post> response) {
                            close();
                        }

                        @Override
                        public void onFailure(Call<Post> call, Throwable t) {

                        }
                    });
                }else {
                    App.api.createPost(post).enqueue(new Callback<Post>() {
                        @Override
                        public void onResponse(Call<Post> call, Response<Post> response) {
                            close();
                        }

                        @Override
                        public void onFailure(Call<Post> call, Throwable t) {

                        }
                    });
                }
            }
        });
    }

    private void initBundle() {
        if (getArguments() != null) {
            post = (Post) getArguments().getSerializable("key");
            binding.etTitle.setText(post.getTitle());
            binding.etContent.setText(post.getContent());
        }

    }
    private void close(){
        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);
        navController.navigateUp();
    }
}