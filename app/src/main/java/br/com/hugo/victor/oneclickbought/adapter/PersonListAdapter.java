package br.com.hugo.victor.oneclickbought.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import br.com.hugo.victor.oneclickbought.R;

import br.com.hugo.victor.oneclickbought.util.Util;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PersonListAdapter extends RecyclerView.Adapter {

    private Context mContext;
    private String[] mNames;
    private String[] mDescs;
    private String[] mLinkedins;

    public PersonListAdapter(Context context, int idNames, int idDesc, int idLinkedin) {
        mContext = context;

        mNames = mContext.getResources().getStringArray(idNames);
        mDescs = mContext.getResources().getStringArray(idDesc);
        mLinkedins = mContext.getResources().getStringArray(idLinkedin);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        DeveloperHolder holder = null;

        try {
            View view = LayoutInflater.from(mContext)
                    .inflate(R.layout.item_developer, parent, false);
            holder = new DeveloperHolder(view);
        } catch (Exception error) {
            Log.e("Error", "Error at onCreateViewHolder in " + getClass().getName() +
                    ". " + error.getMessage());
        }

        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        try {
            DeveloperHolder holder = (DeveloperHolder) viewHolder;

            holder.tvName.setText(mNames[position]);
            holder.tvDesc.setText(mDescs[position]);

            holder.ivLinkedin.setVisibility(mLinkedins[position]
                    .equals("nd") ? View.GONE : View.VISIBLE);

            holder.container.setTag(position);

            holder.container.setBackgroundColor(position % 2 == 0 ?
                    ContextCompat.getColor(mContext, R.color.colorGray0) :
                    ContextCompat.getColor(mContext, android.R.color.white));
        } catch (Exception error) {
            Log.e("Error", "Error at onBindViewHolder in " + getClass().getName() +
                    ". " + error.getMessage());
        }
    }

    @Override
    public int getItemCount() {
        return mNames.length;
    }

    class DeveloperHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.container)
        View container;
        @BindView(R.id.tvName)
        TextView tvName;
        @BindView(R.id.tvDesc)
        TextView tvDesc;
        @BindView(R.id.ivLinkedin)
        ImageView ivLinkedin;

        DeveloperHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }

        @OnClick({R.id.container})
        void onClick(View view) {
            try {
                int position = (int) view.getTag();

                if (mLinkedins[position].equals("nd"))
                    return;

                Util.openLinkedinProfile(mContext, mLinkedins[position]);
            } catch (Exception error) {
                Log.e("Error", "Error at openLinkedInProfile in " + getClass().getName()
                        + ". " + error.getMessage());
            }
        }
    }
}
