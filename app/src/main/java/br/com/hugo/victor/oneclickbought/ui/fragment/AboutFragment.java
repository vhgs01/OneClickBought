package br.com.hugo.victor.oneclickbought.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseUser;

import br.com.hugo.victor.oneclickbought.R;
import br.com.hugo.victor.oneclickbought.adapter.PersonListAdapter;
import br.com.hugo.victor.oneclickbought.ui.activity.LoginActivity;
import br.com.hugo.victor.oneclickbought.util.Firebase;
import br.com.hugo.victor.oneclickbought.util.Util;
import butterknife.BindView;
import butterknife.ButterKnife;

public class AboutFragment extends Fragment {

    private Firebase.Auth auth = new Firebase.Auth();

    @BindView(R.id.rvDevelopedBy)
    RecyclerView rvDevelopedBy;
    @BindView(R.id.btLogout)
    Button btLogout;
    @BindView(R.id.tvDeveloperPhone)
    TextView tvDeveloperPhone;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View viewInflated = inflater.inflate(R.layout.fragment_about, container, false);

        ButterKnife.bind(this, viewInflated);

        String phone = "<u>" + getString(R.string.text_developer_phone_number) + "</u>";
        tvDeveloperPhone.setText(Html.fromHtml(phone));

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

        tvDeveloperPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                call();
            }
        });

        return viewInflated;
    }

    private void doLogout() {
        auth.logOut();
        FirebaseUser user = auth.isUserCurrentlySignedIn();

        if (user == null) {
            Intent intent = new Intent(getContext(), LoginActivity.class);
            startActivity(intent);
            getActivity().finish();
        }
    }

    private void call() {
        Util.checkPermissionPhoneCall(getActivity(), getContext(),
                Util.getPhoneNumberFormatted(getString(R.string.text_developer_phone_number)));
    }

}
