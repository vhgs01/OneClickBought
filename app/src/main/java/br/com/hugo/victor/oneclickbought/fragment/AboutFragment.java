package br.com.hugo.victor.oneclickbought.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import br.com.hugo.victor.oneclickbought.R;
import br.com.hugo.victor.oneclickbought.adapter.PersonListAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;

public class AboutFragment extends Fragment {

    @BindView(R.id.rvDevelopedBy)
    RecyclerView rvDevelopedBy;

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

        return viewInflated;
    }
}
