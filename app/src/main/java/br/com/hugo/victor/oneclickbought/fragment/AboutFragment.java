package br.com.hugo.victor.oneclickbought.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.firebase.auth.FirebaseUser;

import br.com.hugo.victor.oneclickbought.R;
import br.com.hugo.victor.oneclickbought.adapter.PersonListAdapter;
import br.com.hugo.victor.oneclickbought.ui.LoginActivity;
import br.com.hugo.victor.oneclickbought.util.Firebase;
import butterknife.BindView;
import butterknife.ButterKnife;

public class AboutFragment extends Fragment {

    private Firebase firebase = new Firebase();

    @BindView(R.id.rvDevelopedBy)
    RecyclerView rvDevelopedBy;
    @BindView(R.id.btLogout)
    Button btLogout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View viewInflated = inflater.inflate(R.layout.activity_about, container, false);

        ButterKnife.bind(this, viewInflated);

        PersonListAdapter mDeveloperAdapter = new PersonListAdapter(getContext(),
                R.array.developer_name,
                R.array.developer_desc,
                R.array.developer_linkedin);
        rvDevelopedBy.setLayoutManager(new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL,
                false));
        rvDevelopedBy.setAdapter(mDeveloperAdapter);
        mDeveloperAdapter.notifyDataSetChanged();

        btLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doLogout();
            }
        });

        return viewInflated;
    }

    public void doLogout() {
        firebase.logOut();
        FirebaseUser user = firebase.isUserCurrentlySignedIn();

        if (user == null) {
            Intent intent = new Intent(getContext(), LoginActivity.class);
            startActivity(intent);
            getActivity().finish();
        }
    }

}
